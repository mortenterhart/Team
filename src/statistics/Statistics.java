package statistics;

import data.HSQLDBManager;
import org.hsqldb.util.CSVWriter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class Statistics implements IStatistics {
    public void writeCSVFile() {
        //ResultSet rs = HSQLDBManager.instance.getResultSet("SELECT * FROM DATA");
        try {

            PrintWriter writer = new PrintWriter(new File("data/ichsuchedich.csv"));
            for (int i = 100; i > 0; i--) {
                writer.println(i);

            //while (rs.next()) {
                //writer.println(rs.getString("id"));
            }
            writer.flush();
//        } catch (SQLException e) {
//            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void buildMeasureRFile() {
    }

    public void buildBarPlotFile() {
    }

    public void buildBoxPlotRFile() {
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