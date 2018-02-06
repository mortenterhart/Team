package test.mutation;

import base.City;
import base.Tour;
import mutation.IMutation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ExchangeMutation {
    private  Tour tour;

    @Before
    public void initTour(){
        this.tour = new Tour();
        ArrayList<City> cities = new ArrayList<City>();
        for(int i = 0; i<280; i++){
            cities.add(new City(i,i,i));
        }
        this.tour.setCities(cities);
    }

    @Test
    public void checkSize(){
        IMutation exchangeMutation = new mutation.ExchangeMutation();
        this.tour = exchangeMutation.doMutation(this.tour);
        assertEquals(280, tour.getCities().size());
    }

    @Test
    public void checkNotNull(){
        IMutation exchangeMutation = new mutation.ExchangeMutation();
        this.tour = exchangeMutation.doMutation(this.tour);
        assertNotNull(this.tour.getCities());
    }
}