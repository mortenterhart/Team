package main;

import base.City;
import base.Tour;
import bruteforce.BruteForce;
import data.HSQLDBManager;
import logging.LogEngine;
import scenario.base.BestScenarioFitness;

import java.util.List;

public class BruteForceAlgorithm {
    private List<City> availableCities;
    private long databaseIdCounter = 0;
    private BestScenarioFitness bestBruteForceFitness;
    private int bruteForceScenarioId = 26;

    public BruteForceAlgorithm(List<City> cities, long previousDatabaseIdCounter) {
        availableCities = cities;
        databaseIdCounter = previousDatabaseIdCounter;
        bestBruteForceFitness = new BestScenarioFitness();
        bestBruteForceFitness.getScenario().setScenarioId(bruteForceScenarioId);
    }

    public void executeBruteForce() {
        log("==================================");
        log("--- Started BruteForce algorithm");
        log("numberOfTourElements: " + Configuration.instance.numberOfTourElements);
        logNewLine();

        BruteForce bruteForceApplication = new BruteForce(availableCities);
        bruteForceApplication.generateRandomTours();

        Tour bestTourAll = bruteForceApplication.minimalTourAll();
        bestBruteForceFitness.setFitness(bestTourAll.getFitness());
        logResult(bestTourAll, "whole list", "all");
        updateDatabase(databaseIdCounter, Configuration.instance.numberOfTourElements,
                bestTourAll.getFitness(), bruteForceScenarioId);

        Tour bestTourTop25 = bruteForceApplication.minimalTourTop25();
        logResult(bestTourTop25, "top 25 segment", "top 25");
        updateDatabase(databaseIdCounter, Configuration.instance.numberOfTourElements,
                bestTourTop25.getFitness(), bruteForceScenarioId);

        Tour bestTourMiddle50 = bruteForceApplication.minimalTourMiddle50();
        logResult(bestTourMiddle50, "middle 50 segment", "middle 50");
        updateDatabase(databaseIdCounter, Configuration.instance.numberOfTourElements,
                bestTourMiddle50.getFitness(), bruteForceScenarioId);

        Tour bestTourLast25 = bruteForceApplication.minimalTourLast25();
        logResult(bestTourLast25, "last 25 segment", "last 25");
        updateDatabase(databaseIdCounter, Configuration.instance.numberOfTourElements,
                bestTourLast25.getFitness(), bruteForceScenarioId);

        locateBestTour(bestTourAll, bestTourTop25, bestTourMiddle50, bestTourLast25);

        log("--- Finished BruteForce");
        log("==================================");
    }

    private void locateBestTour(Tour best, Tour top25, Tour middle50, Tour last25) {
        String locatedMessage = "The best found tour by BruteForce was located ";
        if (best.equals(top25)) {
            locatedMessage += "in the top 25 percent ";
        } else if (best.equals(middle50)) {
            locatedMessage += "in the middle 50 percent ";
        } else if (best.equals(last25)) {
            locatedMessage += "in the last 25 percent ";
        }
        log(locatedMessage + "of the list of " + Configuration.instance.numberOfTourElements + " elements.");
    }

    public BestScenarioFitness getBestBruteForceFitness() {
        return bestBruteForceFitness;
    }

    private void updateDatabase(long id, long iteration, double fitness, int scenarioId) {
        HSQLDBManager.instance.update(HSQLDBManager.instance.buildSQLStatement(id, iteration, fitness, scenarioId));
        databaseIdCounter++;
        logNewLine();
    }

    private String formatFitness(Tour tour) {
        return formatFitness(tour.getFitness());
    }

    private String formatFitness(double fitness) {
        return String.format("%.4f", fitness);
    }

    private void log(String message) {
        System.out.println(message);
        if (Configuration.instance.writeLogFile) {
            LogEngine.instance.write(message);
        }
    }

    private void logNewLine() {
        System.out.println();
        if (Configuration.instance.writeLogFile) {
            LogEngine.instance.write("");
        }
    }

    private void logResult(Tour resultTour, String listSegment, String category) {
        log(String.format("Best tour in %s: %s", listSegment, resultTour.toString()));
        log(String.format("Fitness of %s: %s", category, formatFitness(resultTour)));
    }

}
