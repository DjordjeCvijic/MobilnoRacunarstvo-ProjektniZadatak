package com.example.nationalquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.nationalquiz.data_base.CountryDBHelper;
import com.example.nationalquiz.data_base.CountryDBService;
import com.example.nationalquiz.games.CapitalCitiesActivity;
import com.example.nationalquiz.games.CountryFlagActivity;
import com.example.nationalquiz.games.CountrySightsActivity;
import com.example.nationalquiz.games.NeighboringCountryActivity;


public class MainActivity extends AppCompatActivity {

    Button startGameBtn;
    Button settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CountryDBHelper countryDBHelper = new CountryDBHelper(MainActivity.this);
        CountryDBService.fillDadaBase(countryDBHelper, this);

        Log.i("kreiranje","main aktiviti");

        startGameBtn=findViewById(R.id.startGameBtn);

        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        settingsBtn=findViewById(R.id.settingsBtn);
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,Preference.class);
                startActivity(i);
            }
        });


    }


    private void showDialog() {
        AlertDialog.Builder dialogBuilder;
        AlertDialog dialog;
        dialogBuilder = new AlertDialog.Builder(MainActivity.this);//ISPRED KOG CONTEXT-A DA PRIKAZE POPUP
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        final View selectQuestionCategoryPopup = inflater.inflate(R.layout.select_question_category_popup, null);





        dialogBuilder.setView(selectQuestionCategoryPopup);
        dialog = dialogBuilder.create();
        dialog.show();

        Button capitalCitiesBtn=selectQuestionCategoryPopup.findViewById(R.id.capitalCitiesBtn);
        capitalCitiesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CapitalCitiesActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        Button countriesFlagsBtn=selectQuestionCategoryPopup.findViewById(R.id.countriesFlagsBtn);
        countriesFlagsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CountryFlagActivity.class);
                startActivity(intent);
                dialog.dismiss();

            }
        });
        Button neighboringCountriesBtn=selectQuestionCategoryPopup.findViewById(R.id.neighboringCountriesBtn);
        neighboringCountriesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NeighboringCountryActivity.class);
                startActivity(intent);
                dialog.dismiss();
            }
        });
        Button sightsBtn=selectQuestionCategoryPopup.findViewById(R.id.sightsBtn);
        sightsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CountrySightsActivity.class);
                startActivity(intent);
                dialog.dismiss();

            }
        });
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        finishAffinity();
    }


}