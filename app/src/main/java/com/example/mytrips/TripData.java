package com.example.mytrips;

public class TripData {

    private  String name;
    private  String source;
    private  String destination;
    private  String date;
    private  String time;
    private  String status;

    public TripData(String name, String source, String destination, String date, String time, String status) {
        this.name = name;
        this.source = source;
        this.destination = destination;
        this.date = date;
        this.time = time;
        this.status = status;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

