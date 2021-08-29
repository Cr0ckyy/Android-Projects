package com.myapplicationdev.android.c347_l3_ex1_demo_database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VER = 1;
    private static final String DATABASE_NAME = "tasks.db";
    private static final String TABLE_TASK = "task";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_DATE = "date";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String createStatement = "CREATE TABLE " + TABLE_TASK + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT )";
        sqLiteDatabase.execSQL(createStatement);
        Log.i("info", "create tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        onCreate(sqLiteDatabase);
    }

    // TODO:Insert a new record.
    public void insertTask(String description, String date) {

        // Get an instance of the database for writing
        SQLiteDatabase db = this.getWritableDatabase();

        // We use ContentValues object to store the values for
        //  the db operation
        ContentValues values = new ContentValues();

        // Store the column name as key and the description as value
        values.put(COLUMN_DESCRIPTION, description);

        // Store the column name as key and the date as value
        values.put(COLUMN_DATE, date);

        // Insert the row into the TABLE_TASK
        db.insert(TABLE_TASK, null, values);

        // Close the database connection
        db.close();
    }

    // TODO: Record retrieval from database table
    //  This method will retrieve the records and convert each one into a String.
    //  Following that, the Strings are placed in an ArrayList to be returned.
    public ArrayList<Task> getTaskContent(String data) {


        ArrayList<Task> tasks = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Crude method in retrieving data
//       String selectQuery = "SELECT " + COLUMN_ID + ", " +
//       COLUMN_DESCRIPTION + ", " + COLUMN_DATE + " FROM " + TABLE_TASK;
//        Cursor cursor = db.rawQuery(selectQuery, null);


        // Todo: Efficient method of getting data
        String[] dbColumns = {COLUMN_ID, COLUMN_DESCRIPTION, COLUMN_DATE};
        String dbCondition = COLUMN_DESCRIPTION + " Like ?";
        String[] dbArguments = {"%" + data + "%"};

        Cursor cursor = db.query(TABLE_TASK, dbColumns, dbCondition, dbArguments,
                null, null, null, null);


        if (cursor.moveToFirst()) {
            do {
                //tasks.add(cursor.getString(0));
                int id = cursor.getInt(0);
                String description = cursor.getString(1);
                String date = cursor.getString(2);
                Task obj = new Task(id, description, date);
                tasks.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return tasks;
    }
}

