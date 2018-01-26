package mutation;

import base.City;
import base.Tour;
import random.MersenneTwisterFast;

import java.util.ArrayList;

public class InversionMutation implements IMutation {
    public Tour doMutation(Tour tour) {

        MersenneTwisterFast mtf = new MersenneTwisterFast();
        ArrayList<City> cities = tour.getCities();

        int point1;
        int point2;

        //Avoid getting the same pointss
        do {
            point1 = mtf.nextInt(0, cities.size() - 1);
            point2 = mtf.nextInt(0, cities.size() - 1);
        } while (point1 == point2);


        //check witch int ist hight
        int startPoint = point1 < point2 ? point1 : point2;
        int endPoint = point1 > point2 ? point1 : point2;


        City tempCity = null;
        for (int i = 0; i < (endPoint - startPoint) / 2 + 0.5; i++) {
            tempCity = cities.get(startPoint + i);
            cities.set(startPoint + i, cities.get(endPoint - i));
            cities.set(endPoint - i, tempCity);
        }

        return tour;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}