package bruteforce;

import base.City;
import base.Tour;
import main.Configuration;
import utilities.MinimalTourDetector;
import utilities.RandomPopulationGenerator;

import java.util.*;

public class BruteForce {
    private List<City> availableCities;
    private Comparator<City> cityComparator;
    private List<Tour> randomTours;

    private int tourCountLimit = 0;

    public BruteForce() {
        availableCities = new ArrayList<>();
        cityComparator = new CityComparator();
        randomTours = new ArrayList<>();

        tourCountLimit = Configuration.instance.numberOfTourElements;
    }

    public BruteForce(List<City> cities) {
        this();
        availableCities = cities;
        availableCities.sort(cityComparator);
    }

    public void generateRandomTours() {
        randomTours = RandomPopulationGenerator.randomTours(availableCities, tourCountLimit);
    }

    public void sortTourByCities(Tour baseTour) {
        baseTour.getCities().sort(cityComparator);
    }

    public void sortTourByCities(Tour baseTour, Comparator<City> comparator) {
        baseTour.getCities().sort(comparator);
    }

    public Tour minimalTourAll() {
        return MinimalTourDetector.minimalTourIn(randomTours);
    }

    public Tour minimalTourTop25() {
        return MinimalTourDetector.minimalTourIn(randomTours, 0, get25PercentIndex());
    }

    public Tour minimalTourMiddle50() {
        return MinimalTourDetector.minimalTourIn(randomTours, get25PercentIndex(), get75PercentIndex());
    }

    public Tour minimalTourLast25() {
        return MinimalTourDetector.minimalTourIn(randomTours, get75PercentIndex(), tourCountLimit);
    }

    private int get25PercentIndex() {
        return tourCountLimit / 4;
    }

    private int get75PercentIndex() {
        return get25PercentIndex() * 3;
    }

    public void setTourCountLimit(int limit) {
        tourCountLimit = limit;
    }

    public void setAvailableCities(List<City> availableCities) {
        this.availableCities = availableCities;
    }

    public List<City> getAvailableCities() {
        return availableCities;
    }

    public List<Tour> getRandomTours() {
        return randomTours;
    }
}
