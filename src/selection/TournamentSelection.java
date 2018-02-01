package selection;

import base.Population;
import base.Tour;
import main.Configuration;
import random.MersenneTwisterFast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TournamentSelection implements ISelection {

    public ArrayList<Tour> doSelection(Population population) {
        ArrayList<Tour> clonedTours = population.getTours();
        ArrayList<Tour> winner = new ArrayList<Tour>();
        ArrayList<Tour> tributes = new ArrayList<Tour>();
        ArrayList<Tour> fighters = new ArrayList<Tour>();
        setTributesfromClonedTours(tributes, clonedTours, getNumberofTributes(clonedTours));
        return getCouples(findTwoFightersFromTributesAndLetThemFight(tributes, fighters, clonedTours, winner));
    }

    public String toString() {
        return getClass().getSimpleName();
    }

    private int getNumberofTributes(ArrayList<Tour> Tours) {
        double percentageOfTributes = Configuration.instance.choosePercentageOfTributes;
        int size = (int) (Tours.size() * percentageOfTributes);
        size = size - size % 4;//suche die nächst kleinere Zahl die durch 4 ganzzahlig Teilbar ist
        return size;
    }

    private boolean fight(ArrayList<Tour> enemys, ArrayList<Tour> looser, ArrayList<Tour> winner) {
        if (enemys.size() == 2) {
            if (enemys.get(0).getFitness() < enemys.get(1).getFitness()) {
                winner.add(enemys.get(0));
                deadOrAlive(Configuration.instance.killDefeatedTributes, looser, enemys.get(1));
                return true;
            }
            if (enemys.get(1).getFitness() < enemys.get(0).getFitness()) {
                winner.add(enemys.get(1));
                deadOrAlive(Configuration.instance.killDefeatedTributes, looser, enemys.get(0));
                return true;
            }
            if (enemys.get(0).getFitness() == enemys.get(1).getFitness()) {
                int randomvalue = Configuration.instance.mersenneTwister.nextInt(0, 1);
                if (randomvalue == 0) {
                    winner.add(enemys.get(0));
                    deadOrAlive(Configuration.instance.killDefeatedTributes, looser, enemys.get(1));
                    return true;
                }
                if (randomvalue == 1) {
                    winner.add(enemys.get(1));
                    deadOrAlive(Configuration.instance.killDefeatedTributes, looser, enemys.get(0));
                    return true;
                }
            }

        }
        return false;
    }

    public ArrayList<Tour> getCouples(ArrayList<Tour> winner)//1und2 ist ein couple 3und4.....
    {
        ArrayList<Tour> couples = new ArrayList<Tour>();
        while (!winner.isEmpty()) {
            couples.add(winner.remove(Configuration.instance.mersenneTwister.nextInt(0, winner.size() - 1)));
        }
        return couples;
    }

    private void setTributesfromClonedTours(ArrayList<Tour> tributes, ArrayList<Tour> clonedTours, int percentfromAllTours) {
        for (int i = 0; i < percentfromAllTours; i++)//später variable Prozentanzahl
        {
            int randomvalue = Configuration.instance.mersenneTwister.nextInt(0, clonedTours.size() - 1);
            tributes.add(clonedTours.remove(randomvalue));
        }
    }

    private ArrayList<Tour> findTwoFightersFromTributesAndLetThemFight(ArrayList<Tour> tributes, ArrayList<Tour> fighters, ArrayList<Tour> looser, ArrayList<Tour> winner) {
        int counteroffighters = 0;
        while (!tributes.isEmpty()) {
            int randomFighterIndex = Configuration.instance.mersenneTwister.nextInt(0, tributes.size() - 1);
            fighters.add(tributes.get(randomFighterIndex));
            tributes.remove(randomFighterIndex);
            if (counteroffighters == 1) //man braucht immer zwei die Kämpfen sollen(verglichen)
            {
                counteroffighters = 0;
                fight(fighters, looser, winner);
                fighters.clear();
            } else {
                counteroffighters++;
            }
        }
        return winner;
    }

    private void deadOrAlive(boolean deadorNot, ArrayList<Tour> allTours, Tour looser) {
        if (!deadorNot) {
            allTours.add(looser);
        }
    }
}


