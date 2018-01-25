package crossover;

import base.City;
import base.Tour;
import random.MersenneTwisterFast;

import java.util.List;

public class PositionBasedCrossover implements ICrossover {
    public Tour doCrossover(Tour tour01,Tour tour02) {
        MersenneTwisterFast random = new MersenneTwisterFast();

        List<City> tourCities1 = tour01.getCities();
        List<City> tourCities2 = tour02.getCities();
        int lastIndex = tour01.getSize()-1;

        int firstSplit = random.nextInt(1, lastIndex-1);
        int secondSplit = random.nextInt(firstSplit +1, lastIndex);

        List<City> base1 = tourCities1.subList(firstSplit, secondSplit);
        List<City> base2 = tourCities2.subList(firstSplit, secondSplit);

        Tour child1 = new Tour();
        Tour child2 = new Tour();

        tourCities1.removeAll(base2);
        tourCities2.removeAll(base1);

        for (int i = 0; i < firstSplit; i++)
        {
            child1.addCity(tourCities2.get(i));
            child2.addCity(tourCities1.get(i));
        }

        for (int i = 0; i < secondSplit - firstSplit; i++)
        {
            child1.addCity(base1.get(i));
            child2.addCity(base2.get(i));
        }

        for (int i = secondSplit; i < lastIndex+1; i++)
        {
            child1.addCity(tourCities2.get(i));
            child2.addCity(tourCities1.get(i));
        }


        switch (child1.compareTo(child2))
        {
            case 1: return child1;
            case -1: return child2;
            default: return child1;
        }

    }

    public String toString() {
        return getClass().getSimpleName();
    }
}