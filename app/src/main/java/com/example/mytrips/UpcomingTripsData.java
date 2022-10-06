package com.example.mytrips;

public class UpcomingTripsData {

    String tripStartTime;
    String tripDateTime;
    String tripName;
    String tripStatus;
    String tripStartLoc;
    String tripEndLoc;
    int tripType;
    String tripRoundStartTime;
    String tripRoundDateTime;

    public UpcomingTripsData(String tripStartTime, String tripDateTime, String tripName, String tripStatus, String tripStartLoc, String tripEndLoc, int tripType, String tripRoundStartTime, String tripRoundDateTime) {
        this.tripStartTime = tripStartTime;
        this.tripDateTime = tripDateTime;
        this.tripName = tripName;
        this.tripStatus = tripStatus;
        this.tripStartLoc = tripStartLoc;
        this.tripEndLoc = tripEndLoc;
        this.tripType = tripType;
        this.tripRoundStartTime = tripRoundStartTime;
        this.tripRoundDateTime = tripRoundDateTime;

    }

    public String getTripStartTime() {
        return tripStartTime;
    }


    public String getTripDateTime() {
        return tripDateTime;
    }


    public String getTripName() {
        return tripName;
    }


    public String getTripStatus() {
        return tripStatus;
    }


    public String getTripStartLoc() {
        return tripStartLoc;
    }


    public String getTripEndLoc() {
        return tripEndLoc;
    }


    public int getTripType() {
        return tripType;
    }


    public String getTripRoundStartTime() {
        return tripRoundStartTime;
    }

    public String getTripRoundDateTime() {
        return tripRoundDateTime;
    }

}
