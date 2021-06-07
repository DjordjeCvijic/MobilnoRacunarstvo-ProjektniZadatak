package com.example.newsapp.data_base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.newsapp.Model.Articles;
import com.example.newsapp.Model.Source;

import java.util.LinkedList;

public class NewsDbHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "geography_quiz.db";
    private static final String TABLE_NAME = "news";
    public static final int DATABASE_VERSION = 1;

    public static final String COLUMN1_ID = "id";
    public static final String COLUMN2_COUNTRY_MARK = "country_mark";
    public static final String COLUMN3_SOURCE_NAME = "source_name";
    public static final String COLUMN4_AUTHOR = "author";
    public static final String COLUMN5_TITLE= "title";
    public static final String COLUMN6_DESCRIPTION= "description";
    public static final String COLUMN7_URL = "url";
    public static final String COLUMN8_URL_TO_IMAGE = "url_to_image";
    public static final String COLUMN9_PUBLISHED_AT = "published_at";


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN1_ID + " INTEGER PRIMARY KEY," +
                    COLUMN2_COUNTRY_MARK + " TEXT," +
                    COLUMN3_SOURCE_NAME + " TEXT," +
                    COLUMN4_AUTHOR + " TEXT," +
                    COLUMN5_TITLE + " TEXT," +
                    COLUMN6_DESCRIPTION+ " TEXT," +
                    COLUMN7_URL + " TEXT," +
                    COLUMN8_URL_TO_IMAGE + " TEXT," +
                    COLUMN9_PUBLISHED_AT + " TEXT)";


    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public NewsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.i("baza","konstruktor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i("baza","onCreate");
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("baza","onUpdate");
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void insertNewsForSr(Articles articlesToAdd) {
        String countryMark="sr";
        Log.i("baza","Insert");
        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN2_COUNTRY_MARK, countryMark);
        values.put(COLUMN3_SOURCE_NAME, articlesToAdd.getSource().getName());
        values.put(COLUMN4_AUTHOR, articlesToAdd.getAuthor());
        values.put(COLUMN5_TITLE,articlesToAdd.getTitle());
        values.put(COLUMN6_DESCRIPTION, articlesToAdd.getDescription());
        values.put(COLUMN7_URL, articlesToAdd.getUrl());
        values.put(COLUMN8_URL_TO_IMAGE, articlesToAdd.getUrlToImage());
        values.put(COLUMN9_PUBLISHED_AT, articlesToAdd.getPublishedAt());



        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_NAME, null, values);
    }

    public void deleteDB(){
        Log.i("baza","delete");
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL_DELETE_ENTRIES);
    }

    public LinkedList<Articles> getNewsForSr(){
        LinkedList<Articles> articles=new LinkedList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from "+ TABLE_NAME, null );
        res.moveToFirst();


        while(res.isAfterLast() == false){
            Articles articlesTmp=new Articles();
            articlesTmp.setSource(new Source(res.getString(res.getColumnIndex(COLUMN3_SOURCE_NAME))));
            articlesTmp.setAuthor(res.getString(res.getColumnIndex(COLUMN4_AUTHOR)));
            articlesTmp.setTitle(res.getString(res.getColumnIndex(COLUMN5_TITLE)));
            articlesTmp.setDescription(res.getString(res.getColumnIndex(COLUMN6_DESCRIPTION)));
            articlesTmp.setUrl(res.getString(res.getColumnIndex(COLUMN7_URL)));
            articlesTmp.setUrlToImage(res.getString(res.getColumnIndex(COLUMN8_URL_TO_IMAGE)));
            articlesTmp.setPublishedAt(res.getString(res.getColumnIndex(COLUMN9_PUBLISHED_AT)));


            articles.add(articlesTmp);
            res.moveToNext();
        }


        return articles;
    }

    public int numberOfRows(){

        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
        return numRows;
    }
}
