package crossover;

import base.City;
import base.Tour;
import random.MersenneTwisterFast;

import java.util.ArrayList;
import java.util.List;

public class SubTourExchangeCrossover implements ICrossover {
    public Tour doCrossover(Tour tour01,Tour tour02) {
        MersenneTwisterFast random = new MersenneTwisterFast();

        ArrayList<City> tourCities1 = CloneSubListCity(tour01.getCities(), 0, tour01.getSize());
        ArrayList<City> tourCities2 = CloneSubListCity(tour02.getCities(), 0, tour02.getSize());
        int lastIndex = tour01.getSize()-1;

        int firstSplit = random.nextInt(1, lastIndex-2);
        int secondSplit = random.nextInt(firstSplit +1, lastIndex-1);

        ArrayList<City> base1 = CloneSubListCity(tourCities1, firstSplit, secondSplit);
        ArrayList<City> base2 = CloneSubListCity(tourCities2, firstSplit, secondSplit);

        ArrayList<City> shuffledBase1 = CloneSubListCity(base1, 0, 1);
        ArrayList<City> shuffledBase2 =  CloneSubListCity(base2, 0, 1);

        for (int i = 1; i < base1.size(); i++)
        {
            int position = random.nextInt(0, shuffledBase1.size()-1);

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

        childTour1.setCities(tourCities1);
        childTour2.setCities(tourCities2);

        switch (childTour1.compareTo(childTour2))
        {
            case 1: return childTour1;
            case -1: return childTour2;
            default: return childTour1;
        }

    }

    private ArrayList<City> CloneSubListCity(ArrayList<City> list, int startIndex, int endIndex)
    {
        ArrayList<City> clonedList = new ArrayList<>();

        for (int i = startIndex; i < endIndex; i++)
        {
            clonedList.add(list.get(i));
        }

        return clonedList;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}