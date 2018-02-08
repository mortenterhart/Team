package main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import base.City;
import base.Population;
import base.TSPScenario;
import base.Tour;
import bruteforce.BruteForce;
import crossover.*;
import data.HSQLDBManager;
import data.InstanceReader;
import data.TSPLIBReader;
import logging.LogEngine;
import mutation.*;
import selection.ISelection;
import selection.RouletteWheelSelection;
import selection.TournamentSelection;
import utilities.MinimalTourDetector;
import utilities.RandomPopulationGenerator;

public class Application {
    private ArrayList<City> availableCities;
    private double[][] distances;

    private static double bestScenarioFitness = Double.MAX_VALUE;
    private static double bestBruteforceFitness = Double.MAX_VALUE;

    private int sameFitnessCounter = 0;
    private long databaseIdCounter = 0;

    public void startupHSQLDB() {
        HSQLDBManager.instance.startup();
        HSQLDBManager.instance.init();
    }

    public void shutdownHSQLDB() {
        HSQLDBManager.instance.shutdown();
    }

    public void printMatrix(double[][] matrix) {
        DecimalFormat decimalFormat = new DecimalFormat("000.00");

        for (int rowIndex = 0; rowIndex < matrix.length; rowIndex++) {
            for (int columnIndex = 0; columnIndex < matrix[0].length; columnIndex++)
                System.out.print(decimalFormat.format(matrix[rowIndex][columnIndex]) + "\t");
            System.out.println();
        }
    }

    public void loadData() {
        System.out.println("--- GeneticAlgorithm.loadData()");
        InstanceReader instanceReader = new InstanceReader(Configuration.instance.dataFilePath);
        instanceReader.open();
        TSPLIBReader tspLibReader = new TSPLIBReader(instanceReader);

        availableCities = tspLibReader.getCities();
        System.out.println("availableCities (size) : " + availableCities.size());

        distances = tspLibReader.getDistances();
        printMatrix(distances);

        instanceReader.close();

        System.out.println();
    }

    public void initConfiguration() {
        System.out.println("--- GeneticAlgorithm.initConfiguration()");
        System.out.println();
    }

    public void log(String message) {
        System.out.println(message);
        if (Configuration.instance.writeLogFile) {
            LogEngine.instance.write(message);
        }
    }

    public void logNewLine() {
        System.out.println();
        if (Configuration.instance.writeLogFile) {
            LogEngine.instance.write("");
        }
    }

    public String formatFitness(Tour tour) {
        return String.format("%.3f", tour.getFitness());
    }

    public void startBruteforce() {
        log("==================================");
        log("--- Started Bruteforce algorithm");
        log("numberOfTourElements: " + Configuration.instance.numberOfTourElements);
        logNewLine();

        BruteForce bruteForceApplication = new BruteForce(availableCities);
        bruteForceApplication.generateRandomTours();

        Tour bestTourAll = bruteForceApplication.minimalTourAll();
        bestBruteforceFitness = bestTourAll.getFitness();
        log("Best tour in whole list:        " + bestTourAll.toString());
        log("Fitness of all:                 " + bestTourAll.getFitness());
        HSQLDBManager.instance.update(HSQLDBManager.instance.buildSQLStatement(databaseIdCounter,
                Configuration.instance.numberOfTourElements, bestTourAll.getFitness(), 26));
        databaseIdCounter++;
        logNewLine();

        Tour bestTourTop25 = bruteForceApplication.minimalTourTop25();
        log("Best tour in top 25 segment:    " + bestTourTop25.toString());
        log("Fitness of top 25:              " + bestTourTop25.getFitness());
        HSQLDBManager.instance.update(HSQLDBManager.instance.buildSQLStatement(databaseIdCounter,
                Configuration.instance.numberOfTourElements, bestTourTop25.getFitness(), 26));
        databaseIdCounter++;
        logNewLine();

        Tour bestTourMiddle50 = bruteForceApplication.minimalTourMiddle50();
        log("Best tour in middle 50 segment: " + bestTourMiddle50.toString());
        log("Fitness of middle 50:           " + bestTourMiddle50.getFitness());
        HSQLDBManager.instance.update(HSQLDBManager.instance.buildSQLStatement(databaseIdCounter,
                Configuration.instance.numberOfTourElements, bestTourMiddle50.getFitness(), 26));
        databaseIdCounter++;
        logNewLine();

        Tour bestTourLast25 = bruteForceApplication.minimalTourLast25();
        log("Best tour in last 25 segment:   " + bestTourLast25.toString());
        log("Fitness of last 25:             " + bestTourLast25.getFitness());
        HSQLDBManager.instance.update(HSQLDBManager.instance.buildSQLStatement(databaseIdCounter,
                Configuration.instance.numberOfTourElements, bestTourLast25.getFitness(), 26));
        databaseIdCounter++;
        logNewLine();

        locateBestTour(bestTourAll, bestTourTop25, bestTourMiddle50, bestTourLast25);

        log("--- Finished Bruteforce");
        log("==================================");
    }

