package com.example.testnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;


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

    private TextView notesTab, filesTab;
    private EditText searchEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        notesRecyclerView = findViewById(R.id.notesRecyclerView);
        notesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new NotesAdapter(notesList);
        notesRecyclerView.setAdapter(adapter);

        notesTab = findViewById(R.id.notesTab);
        filesTab = findViewById(R.id.filesTab);
        searchEditText = findViewById(R.id.searchEditText);

        // Tabs logic
        notesTab.setOnClickListener(v -> {
            adapter.showAllNotes();
            highlightTab(true);
        });

        filesTab.setOnClickListener(v -> {
            adapter.showFilesOnly();
            highlightTab(false);
        });

        // Search logic
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> showComposeDialog());
    }

    private void highlightTab(boolean isNotesSelected) {
        notesTab.setBackgroundColor(isNotesSelected ? Color.DKGRAY : Color.TRANSPARENT);
        filesTab.setBackgroundColor(!isNotesSelected ? Color.DKGRAY : Color.TRANSPARENT);
    }

    public void addNote(Note note) {
        adapter.addNote(note); // This handles all list logic and notifies adapter safely
        notesRecyclerView.smoothScrollToPosition(0);
    }

    private void showComposeDialog() {
        ComposeDialogFragment dialog = new ComposeDialogFragment();
        dialog.show(getSupportFragmentManager(), "ComposeDialogFragment");
    }
}