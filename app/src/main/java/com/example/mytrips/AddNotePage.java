package com.example.mytrips;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class AddNotePage extends AppCompatActivity {

    EditText newNote_EditText;
    FloatingActionButton addNote_fab;
    TextView note_textView;
    FloatingActionButton deleteNote_fab;
    ListView notes_listView;
    ArrayList<String> notes_arrayList;
    ArrayAdapter<String> adapter;
    String newNote;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note_page);
        initializeContent();
        setAddNote_fab();

        //MyCustomAdapter customAdapter = new MyCustomAdapter(notes_arrayList);
       //notes_listView.setAdapter(customAdapter);

    }

    private void initializeContent() {
        newNote_EditText = findViewById(R.id.addNewNote_editText);
        addNote_fab = findViewById(R.id.fab_addNote);
        note_textView = findViewById(R.id.newNote_textView);
        deleteNote_fab = findViewById(R.id.deleteNote_fab);
        notes_listView = findViewById(R.id.notes_list);

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
                else {
                    notes_arrayList.add(newNote);
                    notes_listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });
    }
  /*  class MyCustomAdapter extends BaseAdapter
    {
        ArrayList<String> Items=new ArrayList<String>();
        MyCustomAdapter(ArrayList<String> Items ) {
            this.Items=Items;

        }


        @Override
        public int getCount() {
            return Items.size();
        }

        @Override
        public String getItem(int position) {
            return Items.get(position);

        }

        @Override
        public long getItemId(int position) {
            return  position;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            LayoutInflater linflater =getLayoutInflater();
            View view1=linflater.inflate(R.layout.new_notes_item_list, null);
            note_textView.setText(Items.get(i));
            return view1;
        }
    }

   */
}
