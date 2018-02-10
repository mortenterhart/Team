package main;

import base.City;
import base.Population;
import base.Tour;
import crossover.ICrossover;
import data.HSQLDBManager;
import logging.LogEngine;
import mutation.IMutation;
import scenario.base.BestScenarioFitness;
import scenario.base.TSPScenario;
import selection.ISelection;
import utilities.MinimalTourDetector;
import utilities.RandomPopulationGenerator;

import java.util.List;
import java.util.ListIterator;

public class GeneticAlgorithm {

    private BestScenarioFitness bestScenario;
    private final List<City> availableCities;

    private int sameFitnessCounter = 0;
    private long databaseIdCounter = 0;

    private long totalNumberOfCrossovers = 0;
    private long totalNumberOfMutations = 0;

    public GeneticAlgorithm(List<City> availableCities) {
        this.availableCities = availableCities;
        this.bestScenario = new BestScenarioFitness();
    }

    public void startScenario(int scenarioId, ISelection selection, ICrossover crossover,
                              IMutation mutation, double crossoverRatio, double mutationRatio) {
        this.executeScenario(new TSPScenario(scenarioId, selection, crossover, mutation,
                crossoverRatio, mutationRatio, Configuration.instance.numberOfIterations));
    }

    public void executeScenario(TSPScenario scenario) {
        logScenarioHeader(scenario);

        double previousFitness = Double.POSITIVE_INFINITY;

        Population population = RandomPopulationGenerator.randomPopulation(availableCities,
                Configuration.instance.initialNumberOfIndividuums);
        double bestFitness = MinimalTourDetector.minimalTourIn(population).getFitness();
        log("  Initial best fitness: " + formatFitness(bestFitness));

        int generationCounter = 1;
        double generationMinimumFitness = bestFitness;
        // Evolution Loop
        while (!isIterationLimitReached(generationCounter) &&
                minimumNotChanged(previousFitness, generationMinimumFitness, Configuration.instance.noChangeLimit)) {

            // Selection
            List<Tour> selectedTours = scenario.getSelection().doSelection(population);

            // Crossover
            performCrossover(selectedTours, scenario, population);

            // Mutation
            performMutation(scenario, population);

            // Evaluation
            previousFitness = generationMinimumFitness;
            generationMinimumFitness = MinimalTourDetector.minimalTourIn(population).getFitness();

            if (Configuration.instance.isDebug) {
                System.out.println("  Minimal Fitness in generation " + generationCounter + ": " +
                        formatFitness(generationMinimumFitness));
                System.out.println("  > Same Fitness since " + sameFitnessCounter + " iterations");
            }

            // Insert the minimum fitness of the generation into the database
            insertIntoDatabase(scenario.getNumberOfIterations(), generationMinimumFitness, scenario.getScenarioId());

            if (generationMinimumFitness < bestFitness) {
                bestFitness = generationMinimumFitness;
                log("  --- New best fitness: " + formatFitness(bestFitness));
            }

            generationCounter++;
            System.out.println();
        } // End Evolution Loop

        logScenarioResult(scenario, bestFitness);

        if (bestFitness < bestScenario.getFitness()) {
            bestScenario.setScenario(scenario);
            bestScenario.setFitness(bestFitness);
            log("--- New global fitness in scenario " + scenario.getScenarioId() + ": " +
                    formatFitness(bestFitness));
        }
    }

    private void performCrossover(List<Tour> selectedTours, TSPScenario scenario, Population population) {
        ListIterator<Tour> listIterator = selectedTours.listIterator();
        while (listIterator.hasNext()) {
            Tour tour1 = listIterator.next();
            Tour tour2 = null;
            if (listIterator.hasNext()) {
                tour2 = listIterator.next();
                if (Configuration.instance.mersenneTwister.nextBoolean(scenario.getCrossoverRatio())) {
                    Tour childTour = scenario.getCrossover().doCrossover(tour1, tour2);
                    population.getTours().add(childTour);
                    totalNumberOfCrossovers++;

                    if (Configuration.instance.isDebug) {
                        System.out.println("  Doing crossover with tour " + formatFitness(tour1) +
                                " and tour " + formatFitness(tour2));
                        System.out.println("  Result: " + formatFitness(childTour));
                        System.out.println();
                    }
                }
            }
        }
    }

    private void performMutation(TSPScenario scenario, Population population) {
        ListIterator<Tour> listIterator = population.getTours().listIterator();
        while (listIterator.hasNext()) {
            Tour tour = listIterator.next();
            if (Configuration.instance.mersenneTwister.nextBoolean(scenario.getMutationRatio())) {
                Tour mutatedTour = scenario.getMutation().doMutation(tour);
                listIterator.set(mutatedTour);
                totalNumberOfMutations++;

                if (Configuration.instance.isDebug) {
                    System.out.println("  Performing mutation with tour " + formatFitness(tour));
                    System.out.println("  Result: " + formatFitness(mutatedTour));
                    System.out.println();
                }
            }
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

    public BestScenarioFitness getBestScenario() {
        return bestScenario;
    }

    public long getDatabaseIdCounter() {
        return databaseIdCounter;
    }

    public long getTotalNumberOfCrossovers() {
        return totalNumberOfCrossovers;
    }

    public long getTotalNumberOfMutations() {
        return totalNumberOfMutations;
    }

    private String formatFitness(Tour tour) {
        return formatFitness(tour.getFitness());
    }

    private String formatFitness(double fitness) {
        return String.format("%.4f", fitness);
    }

    private void insertIntoDatabase(long iteration, double fitness, int scenarioId) {
        HSQLDBManager.instance.update(HSQLDBManager.instance.buildSQLStatement(databaseIdCounter, iteration,
                fitness, scenarioId));
        databaseIdCounter++;
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

    private void logScenarioHeader(TSPScenario scenario) {
        log("--- Scenario " + String.format("%02d", scenario.getScenarioId()));
        log("selectionAlgorithm: " + scenario.getSelection().getClass().getSimpleName());
        log("crossoverAlgorithm: " + scenario.getCrossover().getClass().getSimpleName());
        log("mutationAlgorithm:  " + scenario.getMutation().getClass().getSimpleName());
        log("crossoverRatio:     " + scenario.getCrossoverRatio());
        log("mutationRatio:      " + scenario.getMutationRatio());
        log("==================================");
    }

    private void logScenarioResult(TSPScenario scenario, double bestFitness) {
        log("==================================");
        log("Best fitness of scenario " + scenario.getScenarioId() + ": " + formatFitness(bestFitness));
        log("--- End Scenario " + scenario.getScenarioId());
        logNewLine();
    }

    public void logAlgorithmCounters() {
        log("Total number of crossovers and mutations:");
        log("   > total amount of crossovers: " + totalNumberOfCrossovers);
        log("   > total amount of mutations:  " + totalNumberOfMutations);
        logNewLine();
    }
}
