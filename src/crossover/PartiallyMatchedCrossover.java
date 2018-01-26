package crossover;

import base.City;
import base.Tour;
import random.MersenneTwisterFast;

import java.util.List;
import java.util.ArrayList;

public class PartiallyMatchedCrossover implements ICrossover {
    public Tour doCrossover(Tour tour01,Tour tour02) {
        MersenneTwisterFast random = new MersenneTwisterFast();

        ArrayList<City> tourCities1 = tour01.getCities();
        ArrayList<City> tourCities2 = tour02.getCities();
        int lastIndex = tour01.getSize()-1;

        int firstSplit = random.nextInt(1, lastIndex-2);
        int secondSplit = random.nextInt(firstSplit +1, lastIndex-1);

        ArrayList<City> base1 = CloneSubListCity(tourCities1, firstSplit, secondSplit);
        ArrayList<City> base2 = CloneSubListCity(tourCities2, firstSplit, secondSplit);

        ArrayList<City> child1 = CloneSubListCity(tourCities1, 0, firstSplit);
        child1.addAll(base2);
        child1.addAll(CloneSubListCity(tourCities1, secondSplit, lastIndex+1));

        ArrayList<City> child2 = CloneSubListCity(tourCities2, 0, firstSplit);
        child2.addAll(base1);
        child2.addAll(CloneSubListCity(tourCities2, secondSplit, lastIndex+1));

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

        childTour1.setCities(child1);
        childTour2.setCities(child2);

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