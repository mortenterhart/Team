package test.utilities;

import base.City;
import base.Tour;
import bruteforce.BruteForce;
import main.Application;
import org.junit.Before;
import org.junit.Test;
import utilities.RandomPopulationGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class RandomPopulationGeneratorTest {
    private Application application;
    private List<City> availableCities;
    private Tour baseTour;

    @Before
    public void initialize() {
        application = new Application();
        application.loadData();

        availableCities = application.getAvailableCities();

        baseTour = new Tour();
        baseTour.setCities(new ArrayList<>(availableCities));

    }

    @Test
    public void testShuffleTourNotNull() {
        Tour testTour = (Tour) baseTour.clone();
        RandomPopulationGenerator.shuffleTour(testTour);
        assertNotNull(testTour);
    }

    @Test
    public void testShuffleTourSameLengthAfter() {
        Tour testTour = (Tour) baseTour.clone();
        RandomPopulationGenerator.shuffleTour(testTour);
        assertEquals(baseTour.getSize(), testTour.getSize());
    }

    @Test
    public void testShuffleTourContainsCity42() {
        Tour testTour = (Tour) baseTour.clone();
        RandomPopulationGenerator.shuffleTour(testTour);
        assertTrue(testTour.containsCity(new City(42, 0.0, 0.0)));
    }

    @Test
    public void testShuffleTourNotContains281() {
        Tour testTour = (Tour) baseTour.clone();
        RandomPopulationGenerator.shuffleTour(testTour);
        assertFalse(testTour.containsCity(new City(281, 0.0, 0.0)));
    }

    @Test
    public void testShuffleTourContainsAllCities() {
        Tour testTour = (Tour) baseTour.clone();
        RandomPopulationGenerator.shuffleTour(testTour);
        assertTrue(tourContainsAllCities(testTour, baseTour));
    }

    private boolean tourContainsAllCities(Tour t1, Tour t2) {
        return t1.getCities().containsAll(t2.getCities()) && t2.getCities().containsAll(t1.getCities());
    }
}
