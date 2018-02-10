package scenario.xml;

import utilities.EnumUtility;

public enum XMLScenarioTag {
    selection,
    crossover,
    mutation,
    crossoverRatio,
    mutationRatio,
    numberOfIterations;

    public static final String PARENT_SCENARIO_TAG = "scenario";

    public static boolean contains(String xmlTag) {
        return EnumUtility.isEnumMember(XMLScenarioTag.class, xmlTag);
    }
}
