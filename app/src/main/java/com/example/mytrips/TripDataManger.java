package com.example.mytrips;

import java.util.ArrayList;
import java.util.List;

public class TripDataManger {
    private static volatile TripDataManger instance = null;
    private final List<UpcomingTripsData> upcoming = new ArrayList<>();
    private final List<UpcomingTripsData> history = new ArrayList<>();


    private TripDataManger() {
    }

    public static TripDataManger getInstance() {
        if (instance == null) {
            synchronized (TripDataManger.class) {
                if (instance == null) {
                    instance = new TripDataManger();
                }
            }
        }
        return instance;
    }

    public List<UpcomingTripsData> getUpcoming() {
        return upcoming;
    }

    public List<UpcomingTripsData> getHistory() {
        return history;
    }

    public void addTrip(UpcomingTripsData trip) {
        if ("Upcoming".equals(trip.getTripStatus()))
            upcoming.add(trip);
        else
            history.add(trip);
    }
}
