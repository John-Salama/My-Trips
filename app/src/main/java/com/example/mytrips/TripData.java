package com.example.mytrips;

public class TripData {

    private  String name;
    private  String source;
    private  String destination;
    private  String date;
    private  String time;
    private  int status;
    private String roundDate;
    private String roundTime;


    public TripData() {}

    public TripData(String name, String source, String destination, String date, String time) {
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.time = time;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRoundDate() {
        return roundDate;
    }

    public void setRoundDate(String roundDate) {
        this.roundDate = roundDate;
    }

    public String getRoundTime() {
        return roundTime;
    }

    public void setRoundTime(String roundTime) {
        this.roundTime = roundTime;
    }

    public Boolean check()
    {
        boolean flag =false;
        if(getName().isEmpty() || getSource().isEmpty() || getDestination().isEmpty() ||
            getDate().isEmpty() || getTime().isEmpty())
        {
            if (getStatus() == 1)
            {
                if (getRoundDate().isEmpty() || getRoundTime().isEmpty())
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

