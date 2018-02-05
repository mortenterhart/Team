package main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import base.City;
import base.Population;
import base.TSPScenario;
import base.Tour;
import bruteforce.BruteForce;
import crossover.ICrossover;
import crossover.PartiallyMatchedCrossover;
import data.HSQLDBManager;
import data.InstanceReader;
import data.TSPLIBReader;
import mutation.ExchangeMutation;
import mutation.IMutation;
import selection.ISelection;
import selection.RouletteWheelSelection;
import selection.TournamentSelection;
import utilities.MinimalTourDetector;
import utilities.RandomPopulationGenerator;

public class Application {
    private ArrayList<City> availableCities;
    private double[][] distances;

    private double previousFitness = Double.MAX_VALUE;
    private int sameFitnessCounter = 0;

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
        // printMatrix(distances);

        instanceReader.close();

        System.out.println();
    }

    public void initConfiguration() {
        System.out.println("--- GeneticAlgorithm.initConfiguration()");
        System.out.println();
    }

    public void execute(TSPScenario scenario) {
        System.out.println("--- GeneticAlgorithm.execute()");

        int generationCounter = 1;
        Population population = RandomPopulationGenerator.randomPopulation(availableCities, 26);
        double bestFitness = MinimalTourDetector.minimalTourIn(population).getFitness();

        // Evolution Loop
        while (!isIterationLimitReached(generationCounter) &&
                minimumNotChanged(bestFitness, Configuration.instance.noChangeLimit)) {

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
                        population.getTours().add(scenario.getCrossover().doCrossover(tour1, tour2));
                    }
                }
            }

            // Mutation
            listIterator = population.getTours().listIterator();
            while (listIterator.hasNext()) {
                Tour tour = listIterator.next();
                if (Configuration.instance.mersenneTwister.nextBoolean(scenario.getMutationRatio())) {
                    listIterator.set(scenario.getMutation().doMutation(tour));
                }
            }

            // Evaluation
            previousFitness = bestFitness;
            Tour bestTour = MinimalTourDetector.minimalTourIn(population);
            bestFitness = bestTour.getFitness();
            System.out.println("Minimal Fitness in generation " + generationCounter + ": " + bestFitness);
            System.out.println("Same Fitness since " + sameFitnessCounter + " iterations");
            HSQLDBManager.instance.update(HSQLDBManager.instance.buildSQLStatement(generationCounter,
                    Configuration.instance.numberOfIterations, bestFitness, scenario.getScenarioId()));

            generationCounter++;
        }
    }

    private boolean isIterationLimitReached(int generation) {
        return generation >= Configuration.instance.numberOfIterations;
    }

    private boolean minimumNotChanged(double minimumFitness, int limit) {
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

        if (Configuration.instance.startBruteForce) {
            if (Configuration.instance.isDebug) {
                System.out.println("--- Started Bruteforce");
            }

            BruteForce bruteForceApplication = new BruteForce(application.availableCities);
            bruteForceApplication.generateRandomTours();
            Tour bestFoundTour = bruteForceApplication.minimalTourAll();
            System.out.println("\nFitness of best tour: " + bestFoundTour.getFitness());

            if (Configuration.instance.isDebug) {
                System.out.println("--- Finished Bruteforce");
            }
        } else {
            application.initConfiguration();
            int scenarioCounter = 1;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            /*scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));

            scenarioCounter++;
            application.execute(new TSPScenario(scenarioCounter, new PartiallyMatchedCrossover(),
                    new ExchangeMutation(), new RouletteWheelSelection(), 0.8, 0.005));*/

        }
        application.shutdownHSQLDB();
    }
}
