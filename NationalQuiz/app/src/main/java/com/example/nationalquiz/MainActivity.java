package com.example.nationalquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.nationalquiz.data_base.CountryDBHelper;
import com.example.nationalquiz.data_base.CountryDBService;


public class MainActivity extends AppCompatActivity {

    Button startGameBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        startGameBtn=findViewById(R.id.startGameBtn);
        startGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder;
                AlertDialog dialog;
                dialogBuilder = new AlertDialog.Builder(MainActivity.this);//ISPRED KOG CONTEXT-A DA PRIKAZE POPUP
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                final View selectQuestionCategoryPopup = inflater.inflate(R.layout.select_question_category_popup, null);

                Button capitalCitiesBtn=selectQuestionCategoryPopup.findViewById(R.id.capitalCitiesBtn);
                capitalCitiesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(MainActivity.this,GameActivity.class);
                        intent.putExtra("gameCategory","Capital Cities");
                        startActivity(intent);
                    }
                });
                Button countriesFlagsBtn=selectQuestionCategoryPopup.findViewById(R.id.countriesFlagsBtn);
                countriesFlagsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(MainActivity.this,GameActivity.class);
                        intent.putExtra("gameCategory","Countries Flags");
                        startActivity(intent);
                    }
                });
                Button neighboringCountriesBtn=selectQuestionCategoryPopup.findViewById(R.id.neighboringCountriesBtn);
                neighboringCountriesBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(MainActivity.this,GameActivity.class);
                        intent.putExtra("gameCategory","Neighboring Countries");
                        startActivity(intent);
                    }
                });
                Button sightsBtn=selectQuestionCategoryPopup.findViewById(R.id.sightsBtn);
                sightsBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent(MainActivity.this,GameActivity.class);
                        intent.putExtra("gameCategory","Sights");
                        startActivity(intent);
                    }
                });


                dialogBuilder.setView(selectQuestionCategoryPopup);
                dialog = dialogBuilder.create();
                dialog.show();

            }
        });
    }
}