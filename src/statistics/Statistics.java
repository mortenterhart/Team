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
    private String mean;
    private String plot;
    private boolean ttest;
    private boolean mff;

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
    }

    public void buildBarPlotFile() {
        try {
            String barplot = new String(Files.readAllBytes(Paths.get("src/statistics/RTemplates/barplot.R.tpl")));
            barplot = barplot.replaceAll(Const.VAR_FILENAME,Const.instance.path);
            barplot = barplot.replaceAll(Const.VAR_SCENARIODESCRIPTION, Const.instance.getScenariodescription_barplot());
            PrintWriter writer = new PrintWriter(new File(Const.instance.barplot_file));
            writer.print(barplot);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildBoxPlotRFile() {
        List<Integer> scenario_ids = new ArrayList<>();
        scenario_ids.add(1);
        scenario_ids.add(2);
        scenario_ids.add(3);
        try {
            String boxplot = new String(Files.readAllBytes(Paths.get("src/statistics/RTemplates/boxplot.R.tpl")));
            boxplot = boxplot.replaceAll(Const.VAR_DATADIR,Const.instance.path);
            boxplot = boxplot.replaceAll(Const.VAR_SCENARIODESCRIPTION, Const.instance.writeCsvInScenarios(scenario_ids));
            boxplot = boxplot.replaceAll(Const.VAR_FILENAME, Const.instance.createBoxplotName(scenario_ids));
            boxplot = boxplot.replaceAll(Const.VAR_SCENARIOSHORT, Const.instance.createScenarioShortname(scenario_ids));
            boxplot = boxplot.replaceAll(Const.VAR_NAMES, Const.instance.createScenarioName(scenario_ids));

            PrintWriter writer = new PrintWriter(new File(Const.instance.boxplot_file));
            writer.print(boxplot);
            writer.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    public void buildDotPlotRFile() {
        List<Integer> scenario_ids = new ArrayList<>();
        scenario_ids.add(1);
        scenario_ids.add(2);
        scenario_ids.add(3);

        try {
            String dotplot = new String(Files.readAllBytes(Paths.get("src/statistics/RTemplates/dotplot.R.tpl")));
            dotplot = dotplot.replaceAll(Const.VAR_DATADIR,Const.instance.path);
            dotplot = dotplot.replaceAll(Const.VAR_SCENARIODESCRIPTION,Const.instance.writeCsvInScenarios(scenario_ids));
            dotplot = dotplot.replaceAll(Const.VAR_FILENAME,Const.instance.createDotplotName(scenario_ids));
            dotplot = dotplot.replaceAll(Const.VAR_DOTPLOTSCENARIO, Const.instance.createDotplotScenarios(scenario_ids));

            PrintWriter writer = new PrintWriter(new File(Const.instance.dotplox_file));
            writer.print(dotplot);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildStripChartRFile() {
        List<Integer> scenario_ids = new ArrayList<>();
        scenario_ids.add(1);
        scenario_ids.add(2);
        scenario_ids.add(3);
        try {
            String stripchart = new String(Files.readAllBytes(Paths.get("src/statistics/RTemplates/stripchart.R.tpl")));
            stripchart = stripchart.replaceAll(Const.VAR_DATADIR,Const.instance.path);
            stripchart = stripchart.replaceAll(Const.VAR_SCENARIODESCRIPTION,Const.instance.writeCsvInScenarios(scenario_ids));
            stripchart = stripchart.replaceAll(Const.VAR_FILENAME,Const.instance.createStripchartName(scenario_ids));
            stripchart = stripchart.replaceAll(Const.VAR_STRIPCHARTSCENARIOS,Const.instance.createStripchartScenarios(scenario_ids));
            PrintWriter writer = new PrintWriter(new File(Const.instance.stripchart_file));
            writer.print(stripchart);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildTTestRFile() {
    }

    public void buildHistogramRFile() {
    }

    public void buildMostFrequentFitnessValuesRFile() {
    }

    public static void main(String[] args){
        Statistics stats = new Statistics();
        stats.generateParams(args);
        stats.startupHSQLDB();
        stats.start();


    }
    public void generateParams(String[] args){
        for(int i = 0; i <= args.length; i++){
            switch (args[i]){
                case "-d":
                    while (!args[i].startsWith("-")){
                        i++;
                        scenarios.add(args[i]);
                    }
                case "-m":
                    while (!args[i].startsWith("-")){
                        i++;
                        mean = args[i];
                    }
                case "-p":
                    while (!args[i].startsWith("-")){
                        i++;
                        plot = args[i];
                    }
                case "-t":
                    while (!args[i].startsWith("-")){
                        i++;
                        ttest = true;
                    }
                case "-mff":
                    while (!args[i].startsWith("-")){
                        i++;
                        mff = true;
                    }
            }
            i++;
        }

    }

    public void startupHSQLDB() {
        HSQLDBManager.instance.startup();
    }

    public void start(){
        if (ttest == true){
            buildTTestRFile();
        }
        else{
            if (mff == true){
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