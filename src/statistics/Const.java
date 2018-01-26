package statistics;

import data.HSQLDBManager;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public enum Const {

    instance;

    public static String VAR_DATADIR = "\\[DATADIR\\]";
    public static String VAR_SCENARIODESCRIPTION = "\\[SCENARIODESCRIPTION\\]";
    public static String VAR_FILENAME = "\\[FILENAME\\]";
    public static String VAR_SCENARIOSHORT = "\\[SCENARIOSSHORT\\]";
    public static String VAR_NAMES = "\\[NAMES\\]";


    public String path = (new File("")).getAbsolutePath()+"/data";
    public String boxplot_file = "data/r_out/boxplot.r";
    public String barplot_file = "data/r_out/barplot.r";

    public String createBoxplotName(int countScenario) {
        String name = "boxplot_scenario_1";
        for (int i = 2; i <= countScenario ; i++) {
            name += "_"+i;
        }
        return name + ".pdf";
    }

    public String createScenarioShortname(int countScenario) {
        String scenariosshort = "s01";
        for (int i = 2; i <= countScenario; i++) {
            scenariosshort += ",s0"+i;
        }
        return scenariosshort;
    }

    public String createScenarioName(int countScenario) {
        String scenarionames = "\"Szenario 1\"";
        for (int i = 2; i <= countScenario; i++) {
            scenarionames += ",\"Szenario "+ i + "\"";
        }
        return scenarionames;
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

}
