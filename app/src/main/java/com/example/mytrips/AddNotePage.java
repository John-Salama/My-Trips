package com.example.mytrips;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddNotePage extends AppCompatActivity {

    EditText newNote_EditText;
    FloatingActionButton addNote_fab;
    TextView note_textView;
    FloatingActionButton deleteNote_fab;
    RecyclerView notes_RecycleView;
    ArrayList<String> notes_arrayList;
    ArrayAdapter<String> adapter;
    String newNote;
    private RecyclerView mRecyclerView;
    NoteAdapter noteAdapter;
    TextView notesEmpty_txt;
    ImageView notesEmpty_img;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseReference mDatabaseReference = FirebaseDatabase.getInstance().getReference(mAuth.getCurrentUser().getUid());
    Intent mIntent = getIntent();
    private String mTripName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_page);
        Bundle extras = getIntent().getExtras();
        mTripName = "";
        if (extras != null)
            mTripName = extras.getString("trip_name");
        mDatabaseReference = mDatabaseReference.child("Trips").child(mTripName).child("Notes");
        initializeContent();
        initializeRecyclerView();

        gettingNotes();
        setAddNote_fab();
        showRecycleView();
        if(noteAdapter.emptyFlag)
        {
            hideRecycleView();
        }


    }

    private void initializeRecyclerView() {
        mRecyclerView = findViewById(R.id.note_list_recyclier);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteAdapter = new NoteAdapter(NotesDataManager.getInstance().getNotes(), AddNotePage.this);
        mRecyclerView.setAdapter(noteAdapter);
    }

    private void initializeContent() {
        newNote_EditText = findViewById(R.id.addNewNote_editText);
        addNote_fab = findViewById(R.id.fab_addNote);
        note_textView = findViewById(R.id.newNote_textView);
        deleteNote_fab = findViewById(R.id.deleteNote_fab);
        notes_RecycleView = findViewById(R.id.note_list_recyclier);
        notesEmpty_img = findViewById(R.id.emptyNote_img);
        notesEmpty_txt = findViewById(R.id.emptyNotes_txt);

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
                else
                    mDatabaseReference.child(newNote_EditText.getText().toString().trim()).child("details").setValue(newNote_EditText.getText().toString().trim());
            });
    }
    private void showRecycleView()
    {
        notesEmpty_img.setVisibility(View.INVISIBLE);
        notesEmpty_txt.setVisibility(View.INVISIBLE);
        notes_RecycleView.setVisibility(View.VISIBLE);
        noteAdapter.emptyFlag = false;
    }
    private void hideRecycleView()
    {
        notesEmpty_img.setVisibility(View.VISIBLE);
        notesEmpty_txt.setVisibility(View.VISIBLE);
        notes_RecycleView.setVisibility(View.INVISIBLE);
    }

    private void gettingNotes() {
        mDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot != null)
                    NotesDataManager.getInstance().getNotes().clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                    NoteData note = new NoteData(dataSnapshot.child("details").getValue(String.class),mTripName);
                    NotesDataManager.getInstance().addNote(note);
                }
                noteAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}


