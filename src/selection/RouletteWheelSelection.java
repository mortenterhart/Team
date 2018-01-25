package selection;

import base.Population;
import base.Tour;
import main.Configuration;
import random.MersenneTwisterFast;

import java.util.ArrayList;

public class RouletteWheelSelection implements ISelection {
    public ArrayList<Tour> doSelection(Population population) {
        MersenneTwisterFast mersenneTwisterFast = new MersenneTwisterFast(); //in Configuration
        ArrayList<Tour> wholePopulation = population.getTours();
        ArrayList<Tour>tributes = new ArrayList<>();
        int numOfTributes = (int) (wholePopulation.size() * Configuration.instance.choosePercentageOfTributes);

        for(int i = 1; i < numOfTributes; i++){
            int temp = mersenneTwisterFast.nextInt(0, wholePopulation.size());
            tributes.add(wholePopulation.remove(temp));
        }

        //fill double[] roulette with border-values of segments
        ArrayList<Double> roulette = new ArrayList<>();
        double allFitness = 0;
        for(Tour tour: tributes){
            double fitness = tour.getFitness();
            roulette.add(fitness);
            allFitness +=fitness;
        }
        double current = 0;
        for(double r: roulette){
            current += r / allFitness;
            r = current;
        }

        //turn the roulette
        ArrayList<Tour> winners = new ArrayList<>();
        double pointer = mersenneTwisterFast.nextDouble(0,1);
        for(int i = 0; i< (numOfTributes/2); i++){
            for(double r: roulette) {
                if(r < pointer){
                    winners.add(tributes.remove(roulette.indexOf(r)));
                    break;
                }
            }
            pointer = mersenneTwisterFast.nextDouble(0, 1);
        }

        wholePopulation.addAll(winners);
        if(!Configuration.instance.killDefeatedTributes){
            wholePopulation.addAll(tributes);
        }

        return winners;
    }




    public String toString() {
        return getClass().getSimpleName();
    }
}