package test.crossover;

import crossover.ICrossover;
import crossover.SubTourExchangeCrossover;
import org.junit.Test;

public class SubTourExchangeCrossoverTest extends CrossoverTest {


    protected ICrossover getTestInstance() {
        return new SubTourExchangeCrossover();
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