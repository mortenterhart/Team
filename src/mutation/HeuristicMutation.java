package mutation;

import base.City;
import base.Tour;
import random.MersenneTwisterFast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.TreeMap;

public class HeuristicMutation implements IMutation {
    private HashMap<Double, ArrayList<City>> finalCities;
    public Tour doMutation(Tour tour) {
        MersenneTwisterFast mersenneTwisterFast = new MersenneTwisterFast();
        int count = mersenneTwisterFast.nextInt(0, tour.getCities().size()-1);

        TreeMap<Integer, City> cities = new TreeMap<>();
        System.out.println("count: " + count);
        while (cities.size()!=count){
            int key = mersenneTwisterFast.nextInt(0,tour.getCities().size()-1);
            cities.put(key, tour.getCity(key));

        }

        this.finalCities = new HashMap<>();
        this.finalCities.put(tour.getFitness(), tour.getCities());
        permute(finalCities.get(tour.getFitness()).toArray(), tour);
        System.out.println(finalCities.keySet());









        return tour;
    }

    public void permute(Object[] arr, Tour tour){
        permuteHelper(arr, 0, tour);
    }

    private void permuteHelper(Object[] arr, int index, Tour tour){
        if(index >= arr.length - 1){ //If we are at the last element - nothing left to permute
            //System.out.println(Arrays.toString(arr));
            //Print the array
            System.out.print("[");
            for(int i = 0; i < arr.length - 1; i++){
                System.out.print(arr[i] + ", ");
            }
            if(arr.length > 0)
                System.out.print(arr[arr.length - 1]);
            System.out.println("]");
            return;
        }

        for(int i = index; i < arr.length; i++){ //For each index in the sub array arr[index...end]

            //Swap the elements at indices index and i
            Object t = arr[index];
            arr[index] = arr[i];
            arr[i] = t;

//            tour.setCities(new ArrayList<City>(Arrays.asList(arr)));

            this.finalCities.put(tour.getFitness(), tour.getCities());
            //Recurse on the sub array arr[index+1...end]
            permuteHelper(arr, index+1, tour);

            //Swap the elements back
            t = arr[index];
            arr[index] = arr[i];
            arr[i] = t;
        }
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}