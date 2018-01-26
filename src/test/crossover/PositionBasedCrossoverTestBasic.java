package test.crossover;

import crossover.ICrossover;
import crossover.PositionBasedCrossover;
import org.junit.Test;

public class PositionBasedCrossoverTestBasic extends CrossoverTest_Basic {


    protected ICrossover getTestInstance() {
        return new PositionBasedCrossover();
    }

    @Test
    public void testSizeAllCities() {
        checkSizeAllCities();
    }

    @Test
    public void testSizeTestCities() {
        checkSizeTestCities();
    }

    @Test
    public void testAllCityStillContained() {
        checkCitiesStillContainedAllCities();
    }

    @Test
    public void testTestCitiesStillContained() {
        checkCitiesStillContainedTestCities();
    }
}