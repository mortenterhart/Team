package bruteforce;

import base.City;
import base.Tour;
import data.InstanceReader;
import data.TSPLIBReader;
import main.Configuration;

import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;

public class BruteForce {
    private List<City> availableCities = null;
    private Tour bestRoute = null;
    private Comparator<City> cityComparator = new CityComparator ();
    private double bestDistance = Double.MAX_VALUE;

    private Tour currentRoute = null;

    private double iterationLimit = 4e9;
    private int breakLimit = 1000;
    private int breakCount = 0;

    public BruteForce() {
        loadCities ();
    }

    public void initRoute() {
        availableCities.sort (cityComparator);
    }

    public Tour findBestRoute() {
        for (int i = 0; i < iterationLimit; i++) {
            nextPermutation ();
            double routeDistance = currentRoute.getFitness ();
            if (routeDistance < bestDistance) {
                bestRoute = currentRoute;
                bestDistance = routeDistance;
            } else {
                breakCount++;
            }

            if (breakCount > breakLimit) {
                return bestRoute;
            }
        }
        return bestRoute;
    }

    private void nextPermutation() {
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

    public List<City> getAvailableCities() {
        return availableCities;
    }

    public static void main(String[] args) {
        BruteForce bruteForceApplication = new BruteForce ();
        bruteForceApplication.findBestRoute ();
    }
}
