package com.example.mytrips;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class AddNotePage extends AppCompatActivity {

    EditText newNote_EditText;
    FloatingActionButton addNote_fab;
    TextView note_textView;
    FloatingActionButton deleteNote_fab;
    RecyclerView notes_listView;
    ArrayList<String> notes_arrayList;
    ArrayAdapter<String> adapter;
    String newNote;
    private final List<NoteData> mNotes = new ArrayList<>();
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_page);
        initializeContent();
        initializeRecyclerView();
        setAddNote_fab();

    }


    private void initializeRecyclerView() {
        mRecyclerView = findViewById(R.id.note_list_recyclier);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        NoteAdapter noteAdapter = new NoteAdapter(mNotes, AddNotePage.this);
        mRecyclerView.setAdapter(noteAdapter);
    }

    private void initializeContent() {
        newNote_EditText = findViewById(R.id.addNewNote_editText);
        addNote_fab = findViewById(R.id.fab_addNote);
        note_textView = findViewById(R.id.newNote_textView);
        deleteNote_fab = findViewById(R.id.deleteNote_fab);
        notes_listView = findViewById(R.id.note_list_recyclier);

        notes_arrayList  = new ArrayList<>();
        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,notes_arrayList);
    }
    private void setAddNote_fab()
    {
            addNote_fab.setOnClickListener(view -> {
                newNote = newNote_EditText.getText().toString();
                if(newNote.isEmpty())
                {
                    Toast.makeText(AddNotePage.this,"Note is empty",Toast.LENGTH_LONG).show();
                }
                else{
                        mNotes.add(new NoteData(newNote));
                        NoteAdapter noteAdapter = new NoteAdapter(mNotes, AddNotePage.this);
                        mRecyclerView.setAdapter(noteAdapter);
                        newNote_EditText.setText("");
                    }
            });
    }
}
