package selection;

import base.Population;
import base.Tour;
import main.Configuration;
import random.MersenneTwisterFast;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TournamentSelection implements ISelection {
    ArrayList<Tour> winner = new ArrayList<Tour>();
    MersenneTwisterFast realrandom = new MersenneTwisterFast();
    ArrayList<Tour> clonedTours;

    public ArrayList<Tour> doSelection(Population population) {
        clonedTours = population.getTours();
        ArrayList<Tour> tributes = new ArrayList<Tour>();
        ArrayList<Tour> fighters = new ArrayList<Tour>();
        int tributecounter = getNumberofTributes(clonedTours);
        for (int i = 0; i < tributecounter; i++)//später variable Prozentanzahl
        {
            int randomvalue = realrandom.nextInt(0, clonedTours.size()-1);
            tributes.add(clonedTours.remove(randomvalue));
        }
        int counteroffighters = 0;
        while (!tributes.isEmpty()) {

            int randomFighterIndex = realrandom.nextInt(0, tributes.size()-1);
            fighters.add(tributes.get(randomFighterIndex));
            tributes.remove(randomFighterIndex);
            if (counteroffighters == 1) //man braucht immer zwei die Kämpfen sollen(verglichen)
            {
                counteroffighters = 0;
                fight(fighters);
                fighters.clear();
            }
            else{
                counteroffighters++;
            }

        }


        return getCouples();
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

    private boolean fight(ArrayList<Tour> enemys) {
        if (enemys.size() == 2) {
            if (enemys.get(0).getFitness() < enemys.get(1).getFitness()) {
                winner.add(enemys.get(0));
                clonedTours.add(enemys.get(1));
                return true;
            }
            if (enemys.get(1).getFitness() < enemys.get(0).getFitness()) {
                winner.add(enemys.get(1));
                clonedTours.add(enemys.get(0));
                return true;
            }
            if (enemys.get(0).getFitness() == enemys.get(1).getFitness()) {
                int randomvalue = realrandom.nextInt(0, 1);
                if (randomvalue == 0) {
                    winner.add(enemys.get(0));
                    clonedTours.add(enemys.get(1));
                    return true;
                }
                if (randomvalue == 1) {
                    winner.add(enemys.get(1));
                    clonedTours.add(enemys.get(0));
                    return true;
                }
            }

        }
        return false;
    }

    public ArrayList<Tour> getCouples()//1und2 ist ein couple 3und4.....
    {
        ArrayList<Tour> couples = new ArrayList<Tour>();
        while (!winner.isEmpty()) {
            couples.add(winner.remove(realrandom.nextInt(0, winner.size()-1)));
        }
        return couples;
    }
}

