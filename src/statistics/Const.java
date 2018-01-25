package statistics;

import java.io.File;

public enum Const {

    instance;

    public static String VAR_DATADIR = "\\[DATADIR\\]";
    public static String VAR_SCENARIODESCRIPTION_BOXPLOT = "\\[SCENARIODESCRIPTION\\]";
    public static String VAR_FILENAME = "\\[FILENAME\\]";
    public static String VAR_SCENARIOSHORT = "\\[SCENARIOSSHORT\\]";
    public static String VAR_NAMES = "\\[NAMES\\]";


    public String path = (new File("")).getAbsolutePath()+"/data";
    public String boxplot_file = "data/r_out/boxplot.r";

    public String createBoxplotName(int countScenario) {
        String name = "boxplot_scenario_1";
        for (int i = 1; i <= countScenario ; i++) {
            name += "_"+i;
        }
        return name + ".pdf";
    }

}
