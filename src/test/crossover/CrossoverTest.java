package test.crossover;

import base.City;
import base.Tour;
import crossover.ICrossover;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import random.MersenneTwisterFast;

import java.util.ArrayList;


public class CrossoverTest {

    Tour _tour1;
    Tour _tour2;

    @Before
    public void InitializeTest()
    {
        MersenneTwisterFast random = new MersenneTwisterFast();

        ArrayList<City> cities = new ArrayList<>();
        for (int i = 0; i < 50; i++)
        {
            cities.add(new City(i, random.nextInt(0, 1000), random.nextInt(0, 1000)));
        }

        _tour1 = new Tour();
        _tour1.setCities(cities);
        _tour2 = new Tour();
        _tour2.setCities(ShuffleList(cities, random));
    }

    @Test
    public void TestPMCrossover()
    {
        InitializeTest();

        ICrossover crossover = new crossover.PartiallyMatchedCrossover();

        Tour tour3 = crossover.doCrossover(_tour1, _tour2);

        assertTrue(tour3.getSize() == _tour1.getSize());
    }

    @Test
    public void TestPBCrossover()
    {
        InitializeTest();

        ICrossover crossover = new crossover.PositionBasedCrossover();

        Tour tour3 = crossover.doCrossover(_tour1, _tour2);

        assertTrue(tour3.getSize() == _tour1.getSize());
    }

    @Test
    public void TestSTCrossover()
    {
        InitializeTest();

        ICrossover crossover = new crossover.SubTourExchangeCrossover();

        Tour tour3 = crossover.doCrossover(_tour1, _tour2);

        assertTrue(tour3.getSize() == _tour1.getSize());
    }






    private ArrayList<City> ShuffleList(ArrayList<City> list, MersenneTwisterFast random)
    {
        ArrayList<City> shuffledList = new ArrayList<>();

        shuffledList.add(list.get(0));

        for (int i = 1; i < list.size(); i++)
        {
            int position = random.nextInt(0, shuffledList.size()-1);

            shuffledList.add(position, list.get(i));
        }

        return shuffledList;
    }

}