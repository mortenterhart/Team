package test.crossover;

import base.City;
import base.Tour;
import crossover.ICrossover;
import org.junit.jupiter.api.Test;
import random.MersenneTwisterFast;

import java.util.ArrayList;

public class PartiallyMatchedCrossover {

    @Test
    public void InitializeTest()
    {
        MersenneTwisterFast random = new MersenneTwisterFast();

        ArrayList<City> cities = new ArrayList<City>();
        for (int i = 0; i < 50; i++)
        {
            cities.add(new City(i, random.nextInt(0, 1000), random.nextInt(0, 1000)));
        }

        Tour test1 = new Tour();
        test1.setCities(cities);
        Tour test2 = new Tour();
        test2.setCities(cities);
    }

    private void TestPMCrossover(Tour tour1, Tour tour2)
    {
        ICrossover crossover = new crossover.PartiallyMatchedCrossover();

        Tour tour3 = crossover.doCrossover(tour1, tour2);
    }


}