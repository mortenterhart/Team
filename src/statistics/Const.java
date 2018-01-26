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


    public String path = (new File("")).getAbsolutePath()+"/data";
    public String boxplot_file = "data/r_out/box_plot.r";
    public String barplot_file = "data/r_out/bar_plot.r";
    public String stripchart_file = "data/r_out/stripchart.r";
    public String dotplox_file = "data/r_out/dot_plot.r";
    public String mff_file = "data/r_out/analysis.r";
    public String ttest_file = "data/r_out/test.r";
    public String histogram_file = "data/r_out/histogram.r";;

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
            text += "s0"+scenario+",";
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
        ResultSet rs = HSQLDBManager.instance.getResultSet("Select * from DATA");
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
            scenDesc += "s0"+scenario+" <- as.numeric(read.csv(\"data/data_scenario_"+scenario+".csv\",header=FALSE)) ";
            scenDesc += System.getProperty("line.separator");
        }
        return scenDesc;
    }

    public String createStripchartScenarios(List<Integer> scenarios) {
        String text = "";
        for (Integer scenario : scenarios) {
            text += "stripchart(s0"+scenario+",xlim=c(2500,5000),main = \"Genetic Algorithms - TSP280 - Scenario "+scenario+"\",method=\"stack\")";
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
            text += "plot(s0"+scenario+",col=\"black\",ylab = \"distance\",xlab = \"iterations\",cex = 0.1,main = \"Genetic Algorithms - TSP280 - Scenario "+scenario+"\")";
            text += System.getProperty("line.separator");
        }
        return text;
    }

    public String createMffs(List<Integer> scenario_ids) {
        String text = "";
        for (Integer scenario : scenario_ids) {
            text += "sort(table(s0"+scenario+"),decreasing=TRUE)[1]";
            text += System.getProperty("line.separator");
        }
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
                text += "c(mean(s0"+scenario_ids.get(i)+"),mean(s0"+scenario_ids.get(j)+"))";
                text += System.getProperty("line.separator");
                text += "t.test(s0"+scenario_ids.get(i)+",s0"+scenario_ids.get(j)+")";
                text += System.getProperty("line.separator");
            }
        }
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
        for (Integer scenario : scenario_ids) {
            text += "hist(s0"+scenario+",xlim=c(2500,5000),ylim=c(0,200),xlab = \"distance\",breaks=100,main = \"Genetic Algorithms - TSP280\" - Scenario "+scenario+")";
            text += System.getProperty("line.separator");
        }
        return text;
    }
}

