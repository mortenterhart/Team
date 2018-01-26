package test.crossover;

import base.City;
import base.Tour;
import crossover.ICrossover;
import data.InstanceReader;
import data.TSPLIBReader;
import main.Configuration;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public abstract class CrossoverTest {


    private TestTourGenerator tourGenerator = new TestTourGenerator();

    protected void checkSizeAllCities() {
        checkSize(getTestInstance(), tourGenerator.generateTourAllCities(), tourGenerator.generateTourAllCities());
    }

    protected void checkSizeTestCities() {
        checkSize(getTestInstance(), tourGenerator.generateTourTestCities(), tourGenerator.generateTourTestCities());
    }

    protected void checkCitiesStillContainedAllCities() {
        checkCitiesStillContained(getTestInstance(), tourGenerator.generateTourAllCities(), tourGenerator.generateTourAllCities());
    }

    protected void checkCitiesStillContainedTestCities() {
        checkCitiesStillContained(getTestInstance(), tourGenerator.generateTourTestCities(), tourGenerator.generateTourTestCities());
    }


    protected abstract ICrossover getTestInstance();

    /**
     * checks whether the created tour has the same amount of cities as the parents
     *
     * @param crossover CrossOver instance to use
     * @param parent1   parent used for generation
     * @param parent2   parent used for generation
     */
    private void checkSize(ICrossover crossover, Tour parent1, Tour parent2) {

        Tour generatedTour = crossover.doCrossover(parent1, parent2);

        Assert.assertEquals(parent1.getSize(), parent2.getSize());
        Assert.assertEquals(parent1.getSize(), generatedTour.getSize());
    }

    /**
     * checks whether all cities of the parents are inside the generated tour, checks for duplicates
     *
     * @param crossover CrossOver instance to use
     * @param parent1   parent used for generation
     * @param parent2   parent used for generation
     */
    private void checkCitiesStillContained(ICrossover crossover, Tour parent1, Tour parent2) {
        Tour generatedTour = crossover.doCrossover(parent1, parent2);

        //since test data is generated randomly,
        // we need to check for duplicates by comparing the size of the generated tour
        Assert.assertEquals(parent1.getSize(), parent2.getSize());
        Assert.assertEquals(parent1.getSize(), generatedTour.getSize());
        //test if all cities of initial tour 1 are in the generated tour.
        for (int i = 0; i < parent1.getSize(); i++) {
            City currentCity = parent1.getCity(i);
            Assert.assertTrue("City not in generated tour: " + currentCity.toString(), generatedTour.containsCity(currentCity));
        }
    }
}
