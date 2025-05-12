package com.example.testnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView notesRecyclerView;
    private NotesAdapter adapter;
    private List<Note> notesList = new ArrayList<>();
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup RecyclerView
        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotesAdapter(notesList);
        notesRecyclerView.setAdapter(adapter);

        // FAB for new note
        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showComposeDialog());
    }

    public void addNote(Note note) {
        notesList.add(0, note); // Add to top
        adapter.notifyItemInserted(0);
        notesRecyclerView.smoothScrollToPosition(0);
    }

    private void showComposeDialog() {
        ComposeDialogFragment dialog = new ComposeDialogFragment();
        dialog.show(getSupportFragmentManager(), "ComposeDialogFragment");
    }
}