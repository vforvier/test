package com.example.testnotes;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    private final List<Note> originalNotes;  // all notes
    private final List<Note> filteredNotes;  // displayed notes

    public NotesAdapter(List<Note> notes) {
        this.originalNotes = notes;
        this.filteredNotes = new ArrayList<>(notes);
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        Note note = filteredNotes.get(position);
        holder.contentTextView.setText(note.content);
        holder.timeTextView.setText(note.timestamp);

        if (note.imagePath != null) {
            holder.attachedImage.setVisibility(View.VISIBLE);
            Glide.with(holder.itemView).load(note.imagePath).into(holder.attachedImage);
        } else {
            holder.attachedImage.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return filteredNotes.size();
    }

    // Add new note safely
    public void addNote(Note note) {
        originalNotes.add(0, note);
        filteredNotes.add(0, note);
        notifyItemInserted(0);
    }

    // Show all notes
    public void showAllNotes() {
        filteredNotes.clear();
        filteredNotes.addAll(originalNotes);
        notifyDataSetChanged();
    }

    // Show only notes with image
    public void showFilesOnly() {
        filteredNotes.clear();
        for (Note note : originalNotes) {
            if (note.imagePath != null) {
                filteredNotes.add(note);
            }
        }
        notifyDataSetChanged();
    }

    // Search by content
    public void filter(String query) {
        filteredNotes.clear();
        for (Note note : originalNotes) {
            if (note.content.toLowerCase().contains(query.toLowerCase())) {
                filteredNotes.add(note);
            }
        }
        notifyDataSetChanged();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView contentTextView, timeTextView;
        ImageView attachedImage;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            attachedImage = itemView.findViewById(R.id.attachedImage);
        }
    }
}

