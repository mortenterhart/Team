package bruteforce;

import base.City;
import base.Tour;
import data.InstanceReader;
import data.TSPLIBReader;
import main.Configuration;

import java.util.*;

public class BruteForce {
    private List<City> availableCities = null;
    private Comparator<City> cityComparator = new CityComparator ();
    private Set<Tour> tourSet = new HashSet<> ();

    private double tourCountLimit = 0;
    private int breakLimit = 1000;
    private int breakCount = 0;

    public BruteForce(List<City> cities, double iterationLimit) {
        availableCities = cities;
        tourCountLimit = iterationLimit;
        fillSet ();
    }

    public void fillSet() {
        Tour baseTour = new Tour ();
        availableCities.sort (cityComparator);
        for (City availableCity : availableCities) {
            baseTour.addCity (availableCity);
        }

        do {
            Tour randomTour = (Tour) baseTour.clone ();
            shuffleTour (randomTour);
            tourSet.add (randomTour);
        } while (tourSet.size () <= tourCountLimit);
    }

    public Tour minimalTour() {
        Tour minimumTour = null;
        double lowestDistance = Double.MAX_VALUE;
        for (Tour testTour : tourSet) {
            double testDistance = testTour.getFitness ();
            if (testDistance < lowestDistance) {
                minimumTour = testTour;
                lowestDistance = testDistance;
                breakCount = 0;
            } else {
                breakCount++;
            }

            if (breakCount < breakLimit) {
                break;
            }
        }
        return minimumTour;
    }

    public void shuffleTour(Tour tour) {
        Collections.shuffle (tour.getCities (), Configuration.instance.mersenneTwister);
    }

    public void setAvailableCities(List<City> availableCities) {
        this.availableCities = availableCities;
    }

    public void setTourCountLimit(double limit) {
        tourCountLimit = limit;
    }

    public void setBreakLimit(int limit) {
        breakLimit = limit;
    }

    public List<City> getAvailableCities() {
        return availableCities;
    }
}
