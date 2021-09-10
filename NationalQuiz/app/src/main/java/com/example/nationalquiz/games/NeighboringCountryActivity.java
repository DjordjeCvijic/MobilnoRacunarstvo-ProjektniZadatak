package com.example.nationalquiz.games;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nationalquiz.R;
import com.example.nationalquiz.data_base.CountryDBHelper;
import com.example.nationalquiz.data_base.CountryDBService;
import com.example.nationalquiz.models.Country;

import java.util.LinkedList;
import java.util.Random;

public class NeighboringCountryActivity extends AppCompatActivity {

    private LinkedList<Country> countriesDataList;
    private String selectedLanguage;
    private int numberOfQuestions;
    private int numberOfCurrentQuestion;
    private int numberOfHints = 3;
    private int currentScore = 0;
    private TextView questionTv;
    private Button answer1Btn;
    private Button answer2Btn;
    private Button answer3Btn;
    private Button answer4Btn;
    private Button nextQuestionBtn;
    private ImageButton hintBtn;
    private TextView numOfQuestionTv;
    private TextView numberOfHintsTv;
    private TextView currentScoreTv;

    public static Country currentCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_neighboring_country);

        CountryDBHelper countryDBHelper = new CountryDBHelper(NeighboringCountryActivity.this);
        //CountryDBService.fillDadaBase(countryDBHelper, this);
        countriesDataList = countryDBHelper.getCountries();


        loadSetting();

        numOfQuestionTv=findViewById(R.id.numOfQuestionTv);
        numberOfHintsTv=findViewById(R.id.hintNumberTv);
        questionTv = findViewById(R.id.questionTv);
        answer1Btn = findViewById(R.id.answer1Btn);
        answer2Btn = findViewById(R.id.answer2Btn);
        answer3Btn = findViewById(R.id.answer3Btn);
        answer4Btn = findViewById(R.id.answer4Btn);
        nextQuestionBtn=findViewById(R.id.nextQuestionBtn);
        hintBtn=findViewById(R.id.hintBtn);
        currentScoreTv=findViewById(R.id.currentScoreTv);

        numberOfHintsTv.setText(getResources().getString(R.string.hint)+numberOfHints);
        currentScoreTv.setText(getResources().getString(R.string.score)+currentScore);

        setQuestion();

        answer1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(v);
            }
        });
        answer2Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(v);
            }
        });
        answer3Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(v);
            }
        });
        answer4Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer(v);
            }
        });
        nextQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setQuestion();
            }
        });

        hintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(numberOfHints==0 )
                    Toast.makeText(NeighboringCountryActivity.this, getResources().getString(R.string.noMoreHints), Toast.LENGTH_SHORT).show();
                else{
                    if( answer1Btn.isEnabled() && !answer1Btn.getText().toString().equals(selectedLanguage.equals("en") ? currentCountry.getNeighboringCountryEn() : currentCountry.getNeighboringCountrySr())){
                        answer1Btn.setEnabled(false);
                        numberOfHints--;
                        numberOfHintsTv.setText(getResources().getString(R.string.hint)+numberOfHints);
                    }else if(answer2Btn.isEnabled() && !answer2Btn.getText().toString().equals(selectedLanguage.equals("en") ? currentCountry.getNeighboringCountryEn() : currentCountry.getNeighboringCountrySr())){
                        answer2Btn.setEnabled(false);
                        numberOfHints--;
                        numberOfHintsTv.setText(getResources().getString(R.string.hint)+numberOfHints);
                    }else if(answer3Btn.isEnabled() && !answer3Btn.getText().toString().equals(selectedLanguage.equals("en") ? currentCountry.getNeighboringCountryEn() : currentCountry.getNeighboringCountrySr())){
                        answer3Btn.setEnabled(false);
                        numberOfHints--;
                        numberOfHintsTv.setText(getResources().getString(R.string.hint)+numberOfHints);
                    }else if(answer4Btn.isEnabled() && !answer4Btn.getText().toString().equals(selectedLanguage.equals("en") ? currentCountry.getNeighboringCountryEn() : currentCountry.getNeighboringCountrySr())){
                        answer4Btn.setEnabled(false);
                        numberOfHints--;
                        numberOfHintsTv.setText(getResources().getString(R.string.hint)+numberOfHints);
                    }
                }

            }
        });
    }

    private void checkAnswer(View v) {
        Button btn = (Button) findViewById(v.getId());
        String selectedAnswer = btn.getText().toString();


        AlertDialog.Builder dialogBuilder;
        AlertDialog dialog;
        dialogBuilder = new AlertDialog.Builder(NeighboringCountryActivity.this);//ISPRED KOG CONTEXT-A DA PRIKAZE POPUP
        LayoutInflater inflater = LayoutInflater.from(NeighboringCountryActivity.this);
        final View AnswerConfirmationPopup = inflater.inflate(R.layout.answer_confirmation_popup, null);

        Button yesBtn = AnswerConfirmationPopup.findViewById(R.id.yesBtn);
        Button noBtn = AnswerConfirmationPopup.findViewById(R.id.noBtn);


        dialogBuilder.setView(AnswerConfirmationPopup);
        dialog = dialogBuilder.create();
        dialog.show();


        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedAnswer.equals(selectedLanguage.equals("en") ? currentCountry.getNeighboringCountryEn() : currentCountry.getNeighboringCountrySr())) {
                    Toast.makeText(NeighboringCountryActivity.this, getResources().getString(R.string.correctAnswer), Toast.LENGTH_LONG).show();
                    btn.setBackgroundColor(getResources().getColor(R.color.green, null));
                    nextQuestionBtn.setEnabled(true);
                    hintBtn.setEnabled(false);
                    currentScore++;
                    currentScoreTv.setText(getResources().getString(R.string.score)+currentScore);
                } else {
                    Toast.makeText(NeighboringCountryActivity.this, getResources().getString(R.string.incorrectAnswer), Toast.LENGTH_LONG).show();
                    btn.setBackgroundColor(getResources().getColor(R.color.red, null));
                    hintBtn.setEnabled(false);
                    if (answer1Btn.getText().toString().equals(selectedLanguage.equals("en") ? currentCountry.getNeighboringCountryEn() : currentCountry.getNeighboringCountrySr())){
                        answer1Btn.setBackgroundColor(getResources().getColor(R.color.green, null));
                    }else if (answer2Btn.getText().toString().equals(selectedLanguage.equals("en") ? currentCountry.getNeighboringCountryEn() : currentCountry.getNeighboringCountrySr())){
                        answer2Btn.setBackgroundColor(getResources().getColor(R.color.green, null));
                    }else if (answer3Btn.getText().toString().equals(selectedLanguage.equals("en") ? currentCountry.getNeighboringCountryEn() : currentCountry.getNeighboringCountrySr())){
                        answer3Btn.setBackgroundColor(getResources().getColor(R.color.green, null));
                    }else
                        answer4Btn.setBackgroundColor(getResources().getColor(R.color.green, null));
                }
                dialog.dismiss();
            }
        });

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void setQuestion() {
        nextQuestionBtn.setEnabled(false);
        hintBtn.setEnabled(true);

        numberOfCurrentQuestion++;
        numOfQuestionTv.setText(getResources().getString(R.string.question)+numberOfCurrentQuestion+"/"+numberOfQuestions);

        LinkedList<Country> data = new LinkedList<>();
        Random ran = new Random();
        int i;
        //izvlacenje cetiri drzave koje ce biti u opticaju za pitanje i odgovor
        while (data.size() != 4) {
            i = ran.nextInt(20);
            if (!data.contains(countriesDataList.get(i)))
                data.add(countriesDataList.get(i));
        }
        currentCountry = data.remove(0);//prva zemlja ce biti za pitanje
        LinkedList<String> answers = new LinkedList<>();
        answers.add(selectedLanguage.equals("en") ? currentCountry.getNeighboringCountryEn() : currentCountry.getNeighboringCountrySr());
        for (Country c : data) {
            answers.add(selectedLanguage.equals("en") ? c.getNameEn() : c.getNameSr());
        }
        String country = null;

        if (selectedLanguage.equals("en")) {
            country = currentCountry.getNameEn();

        } else {
            country = currentCountry.getNameSr();
        }
        questionTv.setText(getResources().getString(R.string.neighboringCountryQuestion) + " " + country + " ?");
        i = ran.nextInt(4);
        answer1Btn.setText(answers.get(i));
        answers.remove(i);
        i = ran.nextInt(3);
        answer1Btn.setBackgroundColor(getResources().getColor(R.color.purple_500, null));
        answer1Btn.setEnabled(true);
        answer2Btn.setText(answers.get(i));
        answers.remove(i);
        i = ran.nextInt(2);
        answer2Btn.setBackgroundColor(getResources().getColor(R.color.purple_500, null));
        answer2Btn.setEnabled(true);
        answer3Btn.setText(answers.get(i));
        answers.remove(i);
        answer3Btn.setBackgroundColor(getResources().getColor(R.color.purple_500, null));
        answer3Btn.setEnabled(true);
        answer4Btn.setText(answers.get(0));
        answer4Btn.setBackgroundColor(getResources().getColor(R.color.purple_500, null));
        answer4Btn.setEnabled(true);
    }


    private void loadSetting() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        selectedLanguage = sp.getString("LANGUAGE", "en");

        String number = sp.getString("NUMBER_OF_QUESTION", "5");
        numberOfQuestions = Integer.parseInt(number);

    }


}