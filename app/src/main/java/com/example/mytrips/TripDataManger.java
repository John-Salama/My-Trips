package com.example.mytrips;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class TripDataManger {

    private final DatabaseReference mDatabase= FirebaseDatabase.getInstance().getReference();
    private final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
    private static volatile TripDataManger instance = null;
    private final List<TripData> upcoming = new ArrayList<>();
    private final List<TripData> history = new ArrayList<>();


    private TripDataManger(){}

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

    public List<TripData> getUpcoming() {
        return upcoming;
    }

    public List<TripData> getHistory() {
        return history;
    }

    public void addTrip(TripData trip) {
        if("Upcoming".equals(trip.getStatus()))
            upcoming.add(trip);
        else
            history.add(trip);
    }
}
