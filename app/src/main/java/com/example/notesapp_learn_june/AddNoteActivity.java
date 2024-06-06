package com.example.notesapp_learn_june;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notesapp_learn_june.NoteContract;
import com.example.notesapp_learn_june.NotesDbHelper;
import com.example.notesapp_learn_june.R;

public class AddNoteActivity extends AppCompatActivity {
    private EditText noteTitleEditText;
    private EditText noteContentEditText;
    private Button saveNoteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        noteTitleEditText = findViewById(R.id.noteTitleEditText);
        noteContentEditText = findViewById(R.id.noteContentEditText);
        saveNoteButton = findViewById(R.id.saveNoteButton);

        saveNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveNote();
            }
        });
    }

    private void saveNote() {
        String title = noteTitleEditText.getText().toString();
        String content = noteContentEditText.getText().toString();

        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        NotesDbHelper dbHelper = new NotesDbHelper(this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NoteContract.NoteEntry.COLUMN_NAME_TITLE, title);
        values.put(NoteContract.NoteEntry.COLUMN_NAME_CONTENT, content);

        long newRowId = db.insert(NoteContract.NoteEntry.TABLE_NAME, null, values);

        if (newRowId != -1) {
            Toast.makeText(this, "Note saved", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error saving note", Toast.LENGTH_SHORT).show();
        }
    }
}
