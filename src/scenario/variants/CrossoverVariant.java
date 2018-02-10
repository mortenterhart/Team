package scenario.variants;

import crossover.CycleCrossover;
import crossover.HeuristicCrossover;
import crossover.ICrossover;
import crossover.OrderedCrossover;
import crossover.PartiallyMatchedCrossover;
import crossover.PositionBasedCrossover;
import crossover.SubTourExchangeCrossover;
import utilities.EnumUtility;

public enum CrossoverVariant {
    Cycle,
    Heuristic,
    Ordered,
    PartiallyMatched,
    PositionBased,
    SubTourExchange;

    public static ICrossover getInstance(CrossoverVariant variant) {
        switch (variant) {
            case Cycle:
                return new CycleCrossover();
            case Heuristic:
                return new HeuristicCrossover();
            case Ordered:
                return new OrderedCrossover();
            case PartiallyMatched:
                return new PartiallyMatchedCrossover();
            case PositionBased:
                return new PositionBasedCrossover();
            case SubTourExchange:
                return new SubTourExchangeCrossover();
        }

        throw new IllegalArgumentException(String.format("invalid crossover type '%s'" +
                " for %s", variant, CrossoverVariant.class.getName()));
    }

    public static ICrossover getInstance(String crossoverType) {
        if (contains(crossoverType)) {
            return getInstance(valueOf(crossoverType));
        }

        throw new IllegalArgumentException(String.format("enum constant '%s' not defined in %s",
                crossoverType, CrossoverVariant.class.getName()));
    }

    public static boolean contains(String member) {
        return EnumUtility.isEnumMember(CrossoverVariant.class, member);
    }
}
