package com.example.nationalquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nationalquiz.data_base.CountryDBHelper;
import com.example.nationalquiz.data_base.CountryDBService;
import com.example.nationalquiz.models.Country;

import java.util.LinkedList;
import java.util.Random;

public class CountrySightsActivity extends AppCompatActivity {

    private LinkedList<Country> countriesDataList;
    private String selectedLanguage;
    private int numberOfQuestions;
    public static Country currentCountry;
    private Random random = new Random();


    private ImageView imageHolderIv;
    private Button nextQuestionBtn;
    private TextView questionTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_sights);


        CountryDBHelper countryDBHelper = new CountryDBHelper(CountrySightsActivity.this);
        CountryDBService.fillDadaBase(countryDBHelper, this);
        countriesDataList = countryDBHelper.getCountries();

        loadSetting();

        imageHolderIv = findViewById(R.id.imageHolderIv);
        nextQuestionBtn = findViewById(R.id.nextQuestionBtn);
        questionTv = findViewById(R.id.questionTv);


        setQuestion();

        nextQuestionBtn.setOnClickListener(x -> setQuestion());
    }


    private void loadSetting() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        selectedLanguage = sp.getString("LANGUAGE", "en");

        String number = sp.getString("NUMBER_OF_QUESTION", "5");
        numberOfQuestions = Integer.parseInt(number);

    }

    private void setQuestion() {

        int i = random.nextInt(20);

        currentCountry = countriesDataList.get(i);
        imageHolderIv.setImageResource(getResources().getIdentifier(currentCountry.getLandmarkImage(), "drawable", getPackageName()));




    }
}