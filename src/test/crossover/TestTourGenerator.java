package test.crossover;

import base.City;
import base.Tour;
import main.Configuration;
import random.MersenneTwisterFast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class TestTourGenerator {
    public Tour generateTourAllCities() {
        return generateTour(CityTestData.Data.getAllCities());
    }

    public Tour generateTourTestCities() {
        return generateTour(CityTestData.Data.getTestCities());
    }

    private Tour generateTour(ArrayList<City> cities) {
        Tour tour = new Tour();
        Collections.shuffle(cities);
        tour.setCities(cities);
        return tour;
    }

}
