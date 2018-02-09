package base;

import java.util.ArrayList;

public class Population {
    public ArrayList<Tour> tours;

    public Population() {
        tours = new ArrayList<>();
    }

    public ArrayList<Tour> getTours() {
        return tours;
    }

    public void setTours(ArrayList<Tour> tours) {
        this.tours = tours;
    }
}