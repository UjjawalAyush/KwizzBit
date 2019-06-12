package com.ujjawalayush.example.kwizzbit;

import java.util.ArrayList;
import java.util.Collections;

public class Sorter {
    ArrayList<RecyclerData> myList = new ArrayList<>();
    public Sorter(ArrayList<RecyclerData> myList) {
        this.myList = myList;
    }
    public ArrayList<RecyclerData> getSortedByScore() {
        Collections.sort(myList);
        Collections.reverse(myList);
        return myList;
    }
}
