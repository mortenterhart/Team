package main;

import crossover.CycleCrossover;
import crossover.ICrossover;
import mutation.DisplacementMutation;
import mutation.IMutation;
import random.MersenneTwisterFast;
import selection.ISelection;
import selection.RouletteWheelSelection;
import selection.TournamentSelection;

public enum Configuration {
    instance;

    public String fileSeparator = System.getProperty("file.separator");
    public String userDirectory = System.getProperty("user.dir");

    public String dataDirectory = userDirectory + fileSeparator + "data" + fileSeparator;
    public String dataFilePath = dataDirectory + "TSP280.txt";

    public String databaseFile = dataDirectory + "datastore.db";

    public MersenneTwisterFast mersenneTwister = new MersenneTwisterFast();

    // Parameters for the genetic algorithm
    public double mutationRatio = 0.001;
    public double crossoverRatio = 0.6;

    public ISelection selection = new TournamentSelection();
    public ICrossover crossover = new CycleCrossover();
    public IMutation mutation = new DisplacementMutation();

    // Show additional debugging information
    public boolean isDebug = false;

    // BruteForce
    public boolean startBruteForce = true;
    public int numberOfTourElements = 1000;

    // Selection
    public double choosePercentageOfTributes = 0.5;
    public boolean killDefeatedTributes = true;
}
