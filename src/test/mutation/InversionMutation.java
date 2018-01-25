package test.mutation;

import base.City;
import base.Tour;
import mutation.IMutation;
import org.junit.Test;

import java.util.ArrayList;

public class InversionMutation {

    @Test
    public void testDoMutation()
    {
        Tour tour = new Tour();
        ArrayList<City> oldCities = new ArrayList<City>();
        ArrayList<City> cities = new ArrayList<City>();
        for(int i = 0; i<10; i++){
            oldCities.add(new City(i,i,i));
            cities.add(new City(i,i,i));
        }

        tour.setCities(cities);

        IMutation inversionMutation = new mutation.InversionMutation();

        tour = inversionMutation.doMutation(tour);

        for(City c : tour.getCities())
        {
            System.out.println(c.toString());
        }

    }


}