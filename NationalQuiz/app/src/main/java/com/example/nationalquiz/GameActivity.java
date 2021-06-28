package com.example.nationalquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.nationalquiz.data_base.CountryDBHelper;
import com.example.nationalquiz.data_base.CountryDBService;
import com.example.nationalquiz.models.Country;

import java.util.LinkedList;

public class GameActivity extends AppCompatActivity {

    public static LinkedList<Country> countriesDataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        Intent intent=getIntent();
        String str = intent.getStringExtra("gameCategory");

        if(str.equals("Capital Cities")){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            CapitalCitiesFragment fragment = new CapitalCitiesFragment();
            transaction.replace(R.id.fragment_placeholder, fragment);
            transaction.commit();
        }else if(str.equals("Countries Flags")){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            CountriesFlagsFragment fragment = new CountriesFlagsFragment();
            transaction.replace(R.id.fragment_placeholder, fragment);
            transaction.commit();
        }else if(str.equals("Neighboring Countries")){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            NeighboringCountriesFragment fragment = new NeighboringCountriesFragment();
            transaction.replace(R.id.fragment_placeholder, fragment);
            transaction.commit();
        }else if(str.equals("Sights")){
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            SightsFragment fragment = new SightsFragment();
            transaction.replace(R.id.fragment_placeholder, fragment);
            transaction.commit();
        }

        CountryDBHelper countryDBHelper = new CountryDBHelper(GameActivity.this);
        CountryDBService.fillDadaBase(countryDBHelper,this);
        countriesDataList= countryDBHelper.getCountries();
        Log.i("Baza","broj redova"+countryDBHelper.numberOfRows());

    }
}