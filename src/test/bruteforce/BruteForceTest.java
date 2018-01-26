package test.bruteforce;

import base.City;
import base.Tour;
import bruteforce.BruteForce;
import main.Application;
import main.Configuration;
import org.junit.Before;
import org.junit.Test;

import java.io.ObjectInputFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class BruteForceTest {
    private Application application;
    private BruteForce bruteForce;
    private double numberIndividuals = 1000;
    private Tour sortedBaseTour;

    @Before
    public void initialize() {
        application = new Application();
        application.loadData();

        bruteForce = new BruteForce(application.getAvailableCities());
        bruteForce.setTourCountLimit(numberIndividuals);

        sortedBaseTour = new Tour();
        bruteForce.initSortedBaseTour(sortedBaseTour);
    }

    @Test
    public void testGenerateRandomToursNotNull() {
        List<Tour> randomTours = bruteForce.generateRandomTours();
        assertNotNull(randomTours);
    }

    @Test
    public void testGenerateRandomToursCorrectLength() {
        List<Tour> randomTours = bruteForce.generateRandomTours();
        assertEquals(numberIndividuals, randomTours.size(), 0);
    }

    @Test
    public void testGenerateRandomToursContainingAllCities() {
        List<Tour> randomTours = bruteForce.generateRandomTours();
        Tour testTour = randomTours.get(Configuration.instance.mersenneTwister.nextInt(0, randomTours.size()));
        assertTrue(tourContainsAllCities(testTour, sortedBaseTour));
    }

    @Test
    public void testShuffleTourNotNull() {
        Tour testTour = (Tour) sortedBaseTour.clone();
        BruteForce.shuffleTour(testTour);
        assertNotNull(testTour);
    }

    @Test
    public void testShuffleTourSameLengthAfter() {
        Tour testTour = (Tour) sortedBaseTour.clone();
        BruteForce.shuffleTour(testTour);
        assertEquals(sortedBaseTour.getSize(), testTour.getSize());
    }

    @Test
    public void testShuffleTourContainsCity42() {
        Tour testTour = (Tour) sortedBaseTour.clone();
        BruteForce.shuffleTour(testTour);
        assertTrue(testTour.containsCity(new City(42, 0.0, 0.0)));
    }

    @Test
    public void testShuffleTourNotContains281() {
        Tour testTour = (Tour) sortedBaseTour.clone();
        BruteForce.shuffleTour(testTour);
        assertFalse(testTour.containsCity(new City(281, 0.0, 0.0)));
    }

    @Test
    public void testShuffleTourContainsAllCities() {
        Tour testTour = (Tour) sortedBaseTour.clone();
        BruteForce.shuffleTour(testTour);
        assertTrue(tourContainsAllCities(testTour, sortedBaseTour));
    }

    @Test
    public void testMinimalTourAllNotNull() {
        Tour minimalTourAll = bruteForce.minimalTourAll();
        assertNotNull(minimalTourAll);
    }

    @Test
    public void testMinimalTourAllCorrectLength() {
        Tour minimalTourAll = bruteForce.minimalTourAll();
        assertEquals(sortedBaseTour.getSize(), minimalTourAll.getSize(), 0.0);
    }

    @Test
    public void testMinimalTourAllContainsAllCities() {
        Tour minimalTourAll = bruteForce.minimalTourAll();
        assertTrue(tourContainsAllCities(minimalTourAll, sortedBaseTour));
    }

    @Test
    public void testMinimalTourTop25NotNull() {
        Tour minimalTourTop25 = bruteForce.minimalTourTop25();
        assertNotNull(minimalTourTop25);
    }

    @Test
    public void testMinimalTourTop25CorrectLength() {
        Tour minimalTourTop25 = bruteForce.minimalTourTop25();
        assertEquals(sortedBaseTour.getSize(), minimalTourTop25.getSize(), 0.0);
    }

    @Test
    public void testMinimalTourTop25ContainsAllCities() {
        Tour minimalTourTop25 = bruteForce.minimalTourTop25();
        assertTrue(tourContainsAllCities(minimalTourTop25, sortedBaseTour));
    }

    @Test
    public void testMinimalTourMiddle50NotNull() {
        Tour minimalTourMiddle50 = bruteForce.minimalTourMiddle50();
        assertNotNull(minimalTourMiddle50);
    }

    @Test
    public void testMinimalTourMiddle50CorrectLength() {
        Tour minimalTourMiddle50 = bruteForce.minimalTourMiddle50();
        assertEquals(sortedBaseTour.getSize(), minimalTourMiddle50.getSize(), 0.0);
    }

    @Test
    public void testMinimalTourMiddle50ContainsAllCities() {
        Tour minimalTourMiddle50 = bruteForce.minimalTourMiddle50();
        assertTrue(tourContainsAllCities(minimalTourMiddle50, sortedBaseTour));
    }

    @Test
    public void testMinimalTourLast25NotNull() {
        Tour minimalTourLast25 = bruteForce.minimalTourLast25();
        assertNotNull(minimalTourLast25);
    }

    @Test
    public void testMinimalTourLast25CorrectLength() {
        Tour minimalTourLast25 = bruteForce.minimalTourLast25();
        assertEquals(sortedBaseTour.getSize(), minimalTourLast25.getSize(),0.0);
    }

    @Test
    public void testMinimalTourLast25ContainsAllCities() {
        Tour minimalTourLast25 = bruteForce.minimalTourLast25();
        assertTrue(tourContainsAllCities(minimalTourLast25, sortedBaseTour));
    }

    public boolean tourContainsAllCities(Tour t1, Tour t2) {
        return t1.getCities().containsAll(t2.getCities()) && t2.getCities().containsAll(t1.getCities());
    }

}
