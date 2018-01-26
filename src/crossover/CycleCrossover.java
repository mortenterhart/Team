package crossover;

import base.City;
import base.Tour;

import java.util.ArrayList;

public class CycleCrossover implements ICrossover {
    public Tour doCrossover(Tour tour01,Tour tour02) {
        int length1 = tour01.getSize();
        int length2 = tour02.getSize();
        if(length1 != length2) {
            throw new Error("Number of cities of given tours not identical");
        }

        ArrayList<City> cities1 = tour01.getCities();
        ArrayList<City> cities2 = tour02.getCities();

        Tour child1 = new Tour(length1);
        Tour child2 = new Tour(length1);

        int idx = 0, startIdx = 0;
        int cycleCount = 0;
        int cycleLength = 0;
        boolean cityDone[] = new boolean[length1];

        while (true) {
            City c = cities2.get(idx);
            int nextIdx = cities1.indexOf(c);

            cityDone[idx] = true;

            if(cycleCount % 2 == 0) {
                child1.addCity(idx, cities1.get(idx));
                child2.addCity(idx, cities2.get(idx));
            }
            else {
                child1.addCity(idx, cities2.get(idx));
                child2.addCity(idx, cities1.get(idx));
            }

            if(nextIdx == idx) { // check for all cycles done, only one remains
                break;
            }
            if(nextIdx == startIdx) { //generate next Idx, if cycle at end
                for(int i = 0; i < length1; i++) { //loop through all remaining start indices
                    if(!cityDone[i]) {//check that new index is not already part of a cycle
                        startIdx = i; //start new cycle
                        idx = i;
                        cycleCount++;
                    }
                    else if(i == length1 - 1) {
                        break; //no remaining index found
                    }
                }
            }
            else
                idx = nextIdx; //continue cycle

            cycleLength++;
            if(cycleLength >= 1000000 || cycleCount >= 1000000)
                throw new Error("stuck in while loop, get me out!");
        }

        for(int i = 0; i < length1; i++) { //loop through all indices
            if (!cityDone[i]) {//check for the element that was not processed
                child1.addCity(i, cities1.get(i));
                child2.addCity(i, cities2.get(i));
            }
        }

        if(child1.compareTo(child2) < 0) //if child 1 is stronger
            return child1;
        else
            return child2;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}