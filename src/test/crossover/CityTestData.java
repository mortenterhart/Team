package test.crossover;

import base.City;
import data.InstanceReader;
import data.TSPLIBReader;
import main.Configuration;

import java.util.ArrayList;

public class CityTestData {
    private ArrayList<City> allCities = new ArrayList<>();
    private ArrayList<City> testCities = new ArrayList<>();

    private CityTestData() {
        readAllCitiesFromDB();
        testCities.add(new City(0, 0, 0));
        testCities.add(new City(1, 1, 2));
        testCities.add(new City(2, 2, 1));
        testCities.add(new City(3, 3, 3));
    }

    public static CityTestData Data = new CityTestData();

    private void readAllCitiesFromDB() {
        InstanceReader instanceReader = new InstanceReader(Configuration.instance.dataFilePath);
        instanceReader.open();
        TSPLIBReader tspLibReader = new TSPLIBReader(instanceReader);
        allCities = tspLibReader.getCities();
    }

    public ArrayList<City> getAllCities() {
        return new ArrayList<>(allCities);
    }

    public ArrayList<City> getTestCities() {
        return new ArrayList<>(testCities);
    }
}