    private void locateBestTour(Tour best, Tour top25, Tour middle50, Tour last25) {
        String locatedMessage = "The best found tour by Bruteforce was located ";
        if (best.equals(top25)) {
            locatedMessage += "in the top 25 percent ";
        } else if (best.equals(middle50)) {
            locatedMessage += "in the middle 50 percent ";
        } else if (best.equals(last25)) {
            locatedMessage += "in the last 25 percent ";
        }
        log(locatedMessage + "of the list of " + Configuration.instance.numberOfTourElements + " elements.");
    }

    public void startScenario(int scenarioId, ISelection selection, ICrossover crossover,
                              IMutation mutation, double crossoverRatio, double mutationRatio) {
        this.executeScenario(new TSPScenario(scenarioId, selection, crossover, mutation, crossoverRatio, mutationRatio));
    }

    public void executeScenario(TSPScenario scenario) {
        log("--- Scenario " + String.format("%02d", scenario.getScenarioId()));
        log("selectionAlgorithm: " + scenario.getSelection().getClass().getSimpleName());
        log("crossoverAlgorithm: " + scenario.getCrossover().getClass().getSimpleName());
        log("mutationAlgorithm:  " + scenario.getMutation().getClass().getSimpleName());
        log("crossoverRatio:     " + scenario.getCrossoverRatio());
        log("mutationRatio:      " + scenario.getMutationRatio());
        log("==================================");

        int generationCounter = 1;
        double previousFitness = Double.MAX_VALUE;
        Population population = RandomPopulationGenerator.randomPopulation(availableCities, 26);
        double bestFitness = MinimalTourDetector.minimalTourIn(population).getFitness();
        log("  Initial best fitness: " + String.format("%.3f", bestFitness));

        double generationMinimumFitness = bestFitness;
        // Evolution Loop
        while (!isIterationLimitReached(generationCounter) &&
                minimumNotChanged(previousFitness, generationMinimumFitness, Configuration.instance.noChangeLimit)) {

            // Selection
            List<Tour> selectedTours = scenario.getSelection().doSelection(population);

            // Crossover
            ListIterator<Tour> listIterator = selectedTours.listIterator();
            while (listIterator.hasNext()) {
                Tour tour1 = listIterator.next();
                Tour tour2 = null;
                if (listIterator.hasNext()) {
                    tour2 = listIterator.next();
                    if (Configuration.instance.mersenneTwister.nextBoolean(scenario.getCrossoverRatio())) {
                        Tour childTour = scenario.getCrossover().doCrossover(tour1, tour2);
                        population.getTours().add(childTour);

                        if (Configuration.instance.isDebug) {
                            System.out.println("  Doing crossover with tour " + formatFitness(tour1) +
                                    " and tour " + formatFitness(tour2));
                            System.out.println("  Result: " + formatFitness(childTour));
                            System.out.println();
                        }
                    }
                }
            }

            // Mutation
            listIterator = population.getTours().listIterator();
            while (listIterator.hasNext()) {
                Tour tour = listIterator.next();
                if (Configuration.instance.mersenneTwister.nextBoolean(scenario.getMutationRatio())) {
                    Tour mutatedTour = scenario.getMutation().doMutation(tour);
                    listIterator.set(mutatedTour);

                    if (Configuration.instance.isDebug) {
                        System.out.println("  Performing mutation with tour " + formatFitness(tour));
                        System.out.println("  Result: " + formatFitness(mutatedTour));
                        System.out.println();
                    }
                }
            }

            // Evaluation
            previousFitness = generationMinimumFitness;
            Tour bestGenerationTour = MinimalTourDetector.minimalTourIn(population);
            generationMinimumFitness = bestGenerationTour.getFitness();

            if (Configuration.instance.isDebug) {
                System.out.println("  Minimal Fitness in generation " + generationCounter + ": " +
                        String.format("%.3f", generationMinimumFitness));
                System.out.println("  > Same Fitness since " + sameFitnessCounter + " iterations");
            }

            // Insert the minimum fitness of the generation into the database
            HSQLDBManager.instance.update(HSQLDBManager.instance.buildSQLStatement(databaseIdCounter,
                    Configuration.instance.numberOfIterations, generationMinimumFitness, scenario.getScenarioId()));

            if (generationMinimumFitness < bestFitness) {
                bestFitness = generationMinimumFitness;
                log("  --- New best fitness: " + String.format("%.3f", bestFitness));
            }

            generationCounter++;
            databaseIdCounter++;
            System.out.println();
        }

        log("==================================");
        log("Best fitness of scenario " + scenario.getScenarioId() + ": " + bestFitness);
        log("--- End Scenario " + scenario.getScenarioId());
        logNewLine();

        if (bestFitness < bestScenarioFitness) {
            bestScenarioFitness = bestFitness;
            log("--- New global fitness in scenario " + scenario.getScenarioId() + ": " + String.format("%.3f", bestFitness));
        }
    }

