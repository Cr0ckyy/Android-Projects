package com.myapplicationdev.android.c347_l4_ps_ps_ndp_songs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

// TODO: To use the SQLite database in the app,
//  you must first prepare the plumbing.
//  To do so, create a Java class called DBHelper that extends SQLiteOpenHelper.
public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "songs.db";
    // To make the app calls onUpgrade(), increment the variable DATABASE_VERSION from 1 to 2.
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_SONG = "song";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_TITLE = "title";
    private static final String COLUMN_SINGERS = "singers";
    private static final String COLUMN_YEAR = "year";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSongTableSql = "CREATE TABLE " + TABLE_SONG + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TITLE + " TEXT,"
                + COLUMN_SINGERS + " TEXT,"
                + COLUMN_YEAR + " INTEGER,"
                + COLUMN_STARS + " INTEGER )";
        db.execSQL(createSongTableSql);
        Log.i("info", "created tables");

        // TODO: to prepare the database for testing,
        //  we could create some dummy data during the table creation process.
        //  Dummy records, to be inserted when the database is created
        for (int i = 0; i < 4; i++) {
            ContentValues values = new ContentValues();
            values.put(COLUMN_ID, "Data number " + i);
            values.put(COLUMN_TITLE, "Data number " + i);
            values.put(COLUMN_SINGERS, "Data number " + i);
            values.put(COLUMN_YEAR, "Data number " + i);
            values.put(COLUMN_STARS, "Data number " + i);
            db.insert(TABLE_SONG, null, values);
        }
        Log.i("info", "dummy records inserted");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SONG);

        //To add a column called module_name to an existing table,
        // modify onUpgrade() to execute an SQL statement as below.
        db.execSQL("ALTER TABLE " + TABLE_SONG + " ADD COLUMN module_name TEXT ");

    }

    // TODO: Insert a new record.
    public long insertSong(String songTitle, String songSingers, int songYear, int songStars) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_TITLE, songTitle);
        values.put(COLUMN_SINGERS, songSingers);
        values.put(COLUMN_YEAR, songYear);
        values.put(COLUMN_STARS, songStars);

        // TODO: This line of code will return a number
        //  that represents the record id (the primary key, _id)
        //  of the table for the record that was inserted.
        //  If the insert fails, the id will be -1.
        //  As a result, we can use it to determine whether or not a record was successfully inserted.
        long result = db.insert(TABLE_SONG, null, values);

        db.close();

        Log.d("SQL Insert", "ID:" + result); //id returned, shouldn’t be -1
        return result;
    }

    // TODO: Record retrieval from database table
    //  This method will retrieve the records and convert each one into a String.
    //  Following that, the Strings are placed in an ArrayList to be returned.
    public ArrayList<Song> getAllNotes() {

        ArrayList<Song> notes = new ArrayList<>();

        String selectQuery = "SELECT " + COLUMN_ID + ", "
                + COLUMN_TITLE + ", "
                + COLUMN_SINGERS + ", "
                + COLUMN_YEAR + ", "
                + COLUMN_STARS
                + " FROM " + TABLE_SONG;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                String songContent = cursor.getString(1);
                String nameContent = cursor.getString(2);
                int yearContent = cursor.getInt(3);
                int starContent = cursor.getInt(4);
                Song song = new Song(songContent, nameContent, yearContent, starContent);
                notes.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }

    /*TODO: In order to perform an update,
       a method called updateSong() must be implemented in DBHelper.java.
        The method will accept a Son object and perform a database update. */
    public int updateSong(Song data) {

        // create and/or open a database that will be used for reading and writing.
        SQLiteDatabase db = this.getWritableDatabase();

        // This class is used to store a set of values that the ContentResolver can process.
        ContentValues values = new ContentValues();

        values.put(COLUMN_TITLE, data.getTitle());
        values.put(COLUMN_SINGERS, data.getSingers());
        values.put(COLUMN_YEAR, data.getYear());
        values.put(COLUMN_STARS, data.getStars());

        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};

        /*TODO: This line of code will return a number representing the number
   of rows in the table that have been affected. We usually expect one
   or more records to be updated when we perform a record update.
   However, in this case, we anticipate only one record.
   As a result, if the affected record is 1,
   we can use it to determine whether or not a record was successfully updated.*/
        int result = db.update(TABLE_SONG, values, condition, args);
        db.close();
        return result;
    }


    /*TODO:To delete a note, a deleteNote() method in DBHelper.java must be implemented.
       To delete a record from the database, the method will accept an ID as the primary reference. */
    public int deleteSong(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_SONG, condition, args);
        db.close();
        return result;
    }

    // TODO Methods to show by year| by Myron
    public ArrayList<Integer> getYears() {

        ArrayList<Integer> years = new ArrayList<>();
        String selectQuery = "SELECT " + COLUMN_YEAR + " FROM " + TABLE_SONG;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                if (!years.contains(cursor.getInt(0))) {
                    years.add(cursor.getInt(0));
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return years;
    }

    public ArrayList<Song> getSongsByYear(int filterYear) {
        ArrayList<Song> songs = new ArrayList<Song>();
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = {COLUMN_ID, COLUMN_TITLE, COLUMN_SINGERS, COLUMN_YEAR, COLUMN_STARS};
        String condition = COLUMN_YEAR + " Like ?";
        String[] args = {"%" + filterYear + "%"};
        Cursor cursor = db.query(TABLE_SONG, columns, condition, args,
                null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String singers = cursor.getString(2);
                int year = cursor.getInt(3);
                int stars = cursor.getInt(4);
                Song song = new Song(title, singers, year, stars);
                songs.add(song);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return songs;
    }
}

