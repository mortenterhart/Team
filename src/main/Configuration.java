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

    // Show additional debugging information
    public boolean isDebug = false;

    // BruteForce
    public boolean startBruteForce = true;
    public double numberOfTourElements = 1000;

    // Selection
    public double choosePercentageOfTributes = 0.5;
    public boolean killDefeatedTributes = true;
}
