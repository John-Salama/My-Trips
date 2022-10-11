package com.example.mytrips;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {

    List<NoteData> mNoteData;
    Context mContext;
    public NoteAdapter(List<NoteData> noteData, AddNotePage newNote)
    {
        this.mNoteData = noteData;
        this.mContext = newNote;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.new_notes_item_list,parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.noteData.setText(mNoteData.get(position).getInfo());
    }

    @Override
    public int getItemCount() {
        if(mNoteData == null) {
            return 0;
        }else{
            return mNoteData.size();
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView noteData;
        FloatingActionButton deleteFab;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            noteData = itemView.findViewById(R.id.newNote_textView);
            deleteFab = itemView.findViewById(R.id.deleteNote_fab);
            deleteFab.setOnClickListener(v -> {
                int position  =  getAdapterPosition();
                mNoteData.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mNoteData.size());
            });
        }
    }
}
