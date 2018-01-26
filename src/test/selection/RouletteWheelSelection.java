package test.selection;


import base.City;
import base.Population;
import base.Tour;
import main.Configuration;
import org.junit.Before;
import org.junit.Test;
import random.MersenneTwisterFast;
import selection.ISelection;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class RouletteWheelSelection {

    /* to debug edit src/main/Application
    public static void main(String... args) {

        test.selection.RouletteWheelSelection test = new test.selection.RouletteWheelSelection();
        test.initializeTest();
        test.resultNotNull();
        test.countSelectedIndividuums();

        Application application = new Application();
        application.startupHSQLDB();
        application.loadData();
        application.initConfiguration();
        application.execute();
        application.shutdownHSQLDB()
    };
   */

    public static void main(String... args) {
        test.selection.RouletteWheelSelection test = new test.selection.RouletteWheelSelection();
        test.initializeTest();
        test.resultNotNull();
        test.countSelectedIndividuums();
    }
    ArrayList<Tour> result = new ArrayList<>();

    @Before
    public void initializeTest(){

        MersenneTwisterFast random = Configuration.instance.mersenneTwisterFast;

        ArrayList<City> cities = new ArrayList<City>();
        ArrayList<Tour> tours = new ArrayList<Tour>();
        for(int i = 0; i < 50; i++){
            cities = new ArrayList<>();
            for (int j = 0; j < 280; j++) {
                cities.add(new City(j, random.nextInt(0, 1000), random.nextInt(0, 1000)));
            }
            Tour tour = new Tour();
            tour.setCities(cities);
            tours.add(tour);
        }

        Population population = new Population();
        population.setTours(tours);

        ISelection selection = new selection.RouletteWheelSelection();
        result = selection.doSelection(population);
    }

    @Test
    public void resultNotNull(){
        assertNotNull(result);
    }

    @Test
    public void countSelectedIndividuums() {
        assertEquals(12,result.size());
    }

}