package com.myapplicationdev.android.c347_l4_ps_ndp_songs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

// TODO: To use the SQLite database in the app,
//  you must first prepare the plumbing.
//  To do so, create a Java class called DBHeper.java that extends SQLiteOpenHelper.
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "songs.db";
    private static final int DATABASE_VERSION = 2;
    // To make the app calls onUpgrade(), increment the variable DATABASE_VERSION from 1 to 2.
    private static final String TABLE_SONG = "song";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE_CONTENT = "title";
    private static final String COLUMN_SINGERS_CONTENT = "singers";
    private static final String COLUMN_YEARS_CONTENT = "years";
    private static final String COLUMN_STARS_CONTENT = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createNoteTableSql = "CREATE TABLE " + TABLE_SONG
                + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE_CONTENT + " TEXT ,"
                + COLUMN_SINGERS_CONTENT + " TEXT ,"
                + COLUMN_YEARS_CONTENT + " INTEGER ,"
                + COLUMN_STARS_CONTENT + " INTEGER  " +
                ")";
        db.execSQL(createNoteTableSql);
        Log.i("info", "created tables");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);
        onCreate(db);
    }

    public long insertSong(String title, String singer, int year, int stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE_CONTENT, title);
        values.put(COLUMN_SINGERS_CONTENT, singer);
        values.put(COLUMN_YEARS_CONTENT, year);
        values.put(COLUMN_STARS_CONTENT, stars);

        long result = db.insert(TABLE_SONG, null, values);

        // TODO: This line of code will return a number
        //  that represents the record id (the primary key, _id)
        //  of the table for the record that was inserted.
        //  If the insert fails, the id will be -1.
        //  As a result, we can use it to determine whether or not a record was successfully inserted.
        if (result == -1) {
            Log.d("DBHelper", "Insert failed");
        } else {
            Log.d("SQL Insert", "ID:" + result);
        }
        db.close();

        return result;
    }

    // TODO: Record retrieval from database table
    //  This method will retrieve the records and convert each one into a String.
    //  Following that, the Strings are placed in an ArrayList to be returned.
    public ArrayList<Song> getAllSongs() {
        ArrayList<Song> songs = new ArrayList<>();

        String selectQuery = "SELECT "
                + COLUMN_ID + ","
                + COLUMN_TITLE_CONTENT + ","
                + COLUMN_SINGERS_CONTENT + ","
                + COLUMN_YEARS_CONTENT + ","
                + COLUMN_STARS_CONTENT
                + " FROM "
                + TABLE_SONG;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singer = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song newSong = new Song(id, title, singer, year, stars);
                songs.add(newSong);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }

    // TODO: Record retrieval from database table
    //  This method will retrieve the records and convert each one into a String.
    //  Following that, the Strings are placed in an ArrayList to be returned.
    public ArrayList<Integer> getAllYears() {
        ArrayList<Integer> years = new ArrayList<>();

        String selectQuery = "SELECT  DISTINCT" + COLUMN_ID + "," + COLUMN_YEARS_CONTENT + " FROM " + TABLE_SONG;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                int year = cursor.getInt(1);
                years.add(year);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return years;
    }

    /*TODO: In order to perform an update,
        a method called updateNote() must be implemented in DBHelper.java.
         The method will accept a Note object and perform a database update. */
    public void updateSong(Song data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE_CONTENT, data.getTitle());
        values.put(COLUMN_SINGERS_CONTENT, data.getSingers());
        values.put(COLUMN_YEARS_CONTENT, data.getYear());
        values.put(COLUMN_STARS_CONTENT, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.get_id())};
        int result = db.update(TABLE_SONG, values, condition, args);
        if (result == -1) {
            Log.d("DBHelper", "Updated failed");
        } else {
            Log.d("SQL Update", "ID:" + result);
        }
        db.close();
    }

    /*TODO:To delete a note, a deleteNote() method in DBHelper.java must be implemented.
       To delete a record from the database, the method will accept an ID as the primary reference. */
    public void deleteSong(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_SONG, condition, args);
        if (result == -1) {
            Log.d("DBHelper", "Delete failed");
        } else {
            Log.d("SQL Delete", "ID:" + result);
        }
        db.close();
    }


}
