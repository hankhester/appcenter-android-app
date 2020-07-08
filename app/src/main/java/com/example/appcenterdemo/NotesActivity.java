package com.example.appcenterdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.icu.text.SymbolTable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.material.textfield.TextInputEditText;
import com.microsoft.appcenter.data.Data;
import com.microsoft.appcenter.data.DefaultPartitions;
import com.microsoft.appcenter.data.models.DocumentWrapper;
import com.microsoft.appcenter.data.models.PaginatedDocuments;
import com.microsoft.appcenter.utils.async.AppCenterConsumer;

import net.minidev.json.JSONObject;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.parser.ParseException;

import java.util.UUID;

public class NotesActivity extends AppCompatActivity {
    TextInputEditText newNoteInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);
        newNoteInput = findViewById(R.id.newNote);

        fetchNotes();
    }

    private void fetchNotes() {
        Data.list(Note.class, DefaultPartitions.USER_DOCUMENTS).thenAccept(new AppCenterConsumer<PaginatedDocuments<Note>>() {
            @Override
            public void accept(PaginatedDocuments<Note> documentWrappers) {
                try {
                    for (DocumentWrapper<Note> dw : documentWrappers) {
                        addNoteToUi(dw.getDeserializedValue());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
                System.out.println("deleting id:" + note.id);
                Data.delete(note.id, DefaultPartitions.USER_DOCUMENTS);
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
        Data.create(note.id, note, Note.class, DefaultPartitions.USER_DOCUMENTS);
    }
}
