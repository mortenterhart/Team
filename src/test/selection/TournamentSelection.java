package test.selection;
import base.City;
import base.Population;
import base.Tour;
import org.junit.jupiter.api.Test;
import random.MersenneTwisterFast;
import selection.ISelection;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class TournamentSelection  {
    @Test
    public void InitializeTest()
    {
        MersenneTwisterFast random = new MersenneTwisterFast();
        Population pop = new Population();

        ArrayList<City> cities = new ArrayList<City>();
        for (int i = 0; i < 50; i++)
        {
            cities.add(new City(i, random.nextInt(0, 1000), random.nextInt(0, 1000)));
        }

        Tour test1 = new Tour();
        test1.setCities(cities);
        Tour test2 = new Tour();
        test2.setCities(cities);

        pop.tours.add(test1);
        pop.tours.add(test2);
        pop.tours.add(test1);
        pop.tours.add(test2);
        pop.tours.add(test1);
        pop.tours.add(test2);
        pop.tours.add(test1);
        pop.tours.add(test2);
        pop.tours.add(test1);
        pop.tours.add(test2);
        pop.tours.add(test1);
        pop.tours.add(test2);
        pop.tours.add(test1);
        pop.tours.add(test2);
        pop.tours.add(test1);
        pop.tours.add(test2);
        pop.tours.add(test1);
        pop.tours.add(test2);
        pop.tours.add(test1);
        pop.tours.add(test2);
        TournamentTest(pop);
    }


    public void TournamentTest(Population pop)
    {
        ISelection tour = new selection.TournamentSelection();
        ArrayList<Tour> temp = tour.doSelection(pop);
        assertNotNull(temp);

    }
}