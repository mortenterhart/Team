package test.crossover;

import base.City;
import base.Tour;
import crossover.ICrossover;
import org.junit.jupiter.api.Test;
import random.MersenneTwisterFast;

import java.util.ArrayList;

public class CrossoverTest {

    @Test
    public void InitializeTest()
    {
        MersenneTwisterFast random = new MersenneTwisterFast();

        ArrayList<City> cities = new ArrayList<>();
        for (int i = 0; i < 50; i++)
        {
            cities.add(new City(i, random.nextInt(0, 1000), random.nextInt(0, 1000)));
        }

        Tour test1 = new Tour();
        test1.setCities(cities);
        Tour test2 = new Tour();
        test2.setCities(ShuffleList(cities, random));

        TestPMCrossover(test1, test2);
        TestPBCrossover(test1, test2);
        TestSTCrossover(test1, test2);
    }

    private void TestPMCrossover(Tour tour1, Tour tour2)
    {
        ICrossover crossover = new crossover.PartiallyMatchedCrossover();

        Tour tour3 = crossover.doCrossover(tour1, tour2);
    }

    private void TestPBCrossover(Tour tour1, Tour tour2)
    {
        ICrossover crossover = new crossover.PositionBasedCrossover();

        Tour tour3 = crossover.doCrossover(tour1, tour2);
    }

    private void TestSTCrossover(Tour tour1, Tour tour2)
    {
        ICrossover crossover = new crossover.SubTourExchangeCrossover();

        Tour tour3 = crossover.doCrossover(tour1, tour2);
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