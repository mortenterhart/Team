package bruteforce;

import base.City;
import base.Tour;
import main.Configuration;

import java.io.ObjectInputFilter;
import java.util.*;

public class BruteForce {
    private List<City> availableCities;
    private Comparator<City> cityComparator;
    private List<Tour> randomTours;

    private double tourCountLimit = 0;
    private int breakLimit = 1000;

    public BruteForce() {
        availableCities = new ArrayList<>();
        cityComparator = new CityComparator();
        randomTours = new ArrayList<>();

        tourCountLimit = Configuration.instance.numberOfTourElements;
        breakLimit = Configuration.instance.breakLimit;
    }

    public BruteForce(List<City> cities) {
        this();
        availableCities = cities;
        availableCities.sort(cityComparator);
        randomTours = generateRandomTours();
    }

    public List<Tour> generateRandomTours() {
        Tour baseTour = new Tour();
        initSortedBaseTour(baseTour);

        List<Tour> tourSet = new ArrayList<>();
        do {
            Tour randomTour = (Tour) baseTour.clone();
            shuffleTour(randomTour);
            tourSet.add(randomTour);
        } while (tourSet.size() < tourCountLimit);
        return new ArrayList<Tour>(tourSet);
    }
    
    private Tour minimalDistanceTourInRange(double fromIndex, double toIndex) {
        Tour minimumTour = null;
        double lowestDistance = Double.MAX_VALUE;
        int breakCount = 0;

        for (double index = fromIndex; index < toIndex && breakCount <= breakLimit; index++) {
            Tour testTour = randomTours.get((int) index);
            double testDistance = testTour.getFitness();
            if (testDistance < lowestDistance) {
                minimumTour = testTour;
                lowestDistance = testDistance;
                breakCount = 0;
            } else {
                breakCount++;
            }
        }
        return minimumTour;
    }

    public void initSortedBaseTour(Tour baseTour) {
        baseTour.setCities(new ArrayList<>(availableCities));
    }

    public Tour minimalTourAll() {
        return minimalDistanceTourInRange(0, tourCountLimit);
    }

    public Tour minimalTourTop25() {
        return minimalDistanceTourInRange(0, get25PercentIndex());
    }

    public Tour minimalTourMiddle50() {
        return minimalDistanceTourInRange(get25PercentIndex(), get75PercentIndex());
    }

    public Tour minimalTourLast25() {
        return minimalDistanceTourInRange(get75PercentIndex(), tourCountLimit);
    }


    public static void shuffleTour(Tour tour) {
        Collections.shuffle(tour.getCities(), Configuration.instance.mersenneTwister);
    }

    private double get25PercentIndex() {
        return tourCountLimit / 4.0;
    }

    private double get75PercentIndex() {
        return tourCountLimit * (3.0 / 4.0);
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
