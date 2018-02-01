package main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import base.City;
import base.Population;
import base.Tour;
import bruteforce.BruteForce;
import crossover.ICrossover;
import data.HSQLDBManager;
import data.InstanceReader;
import data.TSPLIBReader;
import mutation.IMutation;
import selection.ISelection;
import selection.TournamentSelection;
import utilities.MinimalTourDetector;
import utilities.RandomPopulationGenerator;

public class Application {
    private ArrayList<City> availableCities;
    private double[][] distances;

    private double minimalFitness = Double.MAX_VALUE;
    private int sameFitness = 0;
    private int generationCounter = 0;
    private int noChangeLimit = 1000;

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

    public void execute() {
        System.out.println("--- GeneticAlgorithm.execute()");
        // HSQLDBManager.instance.insertTest("hello world");

        Population population = RandomPopulationGenerator.randomPopulation(availableCities, 26);
        for (Tour tour : population.getTours()) {
            System.out.println(tour);
        }
        double bestFitness = Double.MAX_VALUE;

        // Evolution Loop
        while (hasPopulationChanged(bestFitness)) {

            // Selection
            List<Tour> selectedTours = Configuration.instance.selection.doSelection(population);

            // Crossover
            ListIterator<Tour> listIterator = selectedTours.listIterator();
            while (listIterator.hasNext()) {
                Tour tour1 = listIterator.next();
                Tour tour2 = null;
                if (listIterator.hasNext()) {
                    tour2 = listIterator.next();
                    if (Configuration.instance.mersenneTwister.nextBoolean(Configuration.instance.crossoverRatio)) {
                        population.getTours().add(Configuration.instance.crossover.doCrossover(tour1, tour2));
                    }
                }
            }

            // Mutation
            listIterator = population.getTours().listIterator();
            while (listIterator.hasNext()) {
                Tour tour = listIterator.next();
                if (Configuration.instance.mersenneTwister.nextBoolean(Configuration.instance.mutationRatio)) {
                    listIterator.set(Configuration.instance.mutation.doMutation(tour));
                }
            }

            // Evaluation
            Tour bestTour = MinimalTourDetector.minimalTourIn(population);
            minimalFitness = bestTour.getFitness();
            System.out.println("Minimal Fitness in generation " + generationCounter + ": " + minimalFitness);

            generationCounter++;
        }

    }

    private boolean hasPopulationChanged(double populationMinimalFitness) {
        if (generationCounter > Configuration.instance.numberOfIterations) {
            return false;
        }

        if (populationMinimalFitness == minimalFitness) {
            sameFitness++;
        } else {
            sameFitness = 0;
        }

        return sameFitness <= noChangeLimit;
    }

    public List<City> getAvailableCities() {
        return availableCities;
    }

    public static void main(String ... args) {
        Application application = new Application();
        // application.startupHSQLDB();
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
            application.execute();
        }
        // application.shutdownHSQLDB();

    }
}
