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
import mutation.*;
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
        // printMatrix(distances);

        instanceReader.close();

        System.out.println();
    }

    public void initConfiguration() {
        System.out.println("--- GeneticAlgorithm.initConfiguration()");
        System.out.println();
    }

    public void startBruteforce() {
        if (Configuration.instance.isDebug) {
            System.out.println("--- Started Bruteforce");
        }

        BruteForce bruteForceApplication = new BruteForce(availableCities);
        bruteForceApplication.generateRandomTours();
        Tour bestFoundTour = bruteForceApplication.minimalTourAll();
        System.out.println("\nFitness of best tour: " + bestFoundTour.getFitness());

        if (Configuration.instance.isDebug) {
            System.out.println("--- Finished Bruteforce");
        }
    }

    public void startScenario(int scenarioId, ISelection selection, ICrossover crossover,
                              IMutation mutation, double crossoverRatio, double mutationRatio) {
        this.executeScenario(new TSPScenario(scenarioId, selection, crossover, mutation, crossoverRatio, mutationRatio));
    }

    public void executeScenario(TSPScenario scenario) {
        System.out.println("--- GeneticAlgorithm.execute()");

        int generationCounter = 1;
        Population population = RandomPopulationGenerator.randomPopulation(availableCities, 26);
        double bestFitness = MinimalTourDetector.minimalTourIn(population).getFitness();

        double generationMinimumFitness = bestFitness;
        // Evolution Loop
        while (!isIterationLimitReached(generationCounter) &&
                minimumNotChanged(generationMinimumFitness, Configuration.instance.noChangeLimit)) {

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
            previousFitness = generationMinimumFitness;
            Tour bestGenerationTour = MinimalTourDetector.minimalTourIn(population);
            generationMinimumFitness = bestGenerationTour.getFitness();
            System.out.println("Minimal Fitness in generation " + generationCounter + ": " + generationMinimumFitness);
            System.out.println("Same Fitness since " + sameFitnessCounter + " iterations");
            HSQLDBManager.instance.update(HSQLDBManager.instance.buildSQLStatement(databaseIdCounter,
                    Configuration.instance.numberOfIterations, generationMinimumFitness, scenario.getScenarioId()));

            if (generationMinimumFitness < bestFitness) {
                bestFitness = generationMinimumFitness;
            }

            generationCounter++;
            databaseIdCounter++;
        }

        System.out.println("\nOverall minimum fitness: " + bestFitness);
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

        application.initConfiguration();
        int scenarioCounter = 1;
        // Scenario 1
        /*application.startScenario(scenarioCounter, new RouletteWheelSelection(), new PartiallyMatchedCrossover(),
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

        // Bruteforce Algorithm
        application.startBruteforce();


        application.shutdownHSQLDB();
    }
}
