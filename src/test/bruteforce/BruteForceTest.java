package test.bruteforce;

import base.City;
import base.Tour;
import bruteforce.BruteForce;
import main.Application;
import main.Configuration;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class BruteForceTest {
    private Application application;
    private BruteForce bruteForce;
    private List<City> availableCities;
    private int numberIndividuals = 1000;
    private Tour sortedBaseTour;

    @Before
    public void initialize() {
        application = new Application();
        application.loadData();
        availableCities = application.getAvailableCities();

        bruteForce = new BruteForce(availableCities);
        bruteForce.setTourCountLimit(numberIndividuals);
        bruteForce.generateRandomTours();

        sortedBaseTour = new Tour();
        sortedBaseTour.setCities((ArrayList<City>) availableCities);
        bruteForce.sortTourByCities(sortedBaseTour);
    }

    @Test
    public void testGenerateRandomToursNotNull() {
        List<Tour> randomTours = bruteForce.getRandomTours();
        assertNotNull(randomTours);
    }

    @Test
    public void testGenerateRandomToursCorrectLength() {
        List<Tour> randomTours = bruteForce.getRandomTours();
        assertEquals(numberIndividuals, randomTours.size(), 0);
    }

    @Test
    public void testGenerateRandomToursContainsAllCities() {
        List<Tour> randomTours = bruteForce.getRandomTours();
        Tour testTour = randomTours.get(Configuration.instance.mersenneTwister.nextInt(0, randomTours.size() - 1));
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

    private boolean tourContainsAllCities(Tour t1, Tour t2) {
        return t1.getCities().containsAll(t2.getCities()) && t2.getCities().containsAll(t1.getCities());
    }

}
