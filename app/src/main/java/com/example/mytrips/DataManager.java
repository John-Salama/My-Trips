package com.example.mytrips;

import java.util.ArrayList;
import java.util.List;

public class DataManager {
    private static DataManager ourInstance = null;
    private final List<UpcomingTripsData> Upcoming = new ArrayList<>();
    private final List<UpcomingTripsData> cancel = new ArrayList<>();

    public static DataManager getInstance() {
        if (ourInstance == null) {
            ourInstance = new DataManager();
            ourInstance.initializeDays();
        }
        return ourInstance;
    }


    public List<UpcomingTripsData> getUpcomingDays() {
        return Upcoming;
    }

    public List<UpcomingTripsData> getCancelDays() {
        return cancel;
    }

    public void addTrip(UpcomingTripsData data) {
        if (data.tripStatus.equals("Upcoming"))
            Upcoming.add(data);
        else
            cancel.add(data);
    }

    private void initializeDays() {
        Upcoming.add(new UpcomingTripsData("13/12/2008", "13/12/2005", "cairo", "Upcoming", "cairo0", "cairo", 5, "13/12/2005", "12/13/2005"));
        Upcoming.add(new UpcomingTripsData("13/12/2008", "13/12/2005", "cairo", "Upcoming", "cairo0", "cairo", 5, "13/12/2005", "12/13/2005"));
        cancel.add(new UpcomingTripsData("13/12/2008", "13/12/2005", "cairo", "cancel", "cairo0", "cairo", 5, "13/12/2005", "12/13/2005"));
        cancel.add(new UpcomingTripsData("13/12/2008", "13/12/2005", "cairo", "cancel", "cairo0", "cairo", 5, "13/12/2005", "12/13/2005"));
        cancel.add(new UpcomingTripsData("13/12/2008", "13/12/2005", "cairo", "cancel", "cairo0", "cairo", 5, "13/12/2005", "12/13/2005"));
    }
}
