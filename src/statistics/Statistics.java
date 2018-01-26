package statistics;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Statistics implements IStatistics {
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
        String scenDesc = "";
        int scenCount = 3;
        for(int i=1; i <= scenCount ; i++){
            scenDesc += "s0"+i+" <- as.numeric(read.csv(\"data/data_scenario_0"+i+".csv\",header=FALSE)) ";
            scenDesc += System.getProperty("line.separator");
        }
        try {
            String boxplot = new String(Files.readAllBytes(Paths.get("src/statistics/RTemplates/boxplot.R.tpl")));
            boxplot = boxplot.replaceAll(Const.VAR_DATADIR,Const.instance.path);
            boxplot = boxplot.replaceAll(Const.VAR_SCENARIODESCRIPTION, scenDesc);
            boxplot = boxplot.replaceAll(Const.VAR_FILENAME, Const.instance.createBoxplotName(scenCount));
            boxplot = boxplot.replaceAll(Const.VAR_SCENARIOSHORT, Const.instance.createScenarioShortname(scenCount));
            boxplot = boxplot.replaceAll(Const.VAR_NAMES, Const.instance.createScenarioName(scenCount));
            PrintWriter writer = new PrintWriter(new File(Const.instance.boxplot_file));
            writer.print(boxplot);
            writer.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void buildDotPlotRFile() {
    }

    public void buildStripChartRFile() {
    }

    public void buildTTestRFile() {
    }

    public void buildHistogramRFile() {
    }

    public void buildMostFrequentFitnessValuesRFile() {
    }
}