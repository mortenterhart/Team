package base;

import crossover.ICrossover;
import mutation.IMutation;
import selection.ISelection;

public class TSPScenario {
    private int scenarioId = 0;
    private ICrossover crossover;
    private IMutation mutation;
    private ISelection selection;
    private double crossoverRatio = 0.0;
    private double mutationRatio = 0.0;

    public TSPScenario(int scenarioId, ICrossover crossover, IMutation mutation,
                       ISelection selection, double crossoverRatio, double mutationRatio) {
        this.scenarioId = scenarioId;
        this.crossover = crossover;
        this.mutation = mutation;
        this.selection = selection;
        this.crossoverRatio = crossoverRatio;
        this.mutationRatio = mutationRatio;
    }

    public int getScenarioId() {
        return scenarioId;
    }

    public ICrossover getCrossover() {
        return crossover;
    }

    public IMutation getMutation() {
        return mutation;
    }

    public ISelection getSelection() {
        return selection;
    }

    public double getCrossoverRatio() {
        return crossoverRatio;
    }

    public double getMutationRatio() {
        return mutationRatio;
    }
}
