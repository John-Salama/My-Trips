package com.example.mytrips;

public class UpcomingTripsData {

    String tripStartTime ="";
    String tripDateTime ="";
    String tripName ="";
    String tripStatus ="";
    String tripStartLoc ="";
    String tripEndLoc ="";
    int tripType = 0;
    String tripRoundStartTime ="";
    String tripRoundDateTime ="";

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

    public void setTripStartTime(String tripStartTime) {
        this.tripStartTime = tripStartTime;
    }

    public String getTripDateTime() {
        return tripDateTime;
    }

    public void setTripDateTime(String tripDateTime) {
        this.tripDateTime = tripDateTime;
    }

    public String getTripName() {
        return tripName;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public String getTripStartLoc() {
        return tripStartLoc;
    }

    public void setTripStartLoc(String tripStartLoc) {
        this.tripStartLoc = tripStartLoc;
    }

    public String getTripEndLoc() {
        return tripEndLoc;
    }

    public void setTripEndLoc(String tripEndLoc) {
        this.tripEndLoc = tripEndLoc;
    }

    public int getTripType() {
        return tripType;
    }

    public void setTripType(int tripType) {
        this.tripType = tripType;
    }

    public String getTripRoundStartTime() {
        return tripRoundStartTime;
    }

    public void setTripRoundStartTime(String tripRoundStartTime) {
        this.tripRoundStartTime = tripRoundStartTime;
    }

    public String getTripRoundDateTime() {
        return tripRoundDateTime;
    }

    public void setTripRoundDateTime(String tripRoundDateTime) {
        this.tripRoundDateTime = tripRoundDateTime;
    }
}
