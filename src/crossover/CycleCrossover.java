package crossover;

import base.City;
import base.Tour;

import java.util.ArrayList;

public class CycleCrossover implements ICrossover {
    public Tour doCrossover(Tour tour01,Tour tour02) {
        int length1 = tour01.getSize();
        int length2 = tour02.getSize();
        if(length1 != length2) {
            throw new Error("Number of cities of given tours not identical");
        }

        ArrayList<City> cities1 = tour01.getCities();
        ArrayList<City> cities2 = tour02.getCities();

        Tour child = new Tour();

        int idx = 0;
        do {
            City c = cities2.get(idx);
            // if city was found in tour2, add city to child
            child.addCity(idx, cities1.get(idx));
            idx = cities1.indexOf(c); //get next index
        } while (idx != 0);

        for(int i = 0; i < length2; i++) {
            City c = tour02.getCity(i);
            if(!child.containsCity(c)) {
                child.addCity(i, c);
            }
        }

        return child;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}