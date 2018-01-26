package statistics;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import data.HSQLDBManager;


public class Statistics implements IStatistics {

    private ArrayList<String> scenarios;
    private String measures = "";
    private String plot;
    private boolean ttest;
    private boolean mff;
    private boolean median;
    private boolean mean;
    private boolean sd;
    private boolean range;
    private boolean interquartilsrange;
    private double quantileStart;
    private double quantileEnd;
    private double iqr;
    private boolean quantileTo;

    public void writeCSVFile() {
        //ResultSet rs = HSQLDBManager.instance.getResultSet("SELECT * FROM DATA");
        try {
            for (int i = 0; i < 3; i++) {
                PrintWriter writer = new PrintWriter(new File("data/data_scenario_"+i+".csv"));
                PrintWriter barplotwriter = new PrintWriter(new File("data/data_scenario_"+i+"_barplot.csv"));
                for (int j = 100; j > 0; j--) {
                    writer.println(j);
                    barplotwriter.print(j+",");
                    //while (rs.next()) {
                    //writer.println(rs.getString("id"));
                }
                writer.flush();
                barplotwriter.flush();
            }

//        } catch (SQLException e) {
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildMeasureRFile() {
        List<Integer> scenario_ids = createScenarios();
        try {
            String measurefile = Const.instance.buildFileBeginning(scenario_ids,"src/statistics/RTemplates/measures.R.tpl");

            measurefile = median ? measurefile.replaceAll(Const.VAR_MEDIAN,Const.instance.createMedianScenario(scenario_ids)) : measurefile.replaceAll(Const.VAR_MEDIAN,"");
            measurefile = mean ? measurefile.replaceAll(Const.VAR_MEAN,Const.instance.createMeanScenario(scenario_ids)) : measurefile.replaceAll(Const.VAR_MEAN,"");
            measurefile = sd ? measurefile.replaceAll(Const.VAR_SD,Const.instance.createSdScenario(scenario_ids)) : measurefile.replaceAll(Const.VAR_SD,"");
            measurefile = range ? measurefile.replaceAll(Const.VAR_RANGE,Const.instance.createRangeScenario(scenario_ids)) : measurefile.replaceAll(Const.VAR_RANGE,"");
            measurefile = interquartilsrange ? measurefile.replaceAll(Const.VAR_INTERQUARTILERANGE,Const.instance.createInterquartilerangeScenario(scenario_ids)) : measurefile.replaceAll(Const.VAR_INTERQUARTILERANGE,"");

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
        scenario_ids.add(1);
        scenario_ids.add(2);
        scenario_ids.add(3);
        scenario_ids.add(4);
        scenario_ids.add(5);
        return scenario_ids;
    }


    public void buildHistogramRFile() {
        List<Integer> scenario_ids = createScenarios();
        try {
            String histogram = Const.instance.buildFileBeginning(scenario_ids,"src/statistics/RTemplates/histogram.R.tpl");
            histogram = histogram.replaceAll(Const.VAR_FILENAME,Const.instance.createHistogramName(scenario_ids));
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
        stats.generateParams(args);
        stats.startupHSQLDB();
        stats.start();


    }
    private void generateParams(String[] args){
        scenarios = new ArrayList<>();
        int i=0;
        while(i<args.length){
            switch (args[i]) {
                case "-d":
                    if(i<args.length){
                        if (i >= args.length-1) break;
                        i++;
                    }
                    while (!args[i].startsWith("-")) {
                        scenarios.add(args[i].replaceAll(",",""));
                        if(i<args.length){
                            if (i >= args.length-1) break;
                            i++;
                        }
                    }
                    break;
                case "-m":
                    if(i<args.length){
                        if (i >= args.length-1) break;
                        i++;
                    }
                    while (!args[i].startsWith("-")) {
                        measures = args[i];
                        if(i<args.length){
                            if (i >= args.length-1) break;
                            i++;
                        }
                    }
                    break;
                case "-p":
                    if(i<args.length){
                        if (i >= args.length-1) break;
                        i++;
                    }
                    while (!args[i].startsWith("-")) {
                        plot = args[i];
                        if(i<args.length){
                            if (i >= args.length-1) break;
                            i++;
                        }
                    }
                    break;
                case "-t":
                    if(i<args.length){
                        if (i >= args.length-1) break;
                        i++;
                    }
                    while (!args[i].startsWith("-")) {
                        ttest = true;
                        if(i<args.length){
                            if (i >= args.length-1) break;
                            i++;
                        }
                    }
                    break;
                case "-a mff":
                    if(i<args.length){
                        if (i >= args.length-1) break;
                        i++;
                    }
                    while (!args[i].startsWith("-")) {
                        mff = true;
                        if(i<args.length){
                            if (i >= args.length-1) break;
                            i++;
                        }
                    }
                default: i++;
                    break;
            }
        }

    }

    private void startupHSQLDB() {
        HSQLDBManager.instance.startup();
    }

    private void start(){
        if (measures.startsWith("iqr")){
            iqr = Double.parseDouble(measures.substring(measures.lastIndexOf("=")+1));
        }
        if(measures.startsWith("quantile")){
            int seperatorIndex = measures.length();
            if (measures.matches("quantile=0\\.[0-9]+-0\\.[0-9]+")){
                quantileTo = true;
                seperatorIndex = measures.lastIndexOf("-");
                measures=measures.replaceAll("-",",");
            }else{
                quantileTo = false;
                if(measures.matches("quantile=0\\.[0-9]+,0\\.[0-9]+")) {
                    seperatorIndex = measures.lastIndexOf(",");
                }
            }
            quantileStart = Double.parseDouble(measures.substring(measures.lastIndexOf("=")+1,seperatorIndex));
            if (measures.indexOf(",")>0){
                quantileEnd = Double.parseDouble(measures.substring(measures.lastIndexOf(",")+1,measures.length()));
            }
        }
        if (ttest){
            buildTTestRFile();
        }
        else{
            if (mff){
                buildMostFrequentFitnessValuesRFile();
            }
            else{
                switch (plot){
                    case "bar": buildBarPlotFile();
                    case "box": buildBoxPlotRFile();
                    case "strip": buildStripChartRFile();
                    case "hist": buildHistogramRFile();
                    case "dot": buildDotPlotRFile();
                }
            }
        }

    }

}