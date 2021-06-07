package com.example.radsabazompoapi;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity {

    int imageToShow=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CountryDbHelper dbHelper = new CountryDbHelper(MainActivity.this);


        CountryDBService.fillDadaBase(dbHelper,this);

        Log.i("Baza","broj "+dbHelper.numberOfRows());
        LinkedList<Country>countries= dbHelper.getCountries();

        for(Country c: countries){
            Log.i("Baza",c.toString());
        }

        ImageView imageView=findViewById(R.id.imageHolder);
        Button button=findViewById(R.id.switchImageBtn);

        //int imageToShow=0;
        String imgname=countries.get(imageToShow).getFlagImage();
        imageToShow++;
        //String uri ="br_zastava";  // image file
        int resID = getResources().getIdentifier(imgname , "drawable", getPackageName());
        Drawable drawable = getResources().getDrawable(resID); //<----line with error
        imageView.setImageDrawable(drawable);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imgname=countries.get(imageToShow).getFlagImage();
                Log.i("slika: ",imgname);
                int resID = getResources().getIdentifier(imgname , "drawable", getPackageName());
                Drawable drawable = getDrawable(resID); //<----line with error

                imageView.setImageDrawable(drawable);

                imageToShow=(imageToShow+1)%19;


            }
        });




    }
}