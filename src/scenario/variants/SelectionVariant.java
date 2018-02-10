package scenario.variants;

import utilities.EnumUtility;
import selection.ISelection;
import selection.RouletteWheelSelection;
import selection.TournamentSelection;

public enum SelectionVariant {
    RouletteWheel,
    Tournament;

    public static ISelection getInstance(SelectionVariant variant) throws IllegalArgumentException {
        switch (variant) {
            case RouletteWheel:
                return new RouletteWheelSelection();
            case Tournament:
                return new TournamentSelection();
        }

        throw new IllegalArgumentException(String.format("invalid selection type '%s'" +
                " for %s", variant, SelectionVariant.class.getName()));
    }

    public static ISelection getInstance(String selectionType) throws IllegalArgumentException {
        if (contains(selectionType)) {
            return getInstance(valueOf(selectionType));
        }

        throw new IllegalArgumentException(String.format("enum constant '%s' not defined in %s",
                selectionType, SelectionVariant.class.getName()));
    }

    public static boolean contains(String member) {
        return EnumUtility.isEnumMember(SelectionVariant.class, member);
    }
}
