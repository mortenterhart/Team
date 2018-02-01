package crossover;

import base.City;
import base.Tour;
import main.Configuration;
import random.MersenneTwisterFast;

import java.util.ArrayList;

public class PositionBasedCrossover implements ICrossover {
    public Tour doCrossover(Tour tour01,Tour tour02) {
        MersenneTwisterFast random = Configuration.instance.mersenneTwister;

        ArrayList<City> tourCities1 = CloneSubListCity(tour01.getCities(), 0, tour01.getSize());
        ArrayList<City> tourCities2 = CloneSubListCity(tour02.getCities(), 0, tour02.getSize());
        int lastIndex = tour01.getSize()-1;

        int firstSplit = random.nextInt(1, lastIndex-2);
        int secondSplit = random.nextInt(firstSplit +1, lastIndex-1);

        ArrayList<City> base1 = CloneSubListCity(tourCities1, firstSplit, secondSplit);
        ArrayList<City> base2 = CloneSubListCity(tourCities2, firstSplit, secondSplit);

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

        for (int i = firstSplit; i < tourCities1.size(); i++)
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