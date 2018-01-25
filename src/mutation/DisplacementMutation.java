package mutation;

import base.City;
import base.Tour;
import random.MersenneTwisterFast;

import java.util.ArrayList;

public class DisplacementMutation implements IMutation {
    public Tour doMutation(Tour tour) {

        MersenneTwisterFast twisterFast = new MersenneTwisterFast();
        int startIndex = twisterFast.nextInt(0, tour.getCities().size() - 1);
        int endIndex;
        do {
            endIndex = twisterFast.nextInt(0, tour.getCities().size() - 1);
        } while (endIndex == startIndex);


        //swap startIndex and endIndex if endIndex is smaller
        if (startIndex > endIndex) {
            int swap = startIndex;
            startIndex = endIndex;
            endIndex = swap;
        }
        ArrayList<City> finalCities = tour.getCities();
        ArrayList<City> tempCities = new ArrayList<>();

        int difference = endIndex - startIndex;
        for (int i = 0; i <= difference; i++) {
            tempCities.add(finalCities.get(startIndex));
            finalCities.remove(startIndex);
        }

        //get random insertPoint
        int insertPoint;
        do {
            insertPoint = twisterFast.nextInt(0, tour.getCities().size() - 1);
        } while (insertPoint == startIndex);

        for (int i = 0; i < tempCities.size(); i++) {
            finalCities.add(insertPoint + i, tempCities.get(i));
        }

        tour.setCities(finalCities);
        return tour;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}