package test.crossover;

import crossover.HeuristicCrossover;
import crossover.ICrossover;
import org.junit.Test;

public class HeuristicCrossoverTestBasic extends CrossoverTest_Basic {

    protected ICrossover getTestInstance() {
        return new HeuristicCrossover();
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