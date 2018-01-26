package test.crossover;

import crossover.CycleCrossover;
import crossover.ICrossover;
import org.junit.Test;

public class CycleCrossoverTest extends CrossoverTest {


    protected ICrossover getTestInstance() {
        return new CycleCrossover();
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