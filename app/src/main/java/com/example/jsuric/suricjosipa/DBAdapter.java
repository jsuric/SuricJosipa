package com.example.jsuric.suricjosipa;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by jsuric on 2/23/18.
 */

public class DBAdapter {

    static final String KEY_ROWID = "_id";
    static final String KEY_IME = "ime";
    static final String KEY_AUTOR = "autor";
    static final String KEY_ID_FILMA = "_id_filma";
    static final String KEY_ID_LIKA = "_id_lika";
    static final String TAG = "DBAdapter";

    static final String DATABASE_NAME = "MyDB";
    static final String DATABASE_TABLE = "lik";
    static final String DATABASE_TABLE2 = "film";
    static final int DATABASE_VERSION = 5;

    static final String DATABASE_CREATE_LIK =
            "create table lik (_id integer primary key, "
                    + "ime text not null, autor text not null);";

    static final String DATABASE_CREATE_FILM =
            "create table film (_id_filma text, "
                    + "_id_lika integer primary key);";

    final Context context;

    DatabaseHelper DBHelper;
    SQLiteDatabase db;

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
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE_LIK);
                db.execSQL(DATABASE_CREATE_FILM);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading db from" + oldVersion + "to"
                    + newVersion );
            db.execSQL("DROP TABLE IF EXISTS lik");
            db.execSQL("DROP TABLE IF EXISTS film");
            onCreate(db);
        }
    }

    //---opens the database---
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

    //---insert a character into the database---
    public long insertLik(long id, String ime, String autor)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ROWID, id);
        initialValues.put(KEY_IME, ime);
        initialValues.put(KEY_AUTOR, autor);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---insert film into the database---
    public long insertFilm(String film, Integer id_lika)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_ID_FILMA, film);
        initialValues.put(KEY_ID_LIKA, id_lika);
        return db.insert(DATABASE_TABLE2, null, initialValues);
    }

    //---deletes a particular character---
    public boolean deleteLik(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---deletes a particular film---
    public boolean deleteFilm(String rowId)
    {
        return db.delete(DATABASE_TABLE2, KEY_ID_FILMA + "=" + rowId, null) > 0;
    }

    //---deletes a particular film by ID---
    public boolean deleteFilmBYID(long rowId)
    {
        return db.delete(DATABASE_TABLE2, KEY_ID_LIKA + "=" + rowId, null) > 0;
    }

    //---retrieves all the characters---
    public Cursor getAllLikovi()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_IME,
                KEY_AUTOR}, null, null, null, null, null);
    }

    //---retrieves all the films---
    public Cursor getAllFilmovi()
    {
        return db.query(DATABASE_TABLE2, new String[] {KEY_ID_FILMA, KEY_ID_LIKA
        }, null, null, null, null, null);
    }

    //---retrieves a particular character---
    public Cursor getLik(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_IME, KEY_AUTOR}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---retrieves a particular character by name---
    /*
    public Cursor getLikbyName(String ime) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_IME, KEY_AUTOR}, KEY_IME + "=" + ime, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    } */
    //---retrieves a particular film---
    public Cursor getFilm(String rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE2, new String[] {KEY_ID_FILMA,
                                KEY_ID_LIKA}, KEY_ID_FILMA + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a character---
    public boolean updateLik(long rowId, String ime, String autor)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_IME, ime);
        args.put(KEY_AUTOR, autor);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---updates a film---
    public boolean updateFilm(String rowId, long likId)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_ID_LIKA, likId);
        return db.update(DATABASE_TABLE2, args, KEY_ID_FILMA + "=" + rowId, null) > 0;
    }


}
