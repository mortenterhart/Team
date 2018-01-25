package crossover;

import base.City;
import base.Tour;
import random.MersenneTwisterFast;

import java.util.List;
import java.util.ArrayList;

public class PartiallyMatchedCrossover implements ICrossover {
    public Tour doCrossover(Tour tour01,Tour tour02) {
        MersenneTwisterFast random = new MersenneTwisterFast();

        List<City> tourCities1 = tour01.getCities();
        List<City> tourCities2 = tour02.getCities();
        int lastIndex = tour01.getSize()-1;

        int firstSplit = random.nextInt(1, lastIndex-1);
        int secondSplit = random.nextInt(firstSplit +1, lastIndex);

        List<City> base1 = tourCities1.subList(firstSplit, secondSplit);
        List<City> base2 = tourCities2.subList(firstSplit, secondSplit);

        List<City> child1 = tourCities1.subList(0, firstSplit);
        child1.addAll(base2);
        child1.addAll(tourCities1.subList(secondSplit, lastIndex));
        List<City> child2 = tourCities2.subList(0, firstSplit);
        child2.addAll(base1);
        child2.addAll(tourCities2.subList(secondSplit, lastIndex));

        for (int i = 0; i < firstSplit; i++)
        {
            while (base2.contains(child1.get(i)))
            {
                int baseIndex = base2.indexOf(child1.get(i));
                child1.set(i, base1.get(baseIndex));
            }

            while (base1.contains(child2.get(i)))
            {
                int baseIndex = base1.indexOf(child2.get(i));
                child2.set(i, base2.get(baseIndex));
            }
        }

        for (int i = secondSplit; i < lastIndex+1; i++)
        {
            while (base2.contains(child1.get(i)))
            {
                int baseIndex = base2.indexOf(child1.get(i));
                child1.set(i, base1.get(baseIndex));
            }

            while (base1.contains(child2.get(i)))
            {
                int baseIndex = base1.indexOf(child2.get(i));
                child2.set(i, base2.get(baseIndex));
            }
        }

        Tour childTour1 = new Tour();
        Tour childTour2 = new Tour();

        childTour1.setCities((ArrayList)child1);
        childTour2.setCities((ArrayList)child2);

        switch (childTour1.compareTo(childTour2))
        {
            case 1: return childTour1;
            case -1: return childTour2;
            default: return childTour1;
        }
    }


    public String toString() {
        return getClass().getSimpleName();
    }
}