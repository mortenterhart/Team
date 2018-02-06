package statistics;

import data.HSQLDBManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public enum Const {

    instance;

    public static final String VAR_MINANDMAX = "\\[MINANDMAX\\]";
    public static String VAR_MFFSCENARIOS = "\\[MFFSCENARIOS\\]";
    public static String VAR_DOTPLOTSCENARIO = "\\[BOXPLOTSCENARIOS\\]";
    public static String VAR_DATADIR = "\\[DATADIR\\]";
    public static String VAR_SCENARIODESCRIPTION = "\\[SCENARIODESCRIPTION\\]";
    public static String VAR_FILENAME = "\\[FILENAME\\]";
    public static String VAR_SCENARIOSHORT = "\\[SCENARIOSSHORT\\]";
    public static String VAR_NAMES = "\\[NAMES\\]";
    public static String VAR_STRIPCHARTSCENARIOS = "\\[STRIPCHARTSCENARIO\\]";
    public static String VAR_TTESTSCENARIOS = "\\[TTESTSCENARIO\\]";
    public static String VAR_HISTOGRAMSCENARIOS = "\\[SCENARIOHISTOGRAM\\]";
    public static String VAR_MEDIAN = "\\[MEDIAN\\]";
    public static String VAR_MEAN = "\\[MEAN\\]";
    public static String VAR_SD = "\\[SD\\]";
    public static String VAR_RANGE = "\\[RANGE\\]";
    public static String VAR_INTERQUARTILERANGE = "\\[INTERQUARTILERANGE\\]";
    public static String VAR_QUANTILE = "\\[QUANTILE\\]";


    public String path = (new File("")).getAbsolutePath()+"/data";
    public String boxplot_file = "data/r_out/box_plot.r";
    public String barplot_file = "data/r_out/bar_plot.r";
    public String stripchart_file = "data/r_out/stripchart.r";
    public String dotplox_file = "data/r_out/dot_plot.r";
    public String mff_file = "data/r_out/analysis.r";
    public String ttest_file = "data/r_out/test.r";
    public String histogram_file = "data/r_out/histogram.r";
    public String measure_file = "data/r_out/measures.r";

    public String createBoxplotName(List<Integer> scenarios) {
        String name = "boxplot_scenario_";
        for (Integer scenario : scenarios) {
            name += scenario + "_";
        }
        name = name.substring(0,name.length()-1);
        return name + ".pdf";
    }

    public String createStripchartName(List<Integer> scenarios) {
        String name = "stripchart_scenario";
        for (Integer scenario : scenarios) {
            name += "_" + scenario;
        }
        return name + ".pdf";
    }

    public String createScenarioShortname(List<Integer> scenarios) {
        String text = "";
        for (int scenario : scenarios) {
            text += "s"+scenario+",";
        }
        text = text.substring(0,text.length()-1);
        return text;
    }

    public String createScenarioName(List<Integer> scenarios) {
        String text = "";
        for (int scenario : scenarios) {
            text += "\"Szenario "+scenario+"\",";
        }
        text = text.substring(0,text.length()-1);
        return text;
    }

    public String getScenariodescription_barplot() {
        List<Integer> list = getFitnessDataFromDB();
        int lastElement = list.get(list.size()-1);
        String scenariodescription = "c(";
        for (int fitness : list) {
            scenariodescription += "round("+fitness+"/"+lastElement+",digits=2)*100,";
        }
        scenariodescription = scenariodescription.substring(0,scenariodescription.length()-1);
        scenariodescription +=")";
        return scenariodescription;
    }

    private List<Integer> getFitnessDataFromDB() {
        List<Integer> list = new ArrayList<>();
        ResultSet rs = HSQLDBManager.instance.getResultSet("Select * from data");
        try {
            while(rs.next()) {
                list.add(rs.getInt("fitness"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public String writeCsvInScenarios(List<Integer> scenarios) {
        String scenDesc = "";
        for (int scenario : scenarios) {
            scenDesc += "s"+scenario+" <- as.numeric(read.csv(\"data/data_scenario_"+scenario+".csv\",header=FALSE)) ";
            scenDesc += System.getProperty("line.separator");
        }
        return scenDesc;
    }

    public String createStripchartScenarios(List<Integer> scenarios) {
        String text = "";
        for (Integer scenario : scenarios) {
            text += "minimum <- min(s"+scenario+")";
            text += System.getProperty("line.separator");
            text += "maximum <- max(s"+scenario+")";
            text += System.getProperty("line.separator");
            text += "pdf(\"plots/s"+scenario+"_stripchart.pdf\",height = 10,width = 10,paper = \"A4r\")";
            text += System.getProperty("line.separator");
            text += "stripchart(s"+scenario+",xlim=c(minimum,maximum),main = \"Genetic Algorithms - TSP280 - s"+scenario+"\",method=\"stack\")";
            text += System.getProperty("line.separator");
            text += "dev.off()";
            text += System.getProperty("line.separator");
            text += System.getProperty("line.separator");

        }
        return text;
    }

    public String createDotplotName(List<Integer> scenario_ids) {
        String text = "boxplot_scenario";
        for (int scenario : scenario_ids) {
            text += "_" + scenario;
        }
        return text + ".pdf";
    }

    public String createDotplotScenarios(List<Integer> scenario_ids) {
        String text = "";
        for (Integer scenario : scenario_ids) {
            text += "plot(s"+scenario+",col=\"black\",ylab = \"distance\",xlab = \"iterations\",cex = 0.1,main = \"Genetic Algorithms - TSP280 - Scenario "+scenario+"\")";
            text += System.getProperty("line.separator");
        }
        return text;
    }

    public String createMffs(List<Integer> scenario_ids) {
        String text = "c(";
        for (Integer scenario : scenario_ids) {
            text += "sort(table(s"+scenario+"),decreasing=TRUE)[1],";
        }
        text = text.substring(0,text.length()-1);
        text += ")";
        return text;
    }

    public String buildFileBeginning(List<Integer> scenario_ids, String template_path) throws IOException {
        String text = new String(Files.readAllBytes(Paths.get(template_path)));
        text = text.replaceAll(Const.VAR_DATADIR,Const.instance.path);
        text = text.replaceAll(Const.VAR_SCENARIODESCRIPTION,Const.instance.writeCsvInScenarios(scenario_ids));
        return text;
    }

    public void writeFile(String ttest, File file) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(file);
        writer.print(ttest);
        writer.flush();
    }

    public String createTTestText(List<Integer> scenario_ids) {
        String text = "";
        for (int i = 0; i < scenario_ids.size(); i++) {
            for (int j = i+1; j < scenario_ids.size(); j++) {
                text += "c(mean(s"+scenario_ids.get(i)+"),mean(s"+scenario_ids.get(j)+"))";
                text += System.getProperty("line.separator");
                text += "t.test(s"+scenario_ids.get(i)+",s"+scenario_ids.get(j)+")";
                text += System.getProperty("line.separator");
            }
        }
        return text;
    }

    public String createMedianScenario(List<Integer> scenario_ids) {
        String text = "c(";
        for (int scenario_id : scenario_ids) {
            text += "median(s"+scenario_id+"),";
        }
        text = text.substring(0,text.length()-1);
        text += ")";
        return text;
    }
    public String createMeanScenario(List<Integer> scenario_ids) {
        String text = "c(";
        for (int scenario_id : scenario_ids) {
            text += "round(mean(s"+scenario_id+"),digits = 2),";
        }
        text = text.substring(0,text.length()-1);
        text += ")";
        return text;
    }

    public String createHistogramName(List<Integer> scenario_ids) {
        String name = "histogram_scenario";
        for (Integer scenario : scenario_ids) {
            name += "_" + scenario;
        }
        return name + ".pdf";
    }

    public String createHistogramScenarios(List<Integer> scenario_ids) {
        String text = "";
        for (int scenario_id : scenario_ids) {
            text += "pdf(\"plots/s"+scenario_id+"_histogram.pdf\",height = 10,width = 10,paper = \"A4r\")\n";
            text += "hist(s"+scenario_id+",xlim=c(minimum,maximum),ylim=c(0,200),xlab = \"distance\",breaks=100,main = \"Genetic Algorithms - TSP280 - s"+scenario_id+"\")\n";
            text += "dev.off()\n\n";
        }
        return text;
    }

    public String createSdScenario(List<Integer> scenario_ids) {
        String text = "c(";
        for (int scenario_id : scenario_ids) {
            text += "round(sd(s"+scenario_id+"),digits = 2),";
        }
        text = text.substring(0,text.length()-1);
        text += ")";
        return text;
    }

    public String createRangeScenario(List<Integer> scenario_ids) {
        String text = "c(";
        for (Integer scenario_id : scenario_ids) {
            text += "max(s"+scenario_id+") − min(s"+scenario_id+"),";
        }
        text = text.substring(0,text.length()-1);
        text += ")";
        return text;
    }

    public String createInterquartilerangeScenario(List<Integer> scenario_ids) {
        String text = "c(";
        for (Integer scenario_id : scenario_ids) {
            text += "quantile(s"+scenario_id+",0.75) - quantile(s"+scenario_id+",0.25),";
        }
        text = text.substring(0,text.length()-1);
        text += ")";
        return text;
    }

    public String createQuantile(List<Integer> scenario_ids,double quantileStart) {
        String text = "c(";
        for (int scenario_id : scenario_ids) {
            text += "quantile(s"+scenario_id+","+quantileStart+"),";
        }
        text = text.substring(0,text.length()-1);
        text += ")";
        return text;
    }

    public String createQuantileTo(List<Integer> scenario_ids, double quantileStart, double quantileEnd) {
        String text = "";
        for (int scenario_id : scenario_ids) {
            text += "quantile(s"+scenario_id+",probs = c("+quantileStart+","+quantileEnd+"))";
            text += System.getProperty("line.separator");
        }
        return text;
    }

    public String createQuantileRange(List<Integer> scenario_ids, double quantileStart, double quantileEnd) {
        String text = "";
        for (int scenario_id : scenario_ids) {
            text += "quantile(s"+scenario_id+",probs = c("+quantileStart+","+(quantileStart+0.25)+","+quantileEnd+"))";
            text += System.getProperty("line.separator");
        }
        return text;
    }

    public String createHistogramMinAndMax(List<Integer> scenario_ids) {
        String minimumText = "minimum <- min(";
        String maximumText = "maximum <- max(";

        for (int scenario_id : scenario_ids) {
            minimumText += "s"+scenario_id+",";
            maximumText += "s"+scenario_id+",";
        }
        minimumText = minimumText.substring(0,minimumText.length()-1);
        maximumText = maximumText.substring(0,maximumText.length()-1);

        minimumText += ")\n";
        maximumText += ")\n";
        return minimumText+maximumText;
    }
}

