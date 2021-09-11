package com.example.nationalquiz.games;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nationalquiz.R;
import com.example.nationalquiz.data_base.CountryDBHelper;
import com.example.nationalquiz.models.Country;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CountryFlagActivity extends AppCompatActivity {
    private LinkedList<Country> countriesDataList;
    private String selectedLanguage;
    private int numberOfQuestions;
    private int numberOfCurrentQuestion;
    private int numberOfHints = 3;
    private int currentScore = 0;
    private Button nextQuestionBtn;
    private ImageButton hintBtn;
    private TextView numOfQuestionTv;
    private TextView numberOfHintsTv;
    private TextView currentScoreTv;
    private TextView lettersTv;
    private ImageView imageHolderIv;


    public static Country currentCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_flag);


        CountryDBHelper countryDBHelper = new CountryDBHelper(CountryFlagActivity.this);
        countriesDataList = countryDBHelper.getCountries();


        loadSetting();

        numOfQuestionTv = findViewById(R.id.numOfQuestionTv);
        numberOfHintsTv = findViewById(R.id.hintNumberTv);
        nextQuestionBtn = findViewById(R.id.nextQuestionBtn);
        hintBtn = findViewById(R.id.hintBtn);
        currentScoreTv = findViewById(R.id.currentScoreTv);
        lettersTv=findViewById(R.id.lettersTv);
        imageHolderIv = findViewById(R.id.imageHolderIv);

        numberOfHintsTv.setText(getResources().getString(R.string.hint) + numberOfHints);
        currentScoreTv.setText(getResources().getString(R.string.score) + currentScore);

        setQuestion();

        nextQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setQuestion();
            }
        });

        hintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfHints == 0)
                    Toast.makeText(CountryFlagActivity.this, getResources().getString(R.string.noMoreHints), Toast.LENGTH_SHORT).show();
                else {

                }

            }
        });
    }

    private void setQuestion() {
        nextQuestionBtn.setEnabled(false);
        hintBtn.setEnabled(true);

        numberOfCurrentQuestion++;

        numOfQuestionTv.setText(getResources().getString(R.string.question) + numberOfCurrentQuestion + "/" + numberOfQuestions);
        Random ran = new Random();
        currentCountry = countriesDataList.get(ran.nextInt(20));
        imageHolderIv.setImageResource(getResources().getIdentifier(currentCountry.getFlagImage(), "drawable", getPackageName()));

        List<String> letters = new LinkedList<>();
        String answer = selectedLanguage.equals("en") ? currentCountry.getNameEn() : currentCountry.getNameSr().toLowerCase();
        for (int i = 0; i < answer.length(); i++)
            letters.add(String.valueOf(answer.charAt(i)));

        while (letters.size() != 20) {
            letters.add(String.valueOf( (char)(ran.nextInt(26) + 'a')));
        }
        String lettersToShow="";
        while (letters.size()!=0){
            lettersToShow=lettersToShow+" "+letters.remove(ran.nextInt(letters.size()));
        }

        lettersTv.setText(lettersToShow);

    }

    private void loadSetting() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        selectedLanguage = sp.getString("LANGUAGE", "en");

        String number = sp.getString("NUMBER_OF_QUESTION", "5");
        numberOfQuestions = Integer.parseInt(number);

    }
}