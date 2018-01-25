package test.bruteforce;

import base.City;
import bruteforce.BruteForce;
import bruteforce.CityComparator;
import org.junit.Test;

import javax.lang.model.type.IntersectionType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class BruteForceTest {

    @Test
    public void testInitRoute() {
        BruteForce bruteForce = new BruteForce ();
        List<City> availableCities = bruteForce.getAvailableCities ();

        // Expected Value
        List<Integer> expectedCityIds = new ArrayList<> ();
        for (City city : availableCities) {
            expectedCityIds.add (city.getId ());
        }
        Collections.sort(expectedCityIds);

        // Actual Value
        bruteForce.initRoute ();
        List<Integer> actualCityIds = new ArrayList<> ();
        for (City city : bruteForce.getAvailableCities ()) {
            actualCityIds.add (city.getId ());
        }

        assertEquals(expectedCityIds, actualCityIds);
    }
}
