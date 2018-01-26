package main;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import base.City;
import base.Tour;
import bruteforce.BruteForce;
import crossover.ICrossover;
import data.HSQLDBManager;
import data.InstanceReader;
import data.TSPLIBReader;
import mutation.IMutation;
import selection.ISelection;
import selection.TournamentSelection;

public class Application {
    private ArrayList<City> availableCities;
    private double[][] distances;

    private ISelection selection;
    private ICrossover crossover;
    private IMutation mutation;

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

    public void execute() {
        System.out.println("--- GeneticAlgorithm.execute()");
        HSQLDBManager.instance.insertTest("hello world");
    }

    public List<City> getAvailableCities() {
        return availableCities;
    }

    public static void main(String ... args) {
        Application application = new Application ();
        // application.startupHSQLDB();
        application.loadData();

        if (Configuration.instance.startBruteForce) {
            if (Configuration.instance.isDebug) {
                System.out.println("--- Started Bruteforce");
            }

            BruteForce bruteForceApplication = new BruteForce (application.availableCities);
            Tour bestFoundTour = bruteForceApplication.minimalTourAll ();
            System.out.println ("\nFitness of best tour: " + bestFoundTour.getFitness ());

            if (Configuration.instance.isDebug) {
                System.out.println ("--- Finished Bruteforce");
            }
        } else {
            application.initConfiguration();
            application.execute();
        }
        // application.shutdownHSQLDB();

    }
}
