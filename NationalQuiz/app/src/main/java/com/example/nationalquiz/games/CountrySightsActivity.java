package com.example.nationalquiz.games;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nationalquiz.R;
import com.example.nationalquiz.data_base.CountryDBHelper;
import com.example.nationalquiz.models.Answer;
import com.example.nationalquiz.models.Country;
import com.example.nationalquiz.models.GameResult;
import com.example.nationalquiz.services.GameResultService;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.Random;

public class CountrySightsActivity extends AppCompatActivity {

    private LinkedList<Country> countriesDataList;
    private String selectedLanguage;
    private int numberOfQuestions;
    public static Country currentCountry;
    private Random random = new Random();
    private int numberOfCurrentQuestion = 0;
    private int numberOfHints = 3;
    private int currentScore = 0;
    private GameResult gameResult=new GameResult();


    private ImageView imageHolderIv;
    private Button nextQuestionBtn;
    private Button endGameBtn;
    private Button answer1Btn;
    private Button answer2Btn;
    private Button answer3Btn;
    private Button answer4Btn;
    private ImageButton hintBtn;
    private TextView questionTv;
    private TextView numOfQuestionTv;
    private TextView currentScoreTv;
    private TextView numberOfHintsTv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_sights);


        CountryDBHelper countryDBHelper = new CountryDBHelper(CountrySightsActivity.this);
        //CountryDBService.fillDadaBase(countryDBHelper, this);
        countriesDataList = countryDBHelper.getCountries();

        loadSetting();

        imageHolderIv = findViewById(R.id.imageHolderIv);
        questionTv = findViewById(R.id.questionTv);
        numOfQuestionTv = findViewById(R.id.numOfQuestionTv);
        numberOfHintsTv = findViewById(R.id.hintNumberTv);
        currentScoreTv = findViewById(R.id.currentScoreTv);
        nextQuestionBtn = findViewById(R.id.nextQuestionBtn);
        endGameBtn = findViewById(R.id.endGameBtn);
        answer1Btn = findViewById(R.id.answer1Btn);
        answer2Btn = findViewById(R.id.answer2Btn);
        answer3Btn = findViewById(R.id.answer3Btn);
        answer4Btn = findViewById(R.id.answer4Btn);
        hintBtn = findViewById(R.id.hintBtn);


        questionTv.setText(getResources().getString(R.string.countrySightsQuestion));

        numberOfHintsTv.setText(getResources().getString(R.string.hint) + numberOfHints);
        currentScoreTv.setText(getResources().getString(R.string.currentScore) + currentScore);

        setQuestion();

        nextQuestionBtn.setOnClickListener(x -> setQuestion());

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
        hintBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOfHints == 0)
                    Toast.makeText(CountrySightsActivity.this, getResources().getString(R.string.noMoreHints), Toast.LENGTH_SHORT).show();
                else {
                    if (answer1Btn.isEnabled() && !answer1Btn.getText().toString().equals(selectedLanguage.equals("en") ? currentCountry.getCapitalCityEn() : currentCountry.getCapitalCitySr())) {
                        answer1Btn.setEnabled(false);
                        numberOfHints--;
                        numberOfHintsTv.setText(getResources().getString(R.string.hint) + numberOfHints);
                    } else if (answer2Btn.isEnabled() && !answer2Btn.getText().toString().equals(selectedLanguage.equals("en") ? currentCountry.getCapitalCityEn() : currentCountry.getCapitalCitySr())) {
                        answer2Btn.setEnabled(false);
                        numberOfHints--;
                        numberOfHintsTv.setText(getResources().getString(R.string.hint) + numberOfHints);
                    } else if (answer3Btn.isEnabled() && !answer3Btn.getText().toString().equals(selectedLanguage.equals("en") ? currentCountry.getCapitalCityEn() : currentCountry.getCapitalCitySr())) {
                        answer3Btn.setEnabled(false);
                        numberOfHints--;
                        numberOfHintsTv.setText(getResources().getString(R.string.hint) + numberOfHints);
                    } else if (answer4Btn.isEnabled() && !answer4Btn.getText().toString().equals(selectedLanguage.equals("en") ? currentCountry.getCapitalCityEn() : currentCountry.getCapitalCitySr())) {
                        answer4Btn.setEnabled(false);
                        numberOfHints--;
                        numberOfHintsTv.setText(getResources().getString(R.string.hint) + numberOfHints);
                    }
                }

            }
        });
        endGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder;
                AlertDialog dialog;
                dialogBuilder = new AlertDialog.Builder(CountrySightsActivity.this);//ISPRED KOG CONTEXT-A DA PRIKAZE POPUP
                LayoutInflater inflater = LayoutInflater.from(CountrySightsActivity.this);
                final View saveResultPopup = inflater.inflate(R.layout.save_result_popup, null);

                dialogBuilder.setView(saveResultPopup);
                dialog = dialogBuilder.create();
                dialog.show();

                TextView finalResultTv;
                Button saveBtn;
                Button cancelBtn;
                EditText playerNameEt;

                playerNameEt=saveResultPopup.findViewById(R.id.enteredNameEt);
                finalResultTv = saveResultPopup.findViewById(R.id.finalResultTv);
                saveBtn = saveResultPopup.findViewById(R.id.saveBtn);
                cancelBtn = saveResultPopup.findViewById(R.id.cancelBtn);

                finalResultTv.setText(getResources().getString(R.string.finalScore) + currentScore);
                saveBtn.setOnClickListener(new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        LocalDateTime dateTime=LocalDateTime.now();
                        String playerName=playerNameEt.getText().toString();
                        String score=currentScore+"/"+numberOfQuestions;
                        gameResult.setDate(dateTime.toString());
                        gameResult.setPlayerName(playerName);
                        gameResult.setScore(score);

                        GameResultService.writeGameResult(gameResult,CountrySightsActivity.this);

                        dialog.dismiss();
                        finish();
                    }
                });
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        dialog.dismiss();
                    }
                });


            }
        });
    }

    private void checkAnswer(View v) {
        Button btn = (Button) findViewById(v.getId());
        String selectedAnswer = btn.getText().toString();


        AlertDialog.Builder dialogBuilder;
        AlertDialog dialog;
        dialogBuilder = new AlertDialog.Builder(CountrySightsActivity.this);//ISPRED KOG CONTEXT-A DA PRIKAZE POPUP
        LayoutInflater inflater = LayoutInflater.from(CountrySightsActivity.this);
        final View AnswerConfirmationPopup = inflater.inflate(R.layout.answer_confirmation_popup, null);

        Button yesBtn = AnswerConfirmationPopup.findViewById(R.id.yesBtn);
        Button noBtn = AnswerConfirmationPopup.findViewById(R.id.noBtn);


        dialogBuilder.setView(AnswerConfirmationPopup);
        dialog = dialogBuilder.create();
        dialog.show();


        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Answer answer=new Answer(questionTv.getText().toString(),selectedAnswer,currentCountry.getLandmarkImage());

                if (selectedAnswer.equals(selectedLanguage.equals("en") ? currentCountry.getNameEn() : currentCountry.getNameSr())) {
                    Toast.makeText(CountrySightsActivity.this, getResources().getString(R.string.correctAnswer), Toast.LENGTH_LONG).show();
                    btn.setBackgroundColor(getResources().getColor(R.color.green, null));



                    currentScore++;
                    currentScoreTv.setText(getResources().getString(R.string.currentScore) + currentScore);
                } else {
                    Toast.makeText(CountrySightsActivity.this, getResources().getString(R.string.incorrectAnswer), Toast.LENGTH_LONG).show();
                    btn.setBackgroundColor(getResources().getColor(R.color.red, null));

                    if (answer1Btn.getText().toString().equals(selectedLanguage.equals("en") ? currentCountry.getNameEn() : currentCountry.getNameSr())) {
                        answer1Btn.setBackgroundColor(getResources().getColor(R.color.green, null));
                    } else if (answer2Btn.getText().toString().equals(selectedLanguage.equals("en") ? currentCountry.getNameEn() : currentCountry.getNameSr())) {
                        answer2Btn.setBackgroundColor(getResources().getColor(R.color.green, null));
                    } else if (answer3Btn.getText().toString().equals(selectedLanguage.equals("en") ? currentCountry.getNameEn() : currentCountry.getNameSr())) {
                        answer3Btn.setBackgroundColor(getResources().getColor(R.color.green, null));
                    } else
                        answer4Btn.setBackgroundColor(getResources().getColor(R.color.green, null));
                }
                nextQuestionBtn.setEnabled(true);
                hintBtn.setEnabled(false);
                gameResult.addAnswer(answer);
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


    private void loadSetting() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        selectedLanguage = sp.getString("LANGUAGE", "en");

        String number = sp.getString("NUMBER_OF_QUESTION", "5");
        numberOfQuestions = Integer.parseInt(number);

    }

    private void setQuestion() {
        if(numberOfCurrentQuestion!=numberOfQuestions){
            numberOfCurrentQuestion++;
            numOfQuestionTv.setText(getResources().getString(R.string.question) + numberOfCurrentQuestion + "/" + numberOfQuestions);


            hintBtn.setEnabled(true);

            LinkedList<Country> data = new LinkedList<>();
            Random ran = new Random();
            int i;
            //izvlacenje cetiri drzave koje ce biti u opticaju za pitanje i odgovor
            while (data.size() != 4) {
                i = ran.nextInt(20);
                if (!data.contains(countriesDataList.get(i)))
                    data.add(countriesDataList.get(i));
            }
            currentCountry = data.get(0);//prva zemlja ce biti za pitanje
            imageHolderIv.setImageResource(getResources().getIdentifier(currentCountry.getLandmarkImage(), "drawable", getPackageName()));

            LinkedList<String> answers = new LinkedList<>();
            for (Country c : data) {
                answers.add(selectedLanguage.equals("en") ? c.getNameEn() : c.getNameSr());
            }
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
        }else{
            Toast.makeText(this, getResources().getString(R.string.thisIsLastQuestion), Toast.LENGTH_SHORT).show();
        }

    }
}