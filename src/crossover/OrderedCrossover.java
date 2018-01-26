package crossover;

import base.City;
import base.Tour;
import main.Configuration;

public class OrderedCrossover implements ICrossover {
    public Tour doCrossover(Tour tour01,Tour tour02) {
        int size = tour01.getSize();

        //choose start and end indices of slice
        int rand1 = Configuration.instance.mersenneTwister.nextInt(0, size - 2);
        int rand2 = Configuration.instance.mersenneTwister.nextInt(0, size - 1);

        int start = Math.min(rand1, rand2);
        int end = Math.max(rand1, rand2);

        Tour child1 = new Tour(size);
        Tour child2 = new Tour(size);

        //add slice from parents to children
        for(int i = start; i < end; i++) {
            child1.addCity(i, tour01.getCity(i));
            child2.addCity(i, tour02.getCity(i));
        }

        int toSaveIdx = 0;
        //loop through all parent elements
        for (int i = 0; i < size; i++) {
            if(toSaveIdx == start) { //check if we need to skip already copied slice
                toSaveIdx = end;
            }

            City c1 = tour01.getCity(i);
            City c2 = tour02.getCity(i);

            //add missing cities to empty spots of array
            if(!child1.containsCity(c2)) {
                child1.addCity(toSaveIdx, c2);
            }
            if(!child2.containsCity(c1)) {
                child2.addCity(toSaveIdx, c1);
            }
        }

        //return fittest child
        if(child1.compareTo(child2) < 0) //if child 1 is fitter
            return child1;
        else
            return child2;
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}

