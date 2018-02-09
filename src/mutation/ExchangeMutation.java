package mutation;

import base.City;
import base.Tour;
import main.Configuration;
import random.MersenneTwisterFast;

import java.util.ArrayList;

public class ExchangeMutation implements IMutation {
    public Tour doMutation(Tour tour) {

        ArrayList<City> tempCities = tour.getCities();
        MersenneTwisterFast mersenneTwisterFast = Configuration.instance.mersenneTwister;
        int indexFirstCity = mersenneTwisterFast.nextInt(0, tempCities.size() - 1);
        int indexSecondCity;
        do{
            indexSecondCity = mersenneTwisterFast.nextInt(0, tempCities.size() - 1);
        }while(indexSecondCity==indexFirstCity);

        City tempCity = tempCities.get(indexFirstCity);
        tempCities.set(indexFirstCity, tempCities.get(indexSecondCity));

        tempCities.set(indexSecondCity, tempCity);
        tour.setCities(tempCities);

        return tour;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}