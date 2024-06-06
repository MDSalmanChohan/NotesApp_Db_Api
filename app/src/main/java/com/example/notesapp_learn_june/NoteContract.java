package com.example.notesapp_learn_june;

import android.provider.BaseColumns;

public final class NoteContract {
    private NoteContract() {}

    public static class NoteEntry implements BaseColumns {
        public static final String TABLE_NAME = "notes";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_CONTENT = "content";
    }
}
