package utilities;

import base.Population;
import base.Tour;

import java.util.List;

public class MinimalTourDetector {

    private MinimalTourDetector() {}

    public static Tour minimalTourIn(List<Tour> tours) throws IndexOutOfBoundsException, IllegalArgumentException {
        return minimalTourIn(tours, 0, tours.size());
    }

    public static Tour minimalTourIn(Population population) throws IndexOutOfBoundsException, IllegalArgumentException {
        return minimalTourIn(population.getTours());
    }

    public static Tour minimalTourIn(List<Tour> tours, int fromIndex, int toIndex) throws IndexOutOfBoundsException, IllegalArgumentException {
        if (indexNotInRange(tours.size(), fromIndex) || indexNotInRange(tours.size(), toIndex)) {
            throw new IndexOutOfBoundsException("lower index " + fromIndex + " or upper index " +
                    toIndex + " out of scope with " + tours.size() + " elements");
        }

        if (fromIndex > toIndex) {
            throw new IllegalArgumentException("minimal tour calculator: invalid index boundaries " +
                    "(fromIndex = " + fromIndex + " > toIndex = " + toIndex + ")");
        }

        Tour minimalTour = null;
        double minimalFitness = Double.MAX_VALUE;

        for (int index = fromIndex; index < toIndex; index++) {
            Tour currentTour = tours.get(index);
            double currentFitness = currentTour.getFitness();

            if (currentFitness < minimalFitness) {
                minimalTour = currentTour;
                minimalFitness = currentFitness;
            }
        }

        return minimalTour;
    }

    private static boolean indexNotInRange(int listLength, int index) {
        return !(index >= 0 && index <= listLength);
    }
}
