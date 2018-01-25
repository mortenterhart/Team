package crossover;

import base.City;
import base.Tour;
import main.Configuration;

public class HeuristicCrossover implements ICrossover {
    public Tour doCrossover(Tour tour01, Tour tour02) {
        Tour t;
        if (tour01.compareTo(tour02) < 0)
            t = tour01;
        else
            t = tour02;

        int randomStart = Configuration.instance.mersenneTwister.nextInt(0, t.getSize() - 1);

        City startCity = t.getCity(randomStart);

        double min = 1000000000000.0;
        City nearestCity;
        for(int i = 0; i < t.getSize(); i++) {
            if(i != randomStart) {
                City c = t.getCity(i);
                double dist = distanceBetweenCities(startCity, c);
                if(dist < min) {
                    min = dist;
                    nearestCity = c;
                }
            }
        }

        //TODO: rest of algorithm

        return null;
    }

    public double distanceBetweenCities(City c1, City c2) {
        return Tour.euclideanDistance(c1.getX(), c1.getY(), c2.getX(), c2.getY());
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}