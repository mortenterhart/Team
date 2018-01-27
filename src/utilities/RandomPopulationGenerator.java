package utilities;

import base.City;
import base.Population;
import base.Tour;
import main.Configuration;

import java.util.*;

public class RandomPopulationGenerator {

    private RandomPopulationGenerator() {}

    public static Population randomPopulation(List<City> cities, int populationSize) {
        return randomPopulation(cities, populationSize, Configuration.instance.mersenneTwister);
    }

    public static Population randomPopulation(List<City> cities, int populationSize, Random randomGenerator) {
        Population randomPopulation = new Population();
        randomPopulation.setTours((ArrayList<Tour>) randomTours(cities, populationSize, randomGenerator));
        return randomPopulation;
    }

    public static List<Tour> randomTours(List<City> cities, int numberOfTours) {
        return randomTours(cities, numberOfTours, Configuration.instance.mersenneTwister);
    }

    public static List<Tour> randomTours(List<City> cities, int numberOfTours, Random randomGenerator) {
        Tour baseTour = new Tour();
        baseTour.setCities(new ArrayList<>(cities));

        Set<Tour> tourSet = new HashSet<>();
        while (tourSet.size() < numberOfTours) {
            Tour randomTour = (Tour) baseTour.clone();
            shuffleTour(randomTour, randomGenerator);
            tourSet.add(randomTour);
        }

        return new ArrayList<Tour>(tourSet);
    }

    public static void shuffleTour(Tour baseTour) {
        shuffleTour(baseTour, Configuration.instance.mersenneTwister);
    }

    public static void shuffleTour(Tour baseTour, Random randomGenerator) {
        Collections.shuffle(baseTour.getCities(), randomGenerator);
    }
}
