package com.example.mytrips;

import java.util.ArrayList;
import java.util.List;

public class NotesDataManager {
    private static volatile NotesDataManager instance = null;
    private final List<NoteData> notes = new ArrayList<>();


    private NotesDataManager(){}

    public static NotesDataManager getInstance() {
        if (instance == null) {
            synchronized (NotesDataManager.class) {
                if (instance == null) {
                    instance = new NotesDataManager();
                }
            }
        }
        return instance;
    }



    public List<NoteData> getNotes() {
        return notes;
    }


    public void addNote(NoteData note) {
        notes.add(note);
    }
}
