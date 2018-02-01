package test.utilities;

import base.City;
import base.Population;
import base.Tour;
import main.Application;
import main.Configuration;
import org.junit.Before;
import org.junit.Test;
import random.MersenneTwisterFast;
import utilities.RandomPopulationGenerator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.junit.Assert.assertTrue;

public class RandomPopulationGeneratorTest {
    private Application application;
    private List<City> availableCities;
    private Tour baseTour;
    private MersenneTwisterFast randomGenerator;

    private int populationSize = 26;

    /**
     * Loads the available cities from the Application instance and
     * constructs objects for the list of cities, the base tour which
     * is used as comparison instance in the tests and the Mersenne
     * Twister Fast random generator
     */
    @Before
    public void initialize() {
        application = new Application();
        application.loadData();

        availableCities = application.getAvailableCities();

        baseTour = new Tour();
        baseTour.setCities(new ArrayList<>(availableCities));

        randomGenerator = Configuration.instance.mersenneTwister;
    }

    /*
     *  Tests for RandomPopulationGenerator.randomPopulation(List<City> cities, int populationSize)
     *  ===========================================================================================
     */

    /**
     * Tests whether the returned random population is not null
     *
     * expected result: true
     */
    @Test
    public void testRandomPopulationNotNull() {
        Population population = RandomPopulationGenerator.randomPopulation(availableCities, populationSize);
        assertNotNull(population);
    }

    /**
     * Tests whether the returned random population has the given size
     *
     * expected result: 26 (populationSize)
     */
    @Test
    public void testRandomPopulationCorrectSize() {
        Population population = RandomPopulationGenerator.randomPopulation(availableCities, populationSize);
        assertEquals(populationSize, population.getTours().size());
    }

    /**
     * Tests whether a random tour out of the returned random population has the
     * given amount of cities
     *
     * expected result: 280
     */
    @Test
    public void testRandomPopulationTourHasCorrectLength() {
        Population population = RandomPopulationGenerator.randomPopulation(availableCities, populationSize);
        Tour randomTour = population.getTours().get(randomGenerator.nextInt(0, populationSize - 1));
        assertEquals(availableCities.size(), randomTour.getSize());
    }

    /**
     * Tests whether a random tour out of the returned random population
     * contains the city with id 42
     *
     * expected result: true
     */
    @Test
    public void testRandomPopulationTourContainsCity42() {
        Population population = RandomPopulationGenerator.randomPopulation(availableCities, populationSize);
        Tour randomTour = population.getTours().get(randomGenerator.nextInt(0, populationSize - 1));
        assertTrue(randomTour.containsCity(new City(42, 0.0, 0.0)));
    }

    /**
     * Tests whether a random tour out of the returned random population
     * contains the city with id 281
     *
     * expected result: false
     */
    @Test
    public void testRandomPopulationTourNotContainsCity281() {
        Population population = RandomPopulationGenerator.randomPopulation(availableCities, populationSize);
        Tour randomTour = population.getTours().get(randomGenerator.nextInt(0, populationSize - 1));
        assertFalse(randomTour.containsCity(new City(281, 0.0, 0.0)));
    }

    /**
     * Tests whether a random tour out of the returned random population
     * contains all cities that were
     */
    @Test
    public void testRandomPopulationTourContainsAllAvailableCities() {
        Population population = RandomPopulationGenerator.randomPopulation(availableCities, populationSize);
        Tour randomTour = population.getTours().get(randomGenerator.nextInt(0, populationSize - 1));
        assertTrue(tourContainsAllCities(randomTour, baseTour));
    }

    //@Test
    //public void

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
