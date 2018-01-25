package crossover;

import base.City;
import base.Tour;
import random.MersenneTwisterFast;

import java.util.ArrayList;
import java.util.List;

public class SubTourExchangeCrossover implements ICrossover {
    public Tour doCrossover(Tour tour01,Tour tour02) {
        MersenneTwisterFast random = new MersenneTwisterFast();

        List<City> tourCities1 = tour01.getCities();
        List<City> tourCities2 = tour02.getCities();
        int lastIndex = tour01.getSize()-1;

        int firstSplit = random.nextInt(1, lastIndex-1);
        int secondSplit = random.nextInt(firstSplit +1, lastIndex);

        List<City> base1 = tourCities1.subList(firstSplit, secondSplit);
        List<City> base2 = tourCities2.subList(firstSplit, secondSplit);

        List<City> shuffledBase1 = base1.subList(0, 1);
        List<City> shuffledBase2 =  base2.subList(0, 1);

        for (int i = 0; i < base1.size(); i++)
        {
            int position = random.nextInt(0, shuffledBase1.size());

            shuffledBase1.add(position, base1.get(i));
            shuffledBase2.add(position, base2.get(i));
        }

        for (int i = firstSplit, j = 0; i < secondSplit; i++, j++)
        {
            tourCities1.remove(i);
            tourCities1.add(i, shuffledBase1.get(j));

            tourCities2.remove(i);
            tourCities2.add(i, shuffledBase2.get(j));
        }


        Tour childTour1 = new Tour();
        Tour childTour2 = new Tour();

        childTour1.setCities((ArrayList)tourCities1);
        childTour2.setCities((ArrayList)tourCities2);

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