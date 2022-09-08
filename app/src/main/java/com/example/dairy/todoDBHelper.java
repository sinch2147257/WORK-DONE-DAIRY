package com.example.dairy;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.dairy.todoContract.*;
public class todoDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "tod.db";
    public static final int DATABASE_VERSION = 1;
    public todoDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_TOLIST_TABLE = "CREATE TABLE " +
                todoEntry.TABLE_NAME + " (" +
                todoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                todoEntry.COLUMN_NAME + " TEXT NOT NULL, " +

                todoEntry.COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                ");";
        db.execSQL(SQL_CREATE_TOLIST_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + todoEntry.TABLE_NAME);
        onCreate(db);
    }
}
