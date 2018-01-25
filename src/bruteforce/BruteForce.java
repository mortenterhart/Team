package bruteforce;

import base.City;
import base.Tour;
import data.InstanceReader;
import data.TSPLIBReader;
import main.Configuration;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BruteForce {
    private List<City> availableCities = null;
    private Comparator<City> cityComparator = new CityComparator ();
    private Set<Tour> tourSet = new HashSet<> ();

    private double tourCountLimit = 4e9;
    private int breakLimit = 1000;
    private int breakCount = 0;

    public BruteForce() {
        loadCities ();
    }

    public void fillSet() {
        Tour baseTour = new Tour();
        availableCities.sort (cityComparator);
        for (int i = 1; i <= availableCities.size(); i++) {
            baseTour.addCity (availableCities.get(i));
        }


        do {

        } while()
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
