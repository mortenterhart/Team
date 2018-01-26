package selection;

import base.Population;
import base.Tour;
import main.Configuration;
import random.MersenneTwisterFast;

import java.util.ArrayList;

public class RouletteWheelSelection implements ISelection {
    public ArrayList<Tour> doSelection(Population population) {
        ArrayList<Tour> wholePopulation = population.getTours();

        ArrayList<Tour>tributes = new ArrayList<>();

        tributes = getTributes(wholePopulation, tributes);
        int numOfTributes = tributes.size();

        //fill double[] roulette with border-values of segments
        ArrayList<Double> roulette = new ArrayList<Double>();
        setRouletteWheel(numOfTributes, roulette, tributes);

        //turn the roulette
        ArrayList<Tour> winners = new ArrayList<>();
        winners = turnRouletteWheel(numOfTributes, roulette, tributes, winners);

        //write chosen(& skipped) tributes back into Population
        wholePopulation.addAll(winners);
        if(!Configuration.instance.killDefeatedTributes){
            wholePopulation.addAll(tributes);
        }
        population.setTours(wholePopulation);

        return winners;
    }

    /**
     * turn the wheel and return the winners
     * @param numOfTributes
     * @param roulette
     * @param tributes
     * @param winners
     * @return winners
     */
    private ArrayList<Tour> turnRouletteWheel(int numOfTributes, ArrayList<Double> roulette, ArrayList<Tour> tributes, ArrayList<Tour> winners) {
        MersenneTwisterFast mersenneTwisterFast = Configuration.instance.mersenneTwisterFast;
        double pointer = mersenneTwisterFast.nextDouble(true,true);
        for(int i = 0; i< (numOfTributes/2); i++){  //let half of the tributes win
            for(double r: roulette){
                if(r >= pointer){
                    int pos = mersenneTwisterFast.nextInt(0,winners.size()); //random position to add next winner to
                    winners.add(pos, tributes.remove(roulette.indexOf(r)));
                    roulette.remove(roulette.indexOf(r));
                    break;
                }
            }
            pointer = (mersenneTwisterFast.nextDouble(true,true))%(roulette.get(roulette.size()-1));
        }
        return winners;
    }

    /**
     * fill ArrayList<Double> roulette with border-values of segments
     * @param roulette array to fill
     * @param tributes used to get each one's fitness
     * @return filled arraylist roulette
     */
    private ArrayList<Double> setRouletteWheel(int numOfTributes, ArrayList<Double> roulette, ArrayList<Tour> tributes) {
        double allFitness = 0;
        double bestFitness = Double.MAX_VALUE;
        for(int i = 0; i < numOfTributes; i++){
            double fitness = tributes.get(i).getFitness();
            roulette.add(fitness);
            allFitness +=fitness;
            bestFitness = (fitness < bestFitness)? fitness : bestFitness;
        }
        double current = 0;
        for(int i = 0; i < roulette.size(); i++){
            current += roulette.get(i) / allFitness;
            roulette.set(i,current);
        }
        roulette.set(roulette.size()-1,1.0);
        return roulette;
    }

    /**
     * Returns "Configuration.instance.choosePercentageOfTributes" % of whole population as tributes
     * @param wholePopulation ArrayList<Tour>
     * @param tributes ArrayList<Tour>
     * @return tributes
     */
    private ArrayList<Tour> getTributes(ArrayList<Tour> wholePopulation, ArrayList<Tour> tributes) {
        MersenneTwisterFast mersenneTwisterFast = Configuration.instance.mersenneTwisterFast;
        int numOfTributes = (int) (wholePopulation.size() * Configuration.instance.choosePercentageOfTributes);

        for(int i = 0; i < numOfTributes; i++){
            int temp = mersenneTwisterFast.nextInt(0, wholePopulation.size());
            tributes.add(wholePopulation.remove(temp));
        }
        return tributes;
    }

    public String toString() {
        return getClass().getSimpleName();
    }

}