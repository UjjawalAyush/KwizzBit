package com.ujjawalayush.example.kwizzbit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DBAdapter {
    static final String KEY_ROWID = "id";
    static final String KEY_CATEGORY = "category";
    static final String KEY_TYPE = "type";
    static final String KEY_DIFFICULTY = "difficulty";
    static final String KEY_QUESTION = "question";
    static final String KEY_CORRECT = "correct";
    static final String KEY_IN1 = "incorrect1";
    static final String KEY_IN2 = "incorrect2";
    static final String KEY_IN3 = "incorrect3";
    static final String DATABASE_NAME = "MUSIC";
    static final String DATABASE_TABLE = "music";
    static final String TAG = "DBAdapter";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE_CREATE = "create table "+DATABASE_TABLE+" ("+KEY_ROWID+" integer primary key autoincrement,"+KEY_CATEGORY+" text not null,"+KEY_TYPE+" text not null,"+KEY_DIFFICULTY+" text not null,"+KEY_QUESTION+" text not null,"+KEY_CORRECT+" text not null,"+KEY_IN1+" text not null,"+KEY_IN2+" text not null,"+KEY_IN3+" text not null);";
    private final Context context;

    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DATABASE_CREATE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion
                    + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS titles");
            onCreate(db);
        }
    }
    public DBAdapter open() throws SQLException
    {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        DBHelper.close();
    }
    public long insertContact(String email, String password,String difficulty, String question,String correct, String in1,String in2, String in3)
    {
        db = DBHelper.getWritableDatabase();
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_CATEGORY,email);
        initialValues.put(KEY_TYPE,password);
        initialValues.put(KEY_DIFFICULTY,difficulty);
        initialValues.put(KEY_QUESTION,question);
        initialValues.put(KEY_CORRECT,correct);
        initialValues.put(KEY_IN1,in1);
        initialValues.put(KEY_IN2,in2);
        initialValues.put(KEY_IN3,in3);
        return db.insert(DATABASE_TABLE, null,initialValues);
    }

    //---deletes a particular title---
    public boolean deleteContact(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID +
                "=" + rowId, null) > 0;
    }

    //---retrieves all the titles---
    public Cursor getAllContacts()
    {
        return db.query(DATABASE_TABLE, new String[] {
                        KEY_ROWID,
                        KEY_CATEGORY,
                        KEY_TYPE,
                KEY_DIFFICULTY,
                KEY_QUESTION,KEY_CORRECT,KEY_IN1,KEY_IN2,KEY_IN3},
                null,
                null,
                null,
                null,
                null);
    }

    //---retrieves a particular title---
    public Cursor getContact(long rowId) throws SQLException
    {
                return db.query(true, DATABASE_TABLE, new String[] {
                                KEY_ROWID,
                                KEY_CATEGORY,
                                KEY_TYPE,
                                KEY_DIFFICULTY,
                                KEY_QUESTION,KEY_CORRECT,KEY_IN1,KEY_IN2,KEY_IN3},
                        KEY_ROWID + "=?",
                        new String[]{String.valueOf(rowId)},
                        null,
                        null,
                        null,
                        null);
    }
}
