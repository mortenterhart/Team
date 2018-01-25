package test.crossover;

import base.City;
import base.Tour;
import org.junit.Assert;
import org.junit.Test;

public class CycleCrossover extends CrossoverTest {

    public CycleCrossover() {
        super();
    }

    @Test
    public void testSize() {
        crossover.CycleCrossover cc = new crossover.CycleCrossover();
        checkSize(cc, generateTestTour(), generateTestTour());
    }

    @Test
    public void testAllCityStillContained() {
        crossover.CycleCrossover cc = new crossover.CycleCrossover();
        checkAllCityStillContained(cc, generateTestTour(), generateTestTour());
    }

}