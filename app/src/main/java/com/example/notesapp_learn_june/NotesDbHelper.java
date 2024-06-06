package com.example.notesapp_learn_june;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class NotesDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notes.db";
    private static final int DATABASE_VERSION = 1;

    public NotesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_NOTES_TABLE = "CREATE TABLE " +
                NoteContract.NoteEntry.TABLE_NAME + " (" +
                NoteContract.NoteEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                NoteContract.NoteEntry.COLUMN_NAME_TITLE + " TEXT NOT NULL, " +
                NoteContract.NoteEntry.COLUMN_NAME_CONTENT + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_NOTES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NoteContract.NoteEntry.TABLE_NAME);
        onCreate(db);
    }
}
