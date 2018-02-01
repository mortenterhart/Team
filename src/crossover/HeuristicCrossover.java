package crossover;

import base.City;
import base.Tour;
import main.Configuration;

import java.util.ArrayList;

public class HeuristicCrossover implements ICrossover {
    public Tour doCrossover(Tour tour01, Tour tour02) {
        Tour t;
        //use fittest tour
        if (tour01.compareTo(tour02) < 0)
            t = tour01;
        else
            t = tour02;

        int randomStart = Configuration.instance.mersenneTwister.nextInt(0, t.getSize() - 1);

        City currentCity = t.getCity(randomStart);

        ArrayList<City> unvisitedCities = new ArrayList<>(t.getCities());
        unvisitedCities.remove(currentCity);

        Tour child = new Tour();
        child.addCity(currentCity);

        while (unvisitedCities.size() > 0) {
            double min = Double.POSITIVE_INFINITY;
            City nearestCity = unvisitedCities.get(0);
            for (int i = 0; i < unvisitedCities.size(); i++) {
                City c = unvisitedCities.get(i);
                double dist = distanceBetweenCities(currentCity, c);
                if (dist < min) { //new nearest city found
                    min = dist;
                    nearestCity = c;
                }
            }
            child.addCity(nearestCity);
            currentCity = nearestCity;
            unvisitedCities.remove(nearestCity);
        }

        return child;
    }

    public double distanceBetweenCities(City c1, City c2) {
        return Tour.euclideanDistance(c1.getX(), c1.getY(), c2.getX(), c2.getY());
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}