package test.crossover;

import crossover.ICrossover;
import crossover.OrderedCrossover;
import org.junit.Test;

public class OrderedCrossoverTest extends CrossoverTest {


    protected ICrossover getTestInstance() {
        return new OrderedCrossover();
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