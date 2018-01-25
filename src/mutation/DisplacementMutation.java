package mutation;

import base.City;
import base.Tour;
import random.MersenneTwisterFast;

import java.util.ArrayList;

public class DisplacementMutation implements IMutation {
    public Tour doMutation(Tour tour) {
        MersenneTwisterFast twisterFast = new MersenneTwisterFast();
        //get start index
        int startIndex = twisterFast.nextInt(0, tour.getCities().size()-1);
        //get end index
        int endIndex = twisterFast.nextInt(0, tour.getCities().size()-1);
        while (endIndex == startIndex)
            endIndex = twisterFast.nextInt(0, tour.getCities().size()-1);

        //swap startIndex and endIndex if endIndex is smaller
        if (startIndex > endIndex) {
            int swap = startIndex;
            startIndex = endIndex;
            endIndex = swap;
        }
        ArrayList<City> finalCities = tour.getCities();

        //TODO rename
        ArrayList<City> helpCities = new ArrayList<>();

        int difference = endIndex-startIndex;
        for (int i = 0; i <= difference; i++)
        {
            helpCities.add(finalCities.get(startIndex));
            finalCities.remove(startIndex);
        }

        //get random insertPoint
        int insertPoint = twisterFast.nextInt(0, tour.getCities().size()-1);
        while (insertPoint==startIndex)
            insertPoint = twisterFast.nextInt(0, tour.getCities().size()-1);

        for (int i = 0; i < helpCities.size() ; i++)
        {
            finalCities.add(insertPoint+i, helpCities.get(i));
        }

        tour.setCities(finalCities);
        return tour;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}