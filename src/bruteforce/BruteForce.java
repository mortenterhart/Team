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

    public BruteForce() {
        loadCities ();
    }

    public void fillSet() {
        Tour baseTour = new Tour ();
        availableCities.sort (cityComparator);
        for (int i = 1; i <= availableCities.size (); i++) {
            baseTour.addCity (availableCities.get (i));
        }

        do {
            Collections.shuffle (baseTour.getCities (), Configuration.instance.random);
            tourSet.add (baseTour);
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

    private void loadCities() {
        System.out.println ("--- BruteForceAlgorithm.loadData()");
        InstanceReader instanceReader = new InstanceReader (Configuration.instance.dataFilePath);
        instanceReader.open ();
        TSPLIBReader tspLibReader = new TSPLIBReader (instanceReader);

        availableCities = tspLibReader.getCities ();
        System.out.println ("availableCities (size) : " + availableCities.size ());

        instanceReader.close ();

        System.out.println ();
    }

    public void setTourCountLimit(double limit) {
        tourCountLimit = limit;
    }

    public List<City> getAvailableCities() {
        return availableCities;
    }

    public static void main(String[] args) {
        BruteForce bruteForceApplication = new BruteForce ();
        bruteForceApplication.setTourCountLimit (4e9);
    }
}
