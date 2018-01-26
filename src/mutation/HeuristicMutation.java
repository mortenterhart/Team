package mutation;

import base.City;
import base.Tour;
import random.MersenneTwisterFast;

import java.util.*;

public class HeuristicMutation implements IMutation {
    public Tour doMutation(Tour tour) {

        /*TODO
        Check if int ist to small for 280!
         */

        MersenneTwisterFast mersenneTwisterFast = new MersenneTwisterFast();

        //why 2
        int count = mersenneTwisterFast.nextInt(2, tour.getCities().size() - 1);

        ArrayList<Integer> pickedOutIndecies = new ArrayList<>();
        ArrayList<Integer> pickedOutIndeciesBackup = new ArrayList<>();
        int key;
        for (int i = 0; i < count; i++) {
            key = mersenneTwisterFast.nextInt(0, tour.getCities().size() - 1);
            if (pickedOutIndecies.contains(key)) {
                count--;
                continue;
            }
            pickedOutIndecies.add(key);
            pickedOutIndeciesBackup.add(key);
        }

        //test cases
        pickedOutIndecies.clear();
        pickedOutIndeciesBackup.clear();
        pickedOutIndecies.add(2);
        pickedOutIndecies.add(4);
        pickedOutIndecies.add(6);
        pickedOutIndeciesBackup.add(2);
        pickedOutIndeciesBackup.add(4);
        pickedOutIndeciesBackup.add(6);

        System.out.println("FoundIndecies: ");
        System.out.println(pickedOutIndecies.toString());
        System.out.println("Found Indecies Backup:");
        System.out.println(pickedOutIndeciesBackup.toString());



        ArrayList<ArrayList<Integer>> permuatedPickedOutIndecies = permute(pickedOutIndecies);

        System.out.println("Permuated Indecies Lists: ");
        for(ArrayList<Integer> tempList : permuatedPickedOutIndecies)
            System.out.println(tempList);


        long fak = permuatedPickedOutIndecies.size();


        ArrayList<City> oldCities = (ArrayList<City>)tour.getCities().clone();
        ArrayList<City> tempCities = (ArrayList<City>)tour.getCities().clone();

        ArrayList<Integer> tempPermuatedPickedOutIndecies;
        double maxFittness = 0;
        ArrayList<City> maxFittnessCityList = null;

        //go on here
        for(long i=0; i<fak;i++)
        {
            tempPermuatedPickedOutIndecies = permuatedPickedOutIndecies.get(i);
            System.out.println("Start with " + i);
            System.out.println(tempPermuatedPickedOutIndecies.toString());
            for(int j=0; j<tempPermuatedPickedOutIndecies.size(); j++)
            {
                int insertPointNewList = pickedOutIndeciesBackup.get(j);
                int pickOutPointOldList = tempPermuatedPickedOutIndecies.get(j);
                tempCities.set(insertPointNewList, oldCities.get(pickOutPointOldList));
            }
            tour.setCities(tempCities);
            double tempFittness = tour.getFitness();
            System.out.println("Temp Fittness: "+ tempFittness);
            if(tempFittness>maxFittness)
            {
                maxFittness = tempFittness;
                maxFittnessCityList = (ArrayList<City>)tempCities.clone();
                System.out.println("New Max Fittness: " + maxFittness);
            }
        }
        System.out.println("Final Max Fittness: " + maxFittness);

        tour.setCities(maxFittnessCityList);

        return tour;
    }


    private ArrayList<ArrayList<Integer>> permute(ArrayList<Integer> list) {

        if (list.size() == 0) {
            ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
            result.add(new ArrayList<Integer>());
            return result;
        }

        ArrayList<ArrayList<Integer>> returnMe = new ArrayList<ArrayList<Integer>>();

        int firstElement = list.remove(0);

        ArrayList<ArrayList<Integer>> recursiveReturn = permute(list);
        for (List<Integer> li : recursiveReturn) {

            for (int index = 0; index <= li.size(); index++) {
                ArrayList<Integer> temp = new ArrayList<Integer>(li);
                temp.add(index, firstElement);
                returnMe.add(temp);
            }

        }
        return returnMe;
    }


    public String toString() {
        return getClass().getSimpleName();
    }
}