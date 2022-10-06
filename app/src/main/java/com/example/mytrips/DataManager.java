package com.example.mytrips;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager ourInstance = null;
    private final List<UpcomingTripsData> mDays = new ArrayList<>();

    public static DataManager getInstance() {
        if(ourInstance == null) {
            ourInstance = new DataManager();
            ourInstance.initializeDays();
        }
        return ourInstance;
    }

    public List<UpcomingTripsData> getDays() {
        return mDays;
    }


    private void initializeDays() {
        mDays.add(new UpcomingTripsData("13/12/2008","13/12/2005","cairo","goo","cairo0","cairo",5,"13/12/2005","12/13/2005"));
        mDays.add(new UpcomingTripsData("13/12/2008","13/12/2005","cairo","goo","cairo0","cairo",5,"13/12/2005","12/13/2005"));
    }
}
