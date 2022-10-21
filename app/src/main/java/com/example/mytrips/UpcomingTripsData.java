package com.example.mytrips;

public class UpcomingTripsData {

    String tripStartTime;
    String tripDate;
    String tripName;
    String tripStatus;
    String tripStartLoc;
    String tripEndLoc;
    int tripType;
    String tripRoundStartTime;
    String tripRoundDate;

    public UpcomingTripsData(String tripStartTime, String tripDate, String tripName, String tripStatus, String tripStartLoc, String tripEndLoc, int tripType, String tripRoundStartTime, String tripRoundDate) {
        this.tripStartTime = tripStartTime;
        this.tripDate = tripDate;
        this.tripName = tripName;
        this.tripStatus = tripStatus;
        this.tripStartLoc = tripStartLoc;
        this.tripEndLoc = tripEndLoc;
        this.tripType = tripType;
        this.tripRoundStartTime = tripRoundStartTime;
        this.tripRoundDate = tripRoundDate;
    }

    public void setTripStartTime(String tripStartTime) {
        this.tripStartTime = tripStartTime;
    }

    public void setTripDate(String tripDate) {
        this.tripDate = tripDate;
    }

    public void setTripName(String tripName) {
        this.tripName = tripName;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public void setTripStartLoc(String tripStartLoc) {
        this.tripStartLoc = tripStartLoc;
    }

    public void setTripEndLoc(String tripEndLoc) {
        this.tripEndLoc = tripEndLoc;
    }

    public void setTripType(int tripType) {
        this.tripType = tripType;
    }

    public void setTripRoundStartTime(String tripRoundStartTime) {
        this.tripRoundStartTime = tripRoundStartTime;
    }

    public void setTripRoundDate(String tripRoundDate) {
        this.tripRoundDate = tripRoundDate;
    }

    public UpcomingTripsData() {}

    public String getTripStartTime() {
        return tripStartTime;
    }


    public String getTripDate() {
        return tripDate;
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

    public String getTripRoundDate() {
        return tripRoundDate;
    }

    public Boolean check()
    {
        boolean flag =false;
        if(getTripName().isEmpty() || getTripStartLoc().isEmpty() || getTripEndLoc().isEmpty() ||
                getTripDate().isEmpty() || getTripStartTime().isEmpty())
        {
            if (getTripType() == 1)
            {
                if (getTripRoundDate().isEmpty() || getTripRoundStartTime().isEmpty())
                {
                    flag = false;
                }
                else
                {
                    flag = true;
                }
            }
            else {
                flag = true;
            }
        }
        return flag;
    }

}
