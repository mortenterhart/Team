package test.mutation;

import base.City;
import base.Tour;
import mutation.IMutation;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

public class DisplacementMutation {
    private  Tour tour;

    @Before
    public void initTour(){
        this.tour = new Tour();
        ArrayList<City> cities = new ArrayList<City>();
        for(int i = 0; i<20; i++){
            cities.add(new City(i,i,i));
        }
        this.tour.setCities(cities);
    }

    @Test
    public void checkSize(){
        IMutation displacementMutation = new mutation.DisplacementMutation();
        this.tour = displacementMutation.doMutation(this.tour);
        assertEquals(20, tour.getCities().size());
    }

    @Test
    public void checkNotNull(){
        IMutation displacementMutation = new mutation.DisplacementMutation();
        this.tour = displacementMutation.doMutation(this.tour);
        assertNotNull(this.tour.getCities());
    }

}