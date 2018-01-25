package mutation;

import base.City;
import base.Tour;
import random.MersenneTwisterFast;

import java.util.ArrayList;

public class InversionMutation implements IMutation {
    public Tour doMutation(Tour tour) {
        MersenneTwisterFast mtf = new MersenneTwisterFast();

        ArrayList<City> cities = tour.getCities();

        int point1 = mtf.nextInt(0, cities.size()-1);
        int point2 = mtf.nextInt(0, cities.size()-1);

        int startPoint;
        int endPoint;

        if(point1<point2)
        {
            startPoint = point1;
            endPoint = point2;
        }else
        {
            startPoint = point2;
            endPoint = point1;
        }
        System.out.println("StartPoint: " + startPoint);
        System.out.println("EndPoint: " + endPoint);

        City tempCity = null;

        for(int i = 0; i<(endPoint-startPoint)/2; i++)
        {
            tempCity = cities.get(startPoint+i);
            cities.set(startPoint+i, cities.get(endPoint-i));
            cities.set(endPoint-i, tempCity);
        }

        return tour;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}