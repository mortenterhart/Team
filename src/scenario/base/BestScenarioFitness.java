package scenario.base;

public class BestScenarioFitness {
    private TSPScenario scenario;
    private double fitness = Double.POSITIVE_INFINITY;

    public BestScenarioFitness() {
        scenario = new TSPScenario();
    }

    public BestScenarioFitness(TSPScenario scenario, double fitness) {
        this.scenario = scenario;
        this.fitness = fitness;
    }

    public TSPScenario getScenario() {
        return scenario;
    }

    public void setScenario(TSPScenario scenario) {
        this.scenario = scenario;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }
}