    private boolean isIterationLimitReached(int generation) {
        return generation >= Configuration.instance.numberOfIterations;
    }

    private boolean minimumNotChanged(double previousFitness, double minimumFitness, int limit) {
        double epsilon = 0.001;
        if (Math.abs(minimumFitness - previousFitness) < epsilon) {
            sameFitnessCounter++;
        } else {
            sameFitnessCounter = 0;
        }

        return sameFitnessCounter < limit;
    }

    public List<City> getAvailableCities() {
        return availableCities;
    }

    public static void main(String... args) {
        Application application = new Application();
        application.startupHSQLDB();
        application.loadData();
        LogEngine.instance.init();
        application.initConfiguration();

        int scenarioCounter = 1;
        // Scenario 1
        application.startScenario(scenarioCounter, new RouletteWheelSelection(), new PartiallyMatchedCrossover(),
                new ExchangeMutation(), 0.8, 0.005);

        // Scenario 2
        scenarioCounter++;
        application.startScenario(scenarioCounter, new RouletteWheelSelection(), new PartiallyMatchedCrossover(),
                new ExchangeMutation(), 0.7, 0.005);

        // Scenario 3
        scenarioCounter++;
        application.startScenario(scenarioCounter, new RouletteWheelSelection(), new PartiallyMatchedCrossover(),
                new ExchangeMutation(), 0.6, 0.005);

        // Scenario 4
        scenarioCounter++;
        application.startScenario(scenarioCounter, new RouletteWheelSelection(), new PartiallyMatchedCrossover(),
                new ExchangeMutation(), 0.8, 0.0005);

        // Scenario 5
        scenarioCounter++;
        application.startScenario(scenarioCounter, new RouletteWheelSelection(), new PartiallyMatchedCrossover(),
                new ExchangeMutation(), 0.7, 0.0005);

        // Scenario 6
        scenarioCounter++;
        application.startScenario(scenarioCounter, new RouletteWheelSelection(), new PartiallyMatchedCrossover(),
                new ExchangeMutation(), 0.6, 0.0005);

        // Scenario 7
        scenarioCounter++;
        application.startScenario(scenarioCounter, new RouletteWheelSelection(), new PartiallyMatchedCrossover(),
                new DisplacementMutation(), 0.7, 0.0005);

        // Scenario 8
        scenarioCounter++;
        application.startScenario(scenarioCounter, new TournamentSelection(), new PartiallyMatchedCrossover(),
                new DisplacementMutation(), 0.7, 0.0005);

        // Scenario 9
        scenarioCounter++;
        application.startScenario(scenarioCounter, new RouletteWheelSelection(), new PartiallyMatchedCrossover(),
                new HeuristicMutation(), 0.7, 0.0005);

        // Scenario 10
        scenarioCounter++;
        application.startScenario(scenarioCounter, new RouletteWheelSelection(), new PartiallyMatchedCrossover(),
                new InsertionMutation(), 0.7, 0.0005);

        // Scenario 11
        scenarioCounter++;
        application.startScenario(scenarioCounter, new RouletteWheelSelection(), new PartiallyMatchedCrossover(),
                new InversionMutation(), 0.7, 0.0005);

        // Scenario 12
        scenarioCounter++;
        application.startScenario(scenarioCounter, new RouletteWheelSelection(), new CycleCrossover(),
                new ExchangeMutation(), 0.7, 0.005);

        // Scenario 13
        scenarioCounter++;
        application.startScenario(scenarioCounter, new RouletteWheelSelection(), new CycleCrossover(),
                new ExchangeMutation(), 0.7, 0.0005);

        // Scenario 14
        scenarioCounter++;
        application.startScenario(scenarioCounter, new TournamentSelection(), new CycleCrossover(),
                new ExchangeMutation(), 0.7, 0.0005);

        // Scenario 15
        scenarioCounter++;
        application.startScenario(scenarioCounter, new RouletteWheelSelection(), new HeuristicCrossover(),
                new ExchangeMutation(), 0.7, 0.0005);

        // Scenario 16
        scenarioCounter++;
        application.startScenario(scenarioCounter, new RouletteWheelSelection(), new HeuristicCrossover(),
                new InsertionMutation(), 0.7, 0.0005);

        // Scenario 17
        scenarioCounter++;
        application.startScenario(scenarioCounter, new RouletteWheelSelection(), new HeuristicCrossover(),
                new InversionMutation(), 0.7, 0.0005);

        // Scenario 18
        scenarioCounter++;
        application.startScenario(scenarioCounter, new TournamentSelection(), new OrderedCrossover(),
                new ExchangeMutation(), 0.7, 0.0005);

        // Scenario 19
        scenarioCounter++;
        application.startScenario(scenarioCounter, new RouletteWheelSelection(), new OrderedCrossover(),
                new DisplacementMutation(), 0.7, 0.0005);

        // Scenario 20
        scenarioCounter++;
        application.startScenario(scenarioCounter, new RouletteWheelSelection(), new OrderedCrossover(),
                new InsertionMutation(), 0.7, 0.0005);

        // Scenario 21
        scenarioCounter++;
        application.startScenario(scenarioCounter, new TournamentSelection(), new PositionBasedCrossover(),
                new ExchangeMutation(), 0.7, 0.0005);

        // Scenario 22
        scenarioCounter++;
        application.startScenario(scenarioCounter, new RouletteWheelSelection(), new PositionBasedCrossover(),
                new HeuristicMutation(), 0.7, 0.0005);

        // Scenario 23
        scenarioCounter++;
        application.startScenario(scenarioCounter, new TournamentSelection(), new PositionBasedCrossover(),
                new InversionMutation(), 0.7, 0.0005);

        // Scenario 24
        scenarioCounter++;
        application.startScenario(scenarioCounter, new TournamentSelection(), new SubTourExchangeCrossover(),
                new ExchangeMutation(), 0.7, 0.0005);

        // Scenario 25
        scenarioCounter++;
        application.startScenario(scenarioCounter, new RouletteWheelSelection(), new SubTourExchangeCrossover(),
                new DisplacementMutation(), 0.7, 0.0005);

        // Scenario 26: Bruteforce Algorithm
        application.startBruteforce();

        application.log("==================================");
        application.log("Best Fitness of genetic algorithm:    " + String.format("%.3f", bestScenarioFitness));
        application.log("Best Fitness of bruteforce algorithm: " + String.format("%.3f", bestBruteforceFitness));

        LogEngine.instance.close();
        application.shutdownHSQLDB();
    }
}
