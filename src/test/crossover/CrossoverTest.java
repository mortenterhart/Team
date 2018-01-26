package test.crossover;

//import base.City;
//import base.Tour;
//import crossover.ICrossover;
//import org.junit.jupiter.api.Test;
//import static org.junit.Assert.*;
//import random.MersenneTwisterFast;
//
//import java.util.ArrayList;


public class CrossoverTest {

   /* Tour _tour1;
    Tour _tour2;


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
    private void TestPMCrossover(Tour tour1, Tour tour2)
    {
        InitializeTest();

        ICrossover crossover = new crossover.PartiallyMatchedCrossover();

        Tour tour3 = crossover.doCrossover(tour1, tour2);

        assertTrue(tour3.getSize() == tour1.getSize());
    }

    @Test
    private void TestPBCrossover(Tour tour1, Tour tour2)
    {
        InitializeTest();

        ICrossover crossover = new crossover.PositionBasedCrossover();

        Tour tour3 = crossover.doCrossover(tour1, tour2);

        assertTrue(tour3.getSize() == tour1.getSize());
    }

    @Test
    private void TestSTCrossover(Tour tour1, Tour tour2)
    {
        InitializeTest();

        ICrossover crossover = new crossover.SubTourExchangeCrossover();

        Tour tour3 = crossover.doCrossover(tour1, tour2);

        assertTrue(tour3.getSize() == tour1.getSize());
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
    }*/

}