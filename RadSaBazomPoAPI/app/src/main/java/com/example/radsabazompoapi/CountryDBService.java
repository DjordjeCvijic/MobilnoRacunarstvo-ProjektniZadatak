package com.example.radsabazompoapi;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CountryDBService {

    public static void fillDadaBase(CountryDbHelper dbHelper, Context context){
        if(dbHelper.numberOfRows()!=19){
            Log.i("Baza","Generisanje baze");
            //dbHelper.deleteDB();
            Country country=new Country();

            
            InputStream is = context.getResources().openRawResource(R.raw.drzave);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String lineFromFile = "";
            String []argumentsOfCity;
            try {
                lineFromFile= reader.readLine();
                while(lineFromFile!=null){

                    argumentsOfCity=lineFromFile.split(",");
                    country.setMark(argumentsOfCity[0]);
                    country.setNameSr(argumentsOfCity[1]);
                    country.setNameEn(argumentsOfCity[2]);
                    country.setCapitalCitySr(argumentsOfCity[3]);
                    country.setCapitalCityEn(argumentsOfCity[4]);
                    country.setNeighboringCountrySr(argumentsOfCity[5]);
                    country.setNeighboringCountryEn(argumentsOfCity[6]);
                    country.setCountryLandmarkSr(argumentsOfCity[7]);
                    country.setCountryLandmarkEn(argumentsOfCity[8]);
                    country.setCapitalCityLatitude(Double.parseDouble(argumentsOfCity[9]));
                    country.setCapitalCityLongitude(Double.parseDouble(argumentsOfCity[10]));
                    country.makeCityCoatOfArmsImageProperty();
                    country.makeFlagImageProperty();
                    country.makeLandmarkImageProperty();

                    dbHelper.insert(country);
                    lineFromFile = reader.readLine();
                }
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
