package mutation;

import base.City;
import base.Tour;
import main.Configuration;
import org.junit.Test;
import random.MersenneTwisterFast;

import java.awt.*;
import java.util.ArrayList;

public class InsertionMutation implements IMutation {
    public Tour doMutation(Tour tour) {

        MersenneTwisterFast mtf = Configuration.instance.mersenneTwister;
        ArrayList<City> cities = tour.getCities();

        //get random point to pick object out
        int extractPoint = mtf.nextInt(0, cities.size() - 1);
        //get random point to insert picked-out object
        int insertPoint = mtf.nextInt(0, cities.size() - 1);

        City tempCity = cities.get(extractPoint);

        if (insertPoint < extractPoint) {
            cities.remove(extractPoint);
            cities.add(insertPoint, tempCity);
        } else {
            cities.add(insertPoint, tempCity);
            cities.remove(extractPoint);
        }

        return tour;
    }

    public String toString() {
        return getClass().getSimpleName();
    }


}