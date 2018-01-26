package mutation;

import base.City;
import base.Tour;
import main.Configuration;
import random.MersenneTwisterFast;

import java.util.*;

public class HeuristicMutation implements IMutation {
    public Tour doMutation(Tour tour) {

//        long startTime = System.currentTimeMillis();
//        long endTime = System.currentTimeMillis();

        MersenneTwisterFast mersenneTwisterFast = Configuration.instance.mersenneTwister;

        //use start value 2 to avoid doing nothing
        int count = mersenneTwisterFast.nextInt(2, 8);
        ArrayList<Integer> pickedOutIndecies = new ArrayList<>();
        ArrayList<Integer> pickedOutIndeciesBackup = new ArrayList<>();
        int key;
        for (int i = 0; i < count; i++) {
            key = mersenneTwisterFast.nextInt(0, tour.getCities().size() - 1);
            if (pickedOutIndecies.contains(key)) {
                count++;
                continue;
            }
            pickedOutIndecies.add(key);
            pickedOutIndeciesBackup.add(key);
        }

        ArrayList<ArrayList<Integer>> permuatedPickedOutIndecies = permute(pickedOutIndecies);


        long fak = permuatedPickedOutIndecies.size();


        ArrayList<City> oldCities = (ArrayList<City>)tour.getCities().clone();
        ArrayList<City> tempCities = (ArrayList<City>)tour.getCities().clone();

        ArrayList<Integer> tempPermuatedPickedOutIndecies;
        double maxFittness = 0;
        ArrayList<City> maxFittnessCityList = null;

        for(int i=0; i<fak;i++)
        {
            tempPermuatedPickedOutIndecies = permuatedPickedOutIndecies.get(i);
            for(int j=0; j<tempPermuatedPickedOutIndecies.size(); j++)
            {
                int insertPointNewList = pickedOutIndeciesBackup.get(j);
                int pickOutPointOldList = tempPermuatedPickedOutIndecies.get(j);
                tempCities.set(insertPointNewList, oldCities.get(pickOutPointOldList));
            }
            tour.setCities(tempCities);
            double tempFittness = tour.getFitness();
            if(tempFittness>maxFittness)
            {
                maxFittness = tempFittness;
                maxFittnessCityList = (ArrayList<City>)tempCities.clone();
            }
        }

        tour.setCities(maxFittnessCityList);

//        endTime = System.currentTimeMillis();
//        System.out.println(endTime-startTime);


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