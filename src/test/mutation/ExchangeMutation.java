package test.mutation;

import base.City;
import base.Tour;
import mutation.IMutation;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class ExchangeMutation {
    @Test
    public void testExchangeMutation(){

        //init test vaiables
        Tour tour = new Tour();
        ArrayList<City> oldCities = new ArrayList<City>();
        ArrayList<City> cities = new ArrayList<City>();
        for(int i = 0; i<10; i++){
            oldCities.add(new City(i,i,i));
            cities.add(new City(i,i,i));
        }

        tour.setCities(cities);
        IMutation exchangeMutation = new mutation.ExchangeMutation();

        //start test and get response
        tour = exchangeMutation.doMutation(tour);
        ArrayList<City> newCities = tour.getCities();

        //count ids to check if all cities are contained
        int sum = 0;
        for(City c : newCities)
            sum = sum + c.getId();

        Assert.assertEquals(45, sum );

        //check if size is equal
        Assert.assertEquals(newCities.size(), oldCities.size());

        //check if sequence is equal
        Boolean isEqual = true;
        for(int i=0; i<newCities.size(); i++)
            if(newCities.get(i).getX() != oldCities.get(i).getX())
                isEqual = false;

        Assert.assertEquals(false, isEqual);

        int oldId = -1;
        int newId = -1;
        for (int i = 0; i<newCities.size(); i++){
            if (oldCities.get(i).getId() != newCities.get(i).getId())
            {
                oldId = i;
                newId = newCities.get(i).getId();
            }
        }

        Assert.assertEquals(oldId, newCities.get(newId).getId());
    }
}