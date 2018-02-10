package scenario.base;

import crossover.ICrossover;
import mutation.IMutation;
import selection.ISelection;

public class TSPScenario {
    private int scenarioId = 0;
    private ISelection selection;
    private ICrossover crossover;
    private IMutation mutation;
    private double crossoverRatio = 0.0;
    private double mutationRatio = 0.0;
    private int numberOfIterations = 0;

    public TSPScenario() {
        selection = null;
        crossover = null;
        mutation = null;
    }

    public TSPScenario(int scenarioId, ISelection selection, ICrossover crossover,
                       IMutation mutation, double crossoverRatio, double mutationRatio,
                       int numberOfIterations) {
        this.scenarioId = scenarioId;
        this.selection = selection;
        this.crossover = crossover;
        this.mutation = mutation;
        this.crossoverRatio = crossoverRatio;
        this.mutationRatio = mutationRatio;
        this.numberOfIterations = numberOfIterations;
    }

    public int getScenarioId() {
        return scenarioId;
    }

    public void setScenarioId(int scenarioId) {
        this.scenarioId = scenarioId;
    }

    public ISelection getSelection() {
        return selection;
    }

    public ICrossover getCrossover() {
        return crossover;
    }

    public IMutation getMutation() {
        return mutation;
    }

    public double getCrossoverRatio() {
        return crossoverRatio;
    }

    public double getMutationRatio() {
        return mutationRatio;
    }


    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public void setNumberOfIterations(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(getClass().getSimpleName()).append(" { ");
        builder.append("scenarioId = ").append(scenarioId).append(", ");
        builder.append("selection = ").append(selection).append(", ");
        builder.append("crossover = ").append(crossover).append(", ");
        builder.append("mutation = ").append(mutation).append(", ");
        builder.append("crossoverRatio = ").append(crossoverRatio).append(", ");
        builder.append("mutationRatio = ").append(mutationRatio).append(", ");
        builder.append("numberOfIterations = ").append(numberOfIterations).append(" }");
        builder.trimToSize();
        return builder.toString();
    }
}
