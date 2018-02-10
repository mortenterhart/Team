package main;

import java.text.DecimalFormat;
import java.util.List;

import base.City;
import base.Tour;
import data.HSQLDBManager;
import data.InstanceReader;
import data.TSPLIBReader;
import logging.LogEngine;
import scenario.base.BestScenarioFitness;
import scenario.base.TSPScenario;
import scenario.xml.XMLScenarioCreator;

import javax.management.modelmbean.XMLParseException;

public class Application {
    private List<City> availableCities;
    private double[][] distances;

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

    private String formatFitness(double fitness) {
        return String.format("%.4f", fitness);
    }

    public List<City> getAvailableCities() {
        return availableCities;
    }

    public static void main(String... args) throws XMLParseException {
        Application application = new Application();
        application.startupHSQLDB();
        application.loadData();
        LogEngine.instance.init();
        application.initConfiguration();

        GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(application.getAvailableCities());

        // All 25 scenarios loaded from XML configuration file
        XMLScenarioCreator scenarioCreator = new XMLScenarioCreator();
        for (TSPScenario scenario : scenarioCreator.parseScenarios(Configuration.instance.scenarioDefinitionFile)) {
            geneticAlgorithm.executeScenario(scenario);
        }
        geneticAlgorithm.logAlgorithmCounters();

        // Scenario 26: BruteForce Algorithm
        BruteForceAlgorithm bruteForce = new BruteForceAlgorithm(application.getAvailableCities(),
                geneticAlgorithm.getDatabaseIdCounter());
        bruteForce.executeBruteForce();

        // Retrieve the best overall fitness values of the genetic
        // algorithm and the bruteforce algorithm
        BestScenarioFitness bestGeneticFitness = geneticAlgorithm.getBestScenario();
        BestScenarioFitness bestBruteForceFitness = bruteForce.getBestBruteForceFitness();

        application.log("==================================");
        application.log(String.format("Best Fitness of Genetic Algorithm:    %s (Scenario %d)",
                application.formatFitness(bestGeneticFitness.getFitness()),
                bestGeneticFitness.getScenario().getScenarioId()));
        application.log(String.format("Best Fitness of BruteForce Algorithm: %s",
                application.formatFitness(bestBruteForceFitness.getFitness())));

        LogEngine.instance.close();
        application.shutdownHSQLDB();
    }
}
