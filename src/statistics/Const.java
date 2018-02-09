package statistics;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public enum Const {

    instance;

    public static final String VAR_MINANDMAX = "\\[MINANDMAX\\]";
    public static String VAR_MFFSCENARIOS = "\\[MFFSCENARIOS\\]";
    public static String VAR_DOTPLOTSCENARIO = "\\[BOXPLOTSCENARIOS\\]";
    public static String VAR_DATADIR = "\\[DATADIR\\]";
    public static String VAR_SCENARIODESCRIPTION = "\\[SCENARIODESCRIPTION\\]";
    public static String VAR_BARPLOTSCENARIOS = "\\[BARPLOTSCENARIOS\\]";
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

    public String createScenarioShortname(List<Integer> scenarios) {
        StringBuilder text = new StringBuilder();
        for (int scenario : scenarios) {
            text.append("s").append(scenario).append(",");
        }
        text = new StringBuilder(text.substring(0, text.length() - 1));
        return text.toString();
    }

    public String createScenarioName(List<Integer> scenarios) {
        StringBuilder text = new StringBuilder();
        for (int scenario : scenarios) {
            text.append("\"Szenario ").append(scenario).append("\",");
        }
        text = new StringBuilder(text.substring(0, text.length() - 1));
        return text.toString();
    }

    public String writeCsvInScenarios(List<Integer> scenarios) {
        StringBuilder scenDesc = new StringBuilder();
        for (int scenario : scenarios) {
            scenDesc.append("s").append(scenario).append(" <- as.numeric(read.csv(\"data/data_scenario_").append(scenario).append(".csv\",header=FALSE)) ");
            scenDesc.append(System.getProperty("line.separator"));
        }
        return scenDesc.toString();
    }

    public String createBoxplotName(List<Integer> scenarios) {
        StringBuilder name = new StringBuilder("boxplot_s_");
        for (Integer scenario : scenarios) {
            name.append(scenario).append("_");
        }
        name = new StringBuilder(name.substring(0, name.length() - 1));
        return name + ".pdf";
    }

    public String createStripchartName(List<Integer> scenarios) {
        StringBuilder name = new StringBuilder("stripchart_s");
        for (Integer scenario : scenarios) {
            name.append("_").append(scenario);
        }
        return name + ".pdf";
    }

    public String createStripchartScenarios(List<Integer> scenarios) {
        StringBuilder text = new StringBuilder();
        for (Integer scenario : scenarios) {
            text.append("minimum <- min(s").append(scenario).append(")");
            text.append(System.getProperty("line.separator"));
            text.append("maximum <- max(s").append(scenario).append(")");
            text.append(System.getProperty("line.separator"));
            text.append("pdf(\"plots/stripchart_s").append(scenario).append(".pdf\",height = 10,width = 10,paper = \"A4r\")");
            text.append(System.getProperty("line.separator"));
            text.append("stripchart(s").append(scenario).append(",xlim=c(minimum,maximum),main = \"Genetic Algorithms - TSP280 - s").append(scenario).append("\",method=\"stack\")");
            text.append(System.getProperty("line.separator"));
            text.append("dev.off()");
            text.append(System.getProperty("line.separator"));
            text.append(System.getProperty("line.separator"));

        }
        return text.toString();
    }


    public String getScenariodescription_barplot(List<Integer> scenarioIds) {
        StringBuilder text = new StringBuilder();

        for (Integer scenarioId : scenarioIds) {
            text.append("pdf(\"plots/barplot_s").append(scenarioId).append(".pdf\",height = 10,width = 10,paper = \"A4r\")");
            text.append(System.getProperty("line.separator"));
            text.append("min <- min(s").append(scenarioId).append(")");
            text.append(System.getProperty("line.separator"));
            text.append("s").append(scenarioId).append(" <- c(round(min/s").append(scenarioId).append(",digits = 2)*100)");
            text.append(System.getProperty("line.separator"));
            text.append("barplot(s").append(scenarioId).append(",ylim=c(0,100),col=\"black\",ylab = \"solution quality (%)\",xlab = \"iterations\",width = 0.1,main = \"Genetic Algorithms - TSP280 - Scenario ").append(scenarioId).append("\")");
            text.append(System.getProperty("line.separator"));
            text.append("dev.off()");
            text.append(System.getProperty("line.separator")).append(System.getProperty("line.separator"));
        }

        return text.toString();
    }

    public String createDotplotName(List<Integer> scenario_ids) {
        StringBuilder text = new StringBuilder("dotplot_scenario");
        for (int scenario : scenario_ids) {
            text.append("_").append(scenario);
        }
        return text + ".pdf";
    }

    public String createDotplotScenarios(List<Integer> scenario_ids) {
        StringBuilder text = new StringBuilder();
        for (Integer scenario : scenario_ids) {
            text.append("plot(s").append(scenario).append(",col=\"black\",ylab = \"distance\",xlab = \"iterations\",cex = 0.1,main = \"Genetic Algorithms - TSP280 - Scenario ").append(scenario).append("\")");
            text.append(System.getProperty("line.separator"));
        }
        return text.toString();
    }

    public String createMffs(List<Integer> scenario_ids) {
        StringBuilder text = new StringBuilder("c(");
        for (Integer scenario : scenario_ids) {
            text.append("sort(table(s").append(scenario).append("),decreasing=TRUE)[1],");
        }
        text = new StringBuilder(text.substring(0, text.length() - 1));
        text.append(")");
        return text.toString();
    }

    public String createTTestText(List<Integer> scenario_ids) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < scenario_ids.size(); i++) {
            for (int j = i+1; j < scenario_ids.size(); j++) {
                text.append("c(mean(s").append(scenario_ids.get(i)).append("),mean(s").append(scenario_ids.get(j)).append("))");
                text.append(System.getProperty("line.separator"));
                text.append("t.test(s").append(scenario_ids.get(i)).append(",s").append(scenario_ids.get(j)).append(")");
                text.append(System.getProperty("line.separator"));
            }
        }
        return text.toString();
    }

    public String createMedianScenario(List<Integer> scenario_ids) {
        StringBuilder text = new StringBuilder("c(");
        for (int scenario_id : scenario_ids) {
            text.append("median(s").append(scenario_id).append("),");
        }
        text = new StringBuilder(text.substring(0, text.length() - 1));
        text.append(")");
        return text.toString();
    }
    public String createMeanScenario(List<Integer> scenario_ids) {
        StringBuilder text = new StringBuilder("c(");
        for (int scenario_id : scenario_ids) {
            text.append("round(mean(s").append(scenario_id).append("),digits = 2),");
        }
        text = new StringBuilder(text.substring(0, text.length() - 1));
        text.append(")");
        return text.toString();
    }

    public String createHistogramScenarios(List<Integer> scenario_ids) {
        StringBuilder text = new StringBuilder();
        for (int scenario_id : scenario_ids) {
            text.append("pdf(\"plots/histogram_s").append(scenario_id).append(".pdf\",height = 10,width = 10,paper = \"A4r\")\n");
            text.append("hist(s").append(scenario_id).append(",xlim=c(minimum,maximum),ylim=c(0,200),xlab = \"distance\",breaks=100,main = \"Genetic Algorithms - TSP280 - s").append(scenario_id).append("\")\n");
            text.append("dev.off()\n\n");
        }
        return text.toString();
    }

    public String createHistogramMinAndMax(List<Integer> scenario_ids) {
        StringBuilder minimumText = new StringBuilder("minimum <- min(");
        StringBuilder maximumText = new StringBuilder("maximum <- max(");

        for (int scenario_id : scenario_ids) {
            minimumText.append("s").append(scenario_id).append(",");
            maximumText.append("s").append(scenario_id).append(",");
        }
        minimumText = new StringBuilder(minimumText.substring(0, minimumText.length() - 1));
        maximumText = new StringBuilder(maximumText.substring(0, maximumText.length() - 1));

        minimumText.append(")\n");
        maximumText.append(")\n");
        return minimumText+ maximumText.toString();
    }

    public String createSdScenario(List<Integer> scenario_ids) {
        StringBuilder text = new StringBuilder("c(");
        for (int scenario_id : scenario_ids) {
            text.append("round(sd(s").append(scenario_id).append("),digits = 2),");
        }
        text = new StringBuilder(text.substring(0, text.length() - 1));
        text.append(")");
        return text.toString();
    }

    public String createRangeScenario(List<Integer> scenario_ids) {
        StringBuilder text = new StringBuilder("c(");
        for (Integer scenario_id : scenario_ids) {
            text.append("max(s").append(scenario_id).append(") - min(s").append(scenario_id).append("),");
        }
        text = new StringBuilder(text.substring(0, text.length() - 1));
        text.append(")");
        return text.toString();
    }

    public String createInterquartilerangeScenario(List<Integer> scenario_ids) {
        StringBuilder text = new StringBuilder("c(");
        for (Integer scenario_id : scenario_ids) {
            text.append("quantile(s").append(scenario_id).append(",0.75) - quantile(s").append(scenario_id).append(",0.25),");
        }
        text = new StringBuilder(text.substring(0, text.length() - 1));
        text.append(")");
        return text.toString();
    }

    public String createQuantile(List<Integer> scenario_ids,double quantileStart) {
        StringBuilder text = new StringBuilder("c(");
        for (int scenario_id : scenario_ids) {
            text.append("quantile(s").append(scenario_id).append(",").append(quantileStart).append("),");
        }
        text = new StringBuilder(text.substring(0, text.length() - 1));
        text.append(")");
        return text.toString();
    }

    public String createQuantileTo(List<Integer> scenario_ids, double quantileStart, double quantileEnd) {
        StringBuilder text = new StringBuilder();
        for (int scenario_id : scenario_ids) {
            text.append("quantile(s").append(scenario_id).append(",probs = c(").append(quantileStart).append(",").append(quantileEnd).append("))");
            text.append(System.getProperty("line.separator"));
        }
        return text.toString();
    }

    public String createQuantileRange(List<Integer> scenario_ids, double quantileStart, double quantileEnd) {
        StringBuilder text = new StringBuilder();
        for (int scenario_id : scenario_ids) {
            text.append("quantile(s").append(scenario_id).append(",probs = c(").append(quantileStart).append(",").append(quantileStart + 0.25).append(",").append(quantileEnd).append("))");
            text.append(System.getProperty("line.separator"));
        }
        return text.toString();
    }
}

