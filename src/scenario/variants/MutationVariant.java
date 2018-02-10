package scenario.variants;

import mutation.DisplacementMutation;
import mutation.ExchangeMutation;
import mutation.HeuristicMutation;
import mutation.IMutation;
import mutation.InsertionMutation;
import mutation.InversionMutation;
import utilities.EnumUtility;

public enum MutationVariant {
    Displacement,
    Exchange,
    Heuristic,
    Insertion,
    Inversion;

    public static IMutation getInstance(MutationVariant variant) {
        switch (variant) {
            case Displacement:
                return new DisplacementMutation();
            case Exchange:
                return new ExchangeMutation();
            case Heuristic:
                return new HeuristicMutation();
            case Insertion:
                return new InsertionMutation();
            case Inversion:
                return new InversionMutation();
        }

        throw new IllegalArgumentException(String.format("invalid crossover type '%s'" +
                " for %s", variant, MutationVariant.class.getName()));
    }

    public static IMutation getInstance(String mutationType) {
        if (contains(mutationType)) {
            return getInstance(valueOf(mutationType));
        }

        throw new IllegalArgumentException(String.format("enum constant '%s' not defined in %s",
                mutationType, MutationVariant.class.getName()));
    }

    public static boolean contains(String member) {
        return EnumUtility.isEnumMember(MutationVariant.class, member);
    }
}
