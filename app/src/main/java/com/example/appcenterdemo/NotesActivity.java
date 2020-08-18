package com.example.appcenterdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;

public class NotesActivity extends AppCompatActivity {
    TextInputEditText newNoteInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        newNoteInput = findViewById(R.id.newNote);
    }

    private void addNoteToUi(final Note note) {
        System.out.println(note.id);
        final LinearLayout noteLayout = new LinearLayout(this);
        noteLayout.setOrientation(LinearLayout.HORIZONTAL);

        final TextView newText = new TextView(this);
        newText.setText(note.text);
        newText.setTextColor(Color.BLACK);
        noteLayout.addView(newText);

        Button x = new Button(this);
        x.setText("x");
        noteLayout.addView(x);
        final LinearLayout notesLayout = findViewById(R.id.notesLayout);
        x.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.out.println("deleting id :" + note.id);
                notesLayout.removeView(noteLayout);
            }
        });

        LinearLayout layout = findViewById(R.id.notesLayout);
        layout.addView(noteLayout);
        newNoteInput.setText("");
    }

    public void addNote(View view) {
        String noteText = newNoteInput.getText().toString();
        Note note = new Note(noteText);
        addNoteToUi(note);
    }
}
