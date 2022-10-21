package com.example.mytrips;

public class NoteData {
    String info;
    String tripNoteName;

    public String getTripNoteName() {
        return tripNoteName;
    }

    public NoteData(String info, String tripNoteName) {
        this.info = info;
        this.tripNoteName = tripNoteName;
    }

    public String getInfo() {
        return info;
    }
}
