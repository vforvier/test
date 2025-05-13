package com.example.testnotes;

import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.util.ArrayList;
import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NoteViewHolder> {
    private final List<Note> originalNotes;
    private final List<Note> filteredNotes;

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

        holder.menuButton.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(view.getContext(), holder.menuButton);
            MenuInflater inflater = popupMenu.getMenuInflater();
            inflater.inflate(R.menu.menu_note, popupMenu.getMenu());

            popupMenu.setOnMenuItemClickListener(item -> {
                if (item.getItemId() == R.id.action_edit) {
                    showEditDialog(view, note, holder.getAdapterPosition());
                    return true;
                }
                if (item.getItemId() == R.id.action_delete) {
                    deleteNote(holder.getAdapterPosition());
                    return true;
                }
                return false;
            });
            popupMenu.show();
        });
    }

    @Override
    public int getItemCount() {
        return filteredNotes.size();
    }

    public void addNote(Note note) {
        originalNotes.add(0, note);
        filteredNotes.add(0, note);
        notifyItemInserted(0);
    }

    public void showAllNotes() {
        filteredNotes.clear();
        filteredNotes.addAll(originalNotes);
        notifyDataSetChanged();
    }

    public void showFilesOnly() {
        filteredNotes.clear();
        for (Note note : originalNotes) {
            if (note.imagePath != null) {
                filteredNotes.add(note);
            }
        }
        notifyDataSetChanged();
    }

    public void filter(String query) {
        filteredNotes.clear();
        for (Note note : originalNotes) {
            if (note.content.toLowerCase().contains(query.toLowerCase())) {
                filteredNotes.add(note);
            }
        }
        notifyDataSetChanged();
    }

    public void deleteNote(int position) {
        originalNotes.remove(position);  // Remove from the full list
        filteredNotes.remove(position);  // Remove from the displayed list
        notifyItemRemoved(position);  // Notify the adapter that the item has been removed
    }

    private void showEditDialog(View parentView, Note note, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(parentView.getContext());
        View view = LayoutInflater.from(parentView.getContext()).inflate(R.layout.edit_note_popup, null);
        builder.setView(view);

        EditText editContentEditText = view.findViewById(R.id.editContentEditText);
        ImageView editAttachedImage = view.findViewById(R.id.editAttachedImage);
        MaterialButton attachButton = view.findViewById(R.id.editAttachImageButton);
        MaterialButton cancelButton = view.findViewById(R.id.editCancelButton);
        MaterialButton saveButton = view.findViewById(R.id.editSaveButton);

        editContentEditText.setText(note.getContent());

        if (note.imagePath != null) {
            editAttachedImage.setVisibility(View.VISIBLE);
            Glide.with(parentView.getContext()).load(note.imagePath).into(editAttachedImage);
        } else {
            editAttachedImage.setVisibility(View.GONE);
        }

        AlertDialog dialog = builder.create();

        saveButton.setOnClickListener(v -> {
            note.setContent(editContentEditText.getText().toString().trim());
            notifyItemChanged(position);
            dialog.dismiss();
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        attachButton.setOnClickListener(v -> {
            // Add logic to attach a new image if desired
        });

        dialog.show();
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView contentTextView, timeTextView;
        ImageView attachedImage, menuButton;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            contentTextView = itemView.findViewById(R.id.contentTextView);
            timeTextView = itemView.findViewById(R.id.timeTextView);
            attachedImage = itemView.findViewById(R.id.attachedImage);
            menuButton = itemView.findViewById(R.id.menuButton); // Reference to the 3-dot menu button
        }
    }
}
