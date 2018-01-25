package mutation;

import base.City;
import base.Tour;
import random.MersenneTwisterFast;

import java.util.*;

public class HeuristicMutation implements IMutation {
    public Tour doMutation(Tour tour) {
        MersenneTwisterFast mersenneTwisterFast = new MersenneTwisterFast();
        int count = mersenneTwisterFast.nextInt(0, tour.getCities().size()-1);

        ArrayList<Integer> indexCities = new ArrayList<>();
        System.out.println("count: " + count);
        while (indexCities.size()!=count){
            int key = mersenneTwisterFast.nextInt(1,tour.getCities().size()-1);
            if(indexCities.contains(key))
                continue;
            indexCities.add(key);

        }

        ArrayList<City> permutateCities = new ArrayList<>();
        for (int index: indexCities) {
            permutateCities.add(tour.getCity(index));
        }

        ArrayList<ArrayList<City>> permutatedCityLists= permute(permutateCities);

        int fak = permutatedCityLists.size();
        System.out.println(fak);

        ArrayList<ArrayList<City>> cityLists = new ArrayList<>();
        for (int i = 0; i < fak; i++){
            ArrayList<City> cities = new ArrayList<>();
            for (City city: tour.getCities()) {
                cities.add(city);
            }
            cityLists.add(cities);
        }
        Tour tempTour = new Tour();
        Double tempFitness = 0.0;
        int k = 0;
        for (int i = 0; i < fak; i++){
            for (int index: indexCities) {
                cityLists.get(i).set(index, permutatedCityLists.get(i).get(k++));
            }
            tempTour.setCities(cityLists.get(i));
            if(tempTour.getFitness() >= tempFitness){
                tempFitness = tempTour.getFitness();
                tour.setCities(cityLists.get(i));
            }
        }

        return tour;
    }

    public static ArrayList<ArrayList<City>> permute(List<City> list) {

        if (list.size() == 0) {
            ArrayList<ArrayList<City>> result = new ArrayList<ArrayList<City>>();
            result.add(new ArrayList<City>());
            return result;
        }

        ArrayList<ArrayList<City>> returnMe = new ArrayList<ArrayList<City>>();

        City firstElement = list.remove(0);

        ArrayList<ArrayList<City>> recursiveReturn = permute(list);
        for (List<City> li : recursiveReturn) {

            for (int index = 0; index <= li.size(); index++) {
                ArrayList<City> temp = new ArrayList<City>(li);
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