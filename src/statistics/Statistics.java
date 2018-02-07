package statistics;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import data.HSQLDBManager;


public class Statistics implements IStatistics {

    private ArrayList<String> scenarios;
    private boolean median;
    private boolean mean;
    private boolean sd;
    private boolean range;
    private boolean interquartilsrange;
    private boolean quantile;
    private double quantileStart;
    private double quantileEnd;
    private double iqr;
    private boolean quantileTo;
    private boolean quantileRange;

    public void writeCSVFile() {
        HSQLDBManager.instance.startup();
        int numberScenarios = 0;
        ResultSet rs_scenarios = HSQLDBManager.instance.getResultSet("select count(DISTINCT scenario) from data;");
        try {
            rs_scenarios.next();
            numberScenarios = rs_scenarios.getInt(1);

        } catch (SQLException e) {
            e.printStackTrace();
        }


        for (int i = 1; i <= numberScenarios; i++) {
            ResultSet rs = HSQLDBManager.instance.getResultSet("SELECT * FROM DATA WHERE scenario="+i);
            try {
                PrintWriter writer = new PrintWriter(new File("data/data_scenario_"+i+".csv"));
                PrintWriter barplotwriter = new PrintWriter(new File("data/data_scenario_"+i+"_barplot.csv"));
                while (rs.next()) {
                    writer.print(rs.getDouble("fitness")+",");
                    barplotwriter.print(rs.getDouble("fitness"));

                }
                writer.flush();
                barplotwriter.flush();

            } catch (SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public void buildMeasureRFile() {
        List<Integer> scenario_ids = createScenarios();
        try {
            String measurefile = Const.instance.buildFileBeginning(scenario_ids,"src/statistics/RTemplates/measures.R.tpl");

            measurefile = median ? measurefile.replaceAll(Const.VAR_MEDIAN, Const.instance.createMedianScenario(scenario_ids)) : measurefile.replaceAll(Const.VAR_MEDIAN, "");
            measurefile = mean ? measurefile.replaceAll(Const.VAR_MEAN,Const.instance.createMeanScenario(scenario_ids)) : measurefile.replaceAll(Const.VAR_MEAN,"");
            measurefile = sd ? measurefile.replaceAll(Const.VAR_SD,Const.instance.createSdScenario(scenario_ids)) : measurefile.replaceAll(Const.VAR_SD,"");
            measurefile = range ? measurefile.replaceAll(Const.VAR_RANGE,Const.instance.createRangeScenario(scenario_ids)) : measurefile.replaceAll(Const.VAR_RANGE,"");
            measurefile = interquartilsrange ? measurefile.replaceAll(Const.VAR_INTERQUARTILERANGE,Const.instance.createInterquartilerangeScenario(scenario_ids)) : measurefile.replaceAll(Const.VAR_INTERQUARTILERANGE,"");

            String quantileText = "";
            if (quantile) quantileText += Const.instance.createQuantile(scenario_ids, quantileStart) + "\n";
            if (quantileTo) quantileText += Const.instance.createQuantileTo(scenario_ids, quantileStart, quantileEnd)+"\n";
            if (quantileRange) quantileText += Const.instance.createQuantileRange(scenario_ids, quantileStart, quantileEnd)+"\n";
            measurefile = measurefile.replaceAll(Const.VAR_QUANTILE,quantileText);

            Const.instance.writeFile(measurefile,new File(Const.instance.measure_file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildBarPlotFile() {
        try {
            String barplot = new String(Files.readAllBytes(Paths.get("src/statistics/RTemplates/barplot.R.tpl")));
            barplot = barplot.replaceAll(Const.VAR_FILENAME,Const.instance.path);
            barplot = barplot.replaceAll(Const.VAR_SCENARIODESCRIPTION, Const.instance.getScenariodescription_barplot());
            Const.instance.writeFile(barplot, new File(Const.instance.barplot_file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildBoxPlotRFile() {
        List<Integer> scenario_ids = createScenarios();
        try {
            String boxplot = Const.instance.buildFileBeginning(scenario_ids,"src/statistics/RTemplates/boxplot.R.tpl");
            boxplot = boxplot.replaceAll(Const.VAR_FILENAME, Const.instance.createBoxplotName(scenario_ids));
            boxplot = boxplot.replaceAll(Const.VAR_SCENARIOSHORT, Const.instance.createScenarioShortname(scenario_ids));
            boxplot = boxplot.replaceAll(Const.VAR_NAMES, Const.instance.createScenarioName(scenario_ids));
            Const.instance.writeFile(boxplot, new File(Const.instance.boxplot_file));
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    public void buildDotPlotRFile() {
        List<Integer> scenario_ids = createScenarios();

        try {
            String dotplot = Const.instance.buildFileBeginning(scenario_ids,"src/statistics/RTemplates/dotplot.R.tpl");
            dotplot = dotplot.replaceAll(Const.VAR_FILENAME,Const.instance.createDotplotName(scenario_ids));
            dotplot = dotplot.replaceAll(Const.VAR_DOTPLOTSCENARIO, Const.instance.createDotplotScenarios(scenario_ids));

            Const.instance.writeFile(dotplot, new File(Const.instance.dotplox_file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildStripChartRFile() {
        List<Integer> scenario_ids = createScenarios();
        try {
            String stripchart = Const.instance.buildFileBeginning(scenario_ids,"src/statistics/RTemplates/stripchart.R.tpl");
            stripchart = stripchart.replaceAll(Const.VAR_FILENAME,Const.instance.createStripchartName(scenario_ids));
            stripchart = stripchart.replaceAll(Const.VAR_STRIPCHARTSCENARIOS,Const.instance.createStripchartScenarios(scenario_ids));
            Const.instance.writeFile(stripchart, new File(Const.instance.stripchart_file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildTTestRFile() {
        List<Integer> scenario_ids = createScenarios();
        try {
            String ttest = Const.instance.buildFileBeginning(scenario_ids,"src/statistics/RTemplates/t_test.R.tpl");
            ttest = ttest.replaceAll(Const.VAR_TTESTSCENARIOS,Const.instance.createTTestText(scenario_ids));

            Const.instance.writeFile(ttest, new File(Const.instance.ttest_file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Integer> createScenarios() {
        List<Integer> scenario_ids = new ArrayList<>();
        for (String scenario : scenarios) {
            scenario_ids.add(Integer.parseInt(scenario.substring(1)));
        }
        return scenario_ids;
    }


    public void buildHistogramRFile() {
        List<Integer> scenario_ids = createScenarios();
        try {
            String histogram = Const.instance.buildFileBeginning(scenario_ids,"src/statistics/RTemplates/histogram.R.tpl");
            histogram = histogram.replaceAll(Const.VAR_MINANDMAX,Const.instance.createHistogramMinAndMax(scenario_ids));
            histogram = histogram.replaceAll(Const.VAR_HISTOGRAMSCENARIOS,Const.instance.createHistogramScenarios(scenario_ids));
            Const.instance.writeFile(histogram,new File(Const.instance.histogram_file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildMostFrequentFitnessValuesRFile() {
        List<Integer> scenario_ids = createScenarios();
        try {
            String mff = Const.instance.buildFileBeginning(scenario_ids,"src/statistics/RTemplates/mff.R.tpl");
            mff = mff.replaceAll(Const.VAR_MFFSCENARIOS,Const.instance.createMffs(scenario_ids));
            Const.instance.writeFile(mff, new File(Const.instance.mff_file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public static void main(String[] args){
        Statistics stats = new Statistics();
        stats.writeCSVFile();
        stats.generateParams2(args);
        stats.startupHSQLDB();
    }

    private void generateParams2(String[] args) {
            scenarios = new ArrayList<>();
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-d")) {
                for (int j = i+1; j < args.length; j++) {
                    if (!args[j].startsWith("-")) {
                        for (String s : args[j].split(",")) {
                            scenarios.add(s);
                        }
                        //scenarios.add(args[j].replaceAll(",",""));
                    } else {
                        break;
                    }
                }
            } else if(args[i].startsWith("-m")) {
                for (int j = i+1; j < args.length; j++) {
                    if(!args[j].startsWith("-")) {
                        if (args[j].startsWith("median")) median = true;
                        if (args[j].startsWith("mean")) mean = true;
                        if (args[j].startsWith("range")) range = true;
                        if (args[j].startsWith("sd")) sd = true;
                        if (args[j].startsWith("iqr")) {
                            interquartilsrange = true;
                        }
                        if (args[j].startsWith("quantile")) {
                            if (args[j].matches("quantile=0\\.[0-9]+")) {
                                quantile = true;
                                quantileStart = Double.parseDouble(args[j].substring(args[j].lastIndexOf("=")+1));
                            } else if(args[j].matches("quantile=0\\.[0-9]+-0\\.[0-9]+")) {
                                quantileRange = true;
                                quantileStart = Double.parseDouble(args[j].substring(args[j].lastIndexOf("=")+1,args[j].lastIndexOf("-")));
                                quantileEnd = Double.parseDouble(args[j].substring(args[j].lastIndexOf("-")+1));
                            } else if(args[j].matches("quantile=0\\.[0-9]+,0\\.[0-9]+")) {
                                quantileTo = true;
                                quantileStart = Double.parseDouble(args[j].substring(args[j].lastIndexOf("=")+1,args[j].lastIndexOf(",")));
                                quantileEnd = Double.parseDouble(args[j].substring(args[j].lastIndexOf(",")+1));
                            }
                        }
                    } else {
                        break;
                    }
                }
                buildMeasureRFile();
            } else if(args[i].startsWith("-p")) {
                for (int j = i+1; j < args.length; j++) {
                    if(!args[j].startsWith("-")) {
                        if(args[j].startsWith("bar"))
                            buildBarPlotFile();
                        if(args[j].startsWith("box"))
                            buildBoxPlotRFile();
                        if(args[j].startsWith("dot"))
                            buildDotPlotRFile();
                        if(args[j].startsWith("hist"))
                            buildHistogramRFile();
                        if(args[j].startsWith("strip"))
                            buildStripChartRFile();
                    } else {
                        break;
                    }
                }
            } else if(args[i].startsWith("-t")) {
                buildTTestRFile();
            } else if(args[i].startsWith("-a")) {
                for (int j = i+1; j < args.length; j++) {
                    if(!args[j].startsWith("-")) {
                        if(args[j].startsWith("mff")) {
                            buildMostFrequentFitnessValuesRFile();
                        }
                    } else {
                        break;
                    }
                }
            }
        }
    }



    private void startupHSQLDB() {
        HSQLDBManager.instance.startup();
    }

}