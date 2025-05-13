package com.example.testnotes;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ComposeDialogFragment extends DialogFragment {
    private EditText contentEditText;
    private ImageView attachedImage;
    private String imagePath = null;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_compose, null);

        contentEditText = view.findViewById(R.id.contentEditText);
        attachedImage = view.findViewById(R.id.attachedImage);

        view.findViewById(R.id.attachImageButton).setOnClickListener(v -> attachImage());
        view.findViewById(R.id.postButton).setOnClickListener(v -> postNote());
        view.findViewById(R.id.cancelButton).setOnClickListener(v -> dismiss());

        builder.setView(view);
        return builder.create();
    }

    private void attachImage() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, 1001);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1001 && data != null) {
            Uri uri = data.getData();
            imagePath = uri.toString();
            attachedImage.setVisibility(View.VISIBLE);
            Glide.with(this).load(uri).into(attachedImage);
        }
    }


    private void postNote() {
        String content = contentEditText.getText().toString().trim();
        if (content.isEmpty() && imagePath == null) {
            Toast.makeText(getContext(), "Please enter content or attach an image", Toast.LENGTH_SHORT).show();
            return;
        }

        String timestamp = new SimpleDateFormat("MMM dd, HH:mm", Locale.getDefault())
                .format(new Date());


        Note note = new Note(content, timestamp, null);  // Pass null for the imagePath

        note.imagePath = imagePath;

        ((MainActivity)requireActivity()).addNote(note);
        dismiss();
    }
}