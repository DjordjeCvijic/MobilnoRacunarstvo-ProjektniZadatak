package com.example.nationalquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    Button startGameBtn;
    Button settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        Button capitalCitiesBtn=selectQuestionCategoryPopup.findViewById(R.id.capitalCitiesBtn);



        dialogBuilder.setView(selectQuestionCategoryPopup);
        dialog = dialogBuilder.create();
        dialog.show();

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

            }
        });
        Button neighboringCountriesBtn=selectQuestionCategoryPopup.findViewById(R.id.neighboringCountriesBtn);
        neighboringCountriesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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