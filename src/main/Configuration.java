package main;

import random.MersenneTwisterFast;

public enum Configuration {
    instance;

    public String fileSeparator = System.getProperty("file.separator");
    public String userDirectory = System.getProperty("user.dir");

    public String dataDirectory = userDirectory + fileSeparator + "data" + fileSeparator;
    public String dataFilePath = dataDirectory + "TSP280.txt";

    public String databaseFile = dataDirectory + "datastore.db";

    public MersenneTwisterFast mersenneTwister = new MersenneTwisterFast();

    // Parameters for the genetic algorithm
    public final int numberOfIterations = 10_000;
    public final int noChangeLimit = 1_000;

    // Show additional debugging information
    public boolean isDebug = false;

    // BruteForce
    public long numberOfTourElements = 1000;

    // Selection
    public int overPopulation = 50;
    public double choosePercentageOfTributes = 0.5;
    public double choosePercentageOfWinners = 0.5;
    public boolean killDefeatedTributes = false; //is in roulettewheelselection dynamically chosen
}
