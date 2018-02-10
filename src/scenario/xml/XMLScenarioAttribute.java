package scenario.xml;

import utilities.EnumUtility;

public enum XMLScenarioAttribute {
    id;

    public static boolean contains(String member) {
        return EnumUtility.isEnumMember(XMLScenarioAttribute.class, member);
    }
}
