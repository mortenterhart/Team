package mutation;

import base.City;
import base.Tour;
import main.Configuration;
import random.MersenneTwisterFast;

import java.util.ArrayList;

public class ExchangeMutation implements IMutation {
    public Tour doMutation(Tour tour) {

        MersenneTwisterFast mersenneTwisterFast = Configuration.instance.mersenneTwister;
        int indexFirstCity = mersenneTwisterFast.nextInt(0, tour.getCities().size() - 1);
        int indexSecondCity;
        do{
            indexSecondCity = mersenneTwisterFast.nextInt(0, tour.getCities().size() - 1);
        }while(indexSecondCity==indexFirstCity);

        ArrayList<City> finalCities = tour.getCities();

        City firstCity = finalCities.remove(indexFirstCity);
        City secondCity = finalCities.remove(indexSecondCity);

        finalCities.add(indexSecondCity, firstCity);
        finalCities.add(indexFirstCity, secondCity);

        tour.setCities(finalCities);

        return tour;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}