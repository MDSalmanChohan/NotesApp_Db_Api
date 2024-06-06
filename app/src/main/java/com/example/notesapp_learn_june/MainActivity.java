package com.example.notesapp_learn_june;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";

    private Button addNoteButton;
    private ListView notesListView;
    private ArrayAdapter<String> notesAdapter;
    private ArrayList<String> notesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addNoteButton = findViewById(R.id.addNoteButton);
        notesListView = findViewById(R.id.notesListView);
        notesList = new ArrayList<>();
        notesAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, notesList);
        notesListView.setAdapter(notesAdapter);

        addNoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivity(intent);
            }
        });

        loadNotes();
        fetchNotesFromApi();
    }

    private void loadNotes() {
        notesList.clear();
        NotesDbHelper dbHelper = new NotesDbHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        String[] projection = {
                NoteContract.NoteEntry._ID,
                NoteContract.NoteEntry.COLUMN_NAME_TITLE,
                NoteContract.NoteEntry.COLUMN_NAME_CONTENT
        };

        Cursor cursor = db.query(
                NoteContract.NoteEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String title = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NAME_TITLE));
            String content = cursor.getString(cursor.getColumnIndexOrThrow(NoteContract.NoteEntry.COLUMN_NAME_CONTENT));
            notesList.add(title + "\n" + content);
        }
        cursor.close();
        notesAdapter.notifyDataSetChanged();
    }

    private void fetchNotesFromApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiService apiService = retrofit.create(ApiService.class);
        Call<List<Note>> call = apiService.getNotes();
        call.enqueue(new Callback<List<Note>>() {
            @Override
            public void onResponse(Call<List<Note>> call, Response<List<Note>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    for (Note note : response.body()) {
                        notesList.add(note.getTitle() + "\n" + note.getContent());
                    }
                    notesAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(MainActivity.this, "Failed to fetch notes", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Note>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
