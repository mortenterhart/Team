package utilities;

public class EnumUtility {

    private EnumUtility() {}

    public static <E extends Enum<E>> boolean isEnumMember(Class<E> enumType, String member) {
        for (E constant : enumType.getEnumConstants()) {
            if (constant.name().equals(member)) {
                return true;
            }
        }
        return false;
    }
}
