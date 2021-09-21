package com.MRProject.nationalquiz.services;

import android.content.Context;
import android.util.Log;

import com.MRProject.nationalquiz.R;
import com.MRProject.nationalquiz.data_base.CountryDBHelper;
import com.MRProject.nationalquiz.models.Country;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class CountryDBService {

    public static void fillDadaBase(CountryDBHelper dbHelper, Context context){
        if(dbHelper.numberOfRows()!=20){
            Log.i("Baza","Generisanje baze");
            Country country=new Country();

            
            InputStream is = context.getResources().openRawResource(R.raw.countries_data);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String lineFromFile = "";
            String []argumentsOfCity;
            try {
                lineFromFile= reader.readLine();

                while(lineFromFile!=null){
                    Log.i("Baza",lineFromFile);
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
