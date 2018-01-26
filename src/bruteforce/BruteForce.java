package bruteforce;

import base.City;
import base.Tour;
import main.Configuration;

import java.util.*;

public class BruteForce {
    private List<City> availableCities;
    private Comparator<City> cityComparator;
    private List<Tour> randomTours;

    private double tourCountLimit = 0;
    private int breakLimit = 1000;
    private int breakCount = 0;

    public BruteForce(List<City> cities, double numberOfTours) {
        availableCities = cities;
        tourCountLimit = numberOfTours;
        cityComparator = new CityComparator ();
        randomTours = generateRandomTours ();
    }

    public List<Tour> generateRandomTours() {
        Tour baseTour = new Tour ();
        availableCities.sort (cityComparator);
        for (City availableCity : availableCities) {
            baseTour.addCity (availableCity);
        }

        Set<Tour> tourSet = new HashSet<> ();
        do {
            Tour randomTour = (Tour) baseTour.clone ();
            shuffleTour (randomTour);
            System.out.println("Fitness of random Tour: " + randomTour.getFitness());
            tourSet.add (randomTour);
        } while (tourSet.size () <= tourCountLimit);
        return new ArrayList<Tour> (tourSet);
    }

    private Tour minimalDistanceTourInRange(double fromIndex, double toIndex) {
        Tour minimumTour = null;
        double lowestDistance = Double.MAX_VALUE;

        for (double index = fromIndex; index < toIndex; index++) {
            Tour testTour = randomTours.get((int) index);
            System.out.println (testTour.toString ());
            double testDistance = testTour.getFitness ();
            System.out.println (testDistance + " < " + lowestDistance);
            if (testDistance < lowestDistance) {
                //System.out.println ("TestDistance " + testDistance + " is lower than lowestDistance " + lowestDistance);
                minimumTour = testTour;
                lowestDistance = testDistance;
                breakCount = 0;
            } else {
                breakCount++;
                // System.out.println ("Increment breakCount");
            }

            if (breakCount > breakLimit) {
                //System.out.println ("breakCount: " + breakCount);
                //System.out.println ("breakLimit: " + breakLimit);
                break;
            }
        }
        return minimumTour;
    }

    public Tour minimalTourAll() {
        return minimalDistanceTourInRange(0, tourCountLimit);
    }

    public Tour minimalTourTop25() {
        return minimalDistanceTourInRange(0, tourCountLimit / 4);
    }

    public Tour minimalTourLast25() {
        return minimalDistanceTourInRange(tourCountLimit * (3 / 4), tourCountLimit);
    }

    public Tour minimalTourMiddle50() {
        return minimalDistanceTourInRange(tourCountLimit / 4, tourCountLimit * (3 / 4));
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
