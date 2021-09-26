package com.MRProject.nationalquiz.data_base;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.MRProject.nationalquiz.models.Country;

import java.util.LinkedList;

public class CountryDBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "country_data.db";
    private static final String TABLE_NAME = "country";
    public static final int DATABASE_VERSION = 1;

    public static final String COLUMN1_ID = "id";
    public static final String COLUMN2_MARK = "mark";
    public static final String COLUMN3_NAME_SR = "name_sr";
    public static final String COLUMN4_NAME_EN = "name_en";
    public static final String COLUMN5_CAPITAL_CITY_SR = "capital_city_sr";
    public static final String COLUMN6_CAPITAL_CITY_EN = "capital_city_en";
    public static final String COLUMN7_NEIGHBORING_COUNTRY_SR = "neighboring_country_sr";
    public static final String COLUMN8_NEIGHBORING_COUNTRY_EN = "neighboring_country_en";
    public static final String COLUMN9_COUNTRY_LANDMARK_SR = "country_landmark_sr";
    public static final String COLUMN10_COUNTRY_LANDMARK_EN = "country_landmark_en";
    public static final String COLUMN11_CAPITAL_CITY_LATITUDE = "capital_city_latitude";
    public static final String COLUMN12_CAPITAL_CITY_LONGITUDE = "capital_city_longitude";
    public static final String COLUMN13_CITY_COAT_OF_ARMS_IMAGE = "city_coat_of_arms_image";
    public static final String COLUMN14_FLAG_IMAGE = "flag_image";
    public static final String COLUMN15_LANDMARK_IMAGE = "landmark_image";


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN1_ID + " INTEGER PRIMARY KEY," +
                    COLUMN2_MARK + " TEXT," +
                    COLUMN3_NAME_SR + " TEXT," +
                    COLUMN4_NAME_EN + " TEXT," +
                    COLUMN5_CAPITAL_CITY_SR + " TEXT," +
                    COLUMN6_CAPITAL_CITY_EN + " TEXT," +
                    COLUMN7_NEIGHBORING_COUNTRY_SR + " TEXT," +
                    COLUMN8_NEIGHBORING_COUNTRY_EN + " TEXT," +
                    COLUMN9_COUNTRY_LANDMARK_SR + " TEXT," +
                    COLUMN10_COUNTRY_LANDMARK_EN + " TEXT," +
                    COLUMN11_CAPITAL_CITY_LATITUDE + " REAL," +
                    COLUMN12_CAPITAL_CITY_LONGITUDE + " REAL," +
                    COLUMN13_CITY_COAT_OF_ARMS_IMAGE + " TEXT," +
                    COLUMN14_FLAG_IMAGE + " TEXT," +
                    COLUMN15_LANDMARK_IMAGE + " TEXT)";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public CountryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_CREATE_ENTRIES);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over

        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    public void insert(Country countryToInsert) {

        // Gets the data repository in write mode
        SQLiteDatabase db = this.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(COLUMN2_MARK, countryToInsert.getMark());
        values.put(COLUMN3_NAME_SR, countryToInsert.getNameSr());
        values.put(COLUMN4_NAME_EN, countryToInsert.getNameEn());
        values.put(COLUMN5_CAPITAL_CITY_SR, countryToInsert.getCapitalCitySr());
        values.put(COLUMN6_CAPITAL_CITY_EN, countryToInsert.getCapitalCityEn());
        values.put(COLUMN7_NEIGHBORING_COUNTRY_SR, countryToInsert.getNeighboringCountrySr());
        values.put(COLUMN8_NEIGHBORING_COUNTRY_EN, countryToInsert.getNeighboringCountryEn());
        values.put(COLUMN9_COUNTRY_LANDMARK_SR, countryToInsert.getCountryLandmarkSr());
        values.put(COLUMN10_COUNTRY_LANDMARK_EN, countryToInsert.getCountryLandmarkEn());
        values.put(COLUMN11_CAPITAL_CITY_LATITUDE, countryToInsert.getCapitalCityLatitude());
        values.put(COLUMN12_CAPITAL_CITY_LONGITUDE, countryToInsert.getCapitalCityLongitude());
        values.put(COLUMN13_CITY_COAT_OF_ARMS_IMAGE, countryToInsert.getCityCoatOfArmsImage());
        values.put(COLUMN14_FLAG_IMAGE, countryToInsert.getFlagImage());
        values.put(COLUMN15_LANDMARK_IMAGE, countryToInsert.getLandmarkImage());

        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(TABLE_NAME, null, values);
    }






    public LinkedList<Country> getCountries() {
        LinkedList<Country> countries = new LinkedList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TABLE_NAME, null);
        res.moveToFirst();


        while (res.isAfterLast() == false) {
            Country countryTmp = new Country();
            countryTmp.setId(res.getLong(res.getColumnIndex(COLUMN1_ID)));
            countryTmp.setMark(res.getString(res.getColumnIndex(COLUMN2_MARK)));
            countryTmp.setNameSr(res.getString(res.getColumnIndex(COLUMN3_NAME_SR)));
            countryTmp.setNameEn(res.getString(res.getColumnIndex(COLUMN4_NAME_EN)));
            countryTmp.setCapitalCitySr(res.getString(res.getColumnIndex(COLUMN5_CAPITAL_CITY_SR)));
            countryTmp.setCapitalCityEn(res.getString(res.getColumnIndex(COLUMN6_CAPITAL_CITY_EN)));
            countryTmp.setNeighboringCountrySr(res.getString(res.getColumnIndex(COLUMN7_NEIGHBORING_COUNTRY_SR)));
            countryTmp.setNeighboringCountryEn(res.getString(res.getColumnIndex(COLUMN8_NEIGHBORING_COUNTRY_EN)));
            countryTmp.setCountryLandmarkSr(res.getString(res.getColumnIndex(COLUMN9_COUNTRY_LANDMARK_SR)));
            countryTmp.setCountryLandmarkEn(res.getString(res.getColumnIndex(COLUMN10_COUNTRY_LANDMARK_EN)));
            countryTmp.setCapitalCityLatitude(res.getDouble(res.getColumnIndex(COLUMN11_CAPITAL_CITY_LATITUDE)));
            countryTmp.setCapitalCityLongitude(res.getDouble(res.getColumnIndex(COLUMN12_CAPITAL_CITY_LONGITUDE)));
            countryTmp.setCityCoatOfArmsImage(res.getString(res.getColumnIndex(COLUMN13_CITY_COAT_OF_ARMS_IMAGE)));
            countryTmp.setFlagImage(res.getString(res.getColumnIndex(COLUMN14_FLAG_IMAGE)));
            countryTmp.setLandmarkImage(res.getString(res.getColumnIndex(COLUMN15_LANDMARK_IMAGE)));

            countries.add(countryTmp);
            res.moveToNext();
        }

        res.close();
        return countries;
    }

    public int numberOfRows() {

        SQLiteDatabase db = this.getReadableDatabase();
        return (int) DatabaseUtils.queryNumEntries(db, TABLE_NAME);
    }
}