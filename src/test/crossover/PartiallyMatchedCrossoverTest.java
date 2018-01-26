package test.crossover;

import crossover.ICrossover;
import crossover.PartiallyMatchedCrossover;
import org.junit.Test;

public class PartiallyMatchedCrossoverTest extends CrossoverTest {


    protected ICrossover getTestInstance() {
        return new PartiallyMatchedCrossover();
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