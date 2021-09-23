package com.MRProject.nationalquiz.games;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.MRProject.nationalquiz.MapsActivity;
import com.MRProject.nationalquiz.models.Articles;
import com.MRProject.nationalquiz.news.NewsActivity;
import com.MRProject.nationalquiz.R;
import com.MRProject.nationalquiz.data_base.CountryDBHelper;
import com.MRProject.nationalquiz.model_view.CapitalCitiesActivityModelView;
import com.MRProject.nationalquiz.models.Answer;
import com.MRProject.nationalquiz.models.Country;
import com.MRProject.nationalquiz.models.GameResult;
import com.MRProject.nationalquiz.services.GameResultService;
import com.MRProject.nationalquiz.services.NewsService;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class CapitalCitiesActivity extends AppCompatActivity {

    private LinkedList<Country> countriesDataList;
    private String selectedLanguage;
    private int numberOfQuestions;
    private int numberOfCurrentQuestion;
    private int numberOfHints = 3;
    private int currentScore = 0;
    boolean newsCaching;
    private GameResult gameResult = new GameResult();
    private boolean answerIsCorrect = false;
    private TextView questionTv;
    private Button answer1Btn;
    private Button answer2Btn;
    private Button answer3Btn;
    private Button answer4Btn;
    private Button nextQuestionBtn;
    private Button endGameBtn;
    private ImageButton newsBtn;
    private ImageButton mapsBtn;
    private ImageButton hintBtn;
    private TextView numOfQuestionTv;
    private TextView numberOfHintsTv;
    private TextView currentScoreTv;

    public static Country currentCountry;

    int imageToShow = 0;
    TextView tv;
    CapitalCitiesActivityModelView viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capital_cities);

//         tv=findViewById(R.id.tv);
//         viewModel = new ViewModelProvider(this).get(CapitalCitiesActivityModelView.class);
//        viewModel.text.observe(this, new Observer<String>() {
//                    @Override
//                    public void onChanged(String s) {
//                    tv.setText(s);
//                    }
//                });

        CountryDBHelper countryDBHelper = new CountryDBHelper(CapitalCitiesActivity.this);
        // CountryDBService.fillDadaBase(countryDBHelper, this);
        countriesDataList = countryDBHelper.getCountries();

        loadSetting();

        numOfQuestionTv = findViewById(R.id.numOfQuestionTv);
        numberOfHintsTv = findViewById(R.id.hintNumberTv);
        questionTv = findViewById(R.id.questionTv);
        answer1Btn = findViewById(R.id.answer1Btn);
        answer2Btn = findViewById(R.id.answer2Btn);
        answer3Btn = findViewById(R.id.answer3Btn);
        answer4Btn = findViewById(R.id.answer4Btn);
        newsBtn = findViewById(R.id.newsBtn);
        endGameBtn = findViewById(R.id.endGameBtn);
        mapsBtn = findViewById(R.id.mapsBtn);
        nextQuestionBtn = findViewById(R.id.nextQuestionBtn);
        hintBtn = findViewById(R.id.hintBtn);
        currentScoreTv = findViewById(R.id.currentScoreTv);

        numberOfHintsTv.setText(getResources().getString(R.string.hint) + numberOfHints);
        currentScoreTv.setText(getResources().getString(R.string.currentScore) + currentScore);

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


        newsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerIsCorrect) {
                    Log.d("novosti", NewsService.readNewsCache(CapitalCitiesActivity.this));
                    List<Articles> a = NewsService.getNewsCacheFromFileForCountry(CapitalCitiesActivity.this, "*");
                    Log.d("novosti", "broj " + a.size());
                    Intent intent = new Intent(CapitalCitiesActivity.this, NewsActivity.class);
                    intent.putExtra("country", currentCountry.getMark());
                    intent.putExtra("internet", internetIsConnected());
                    intent.putExtra("caching", newsCaching);
                    startActivity(intent);
                } else
                    Toast.makeText(CapitalCitiesActivity.this, getResources().getString(R.string.youHaveToGiveAnAnswer), Toast.LENGTH_SHORT).show();


            }
        });

        mapsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (answerIsCorrect && internetIsConnected()) {
                    Intent intent = new Intent(CapitalCitiesActivity.this, MapsActivity.class);
                    startActivity(intent);
                } else
                    Toast.makeText(CapitalCitiesActivity.this, getResources().getString(R.string.youHaveToGiveAnAnswer), Toast.LENGTH_SHORT).show();

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

                if (answerIsCorrect) {
                    Toast.makeText(CapitalCitiesActivity.this, getResources().getString(R.string.youHaveGivenAnAnswer), Toast.LENGTH_SHORT).show();
                } else if (numberOfHints == 0) {
                    Toast.makeText(CapitalCitiesActivity.this, getResources().getString(R.string.noMoreHints), Toast.LENGTH_SHORT).show();
                } else {
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
                dialogBuilder = new AlertDialog.Builder(CapitalCitiesActivity.this);//ISPRED KOG CONTEXT-A DA PRIKAZE POPUP
                LayoutInflater inflater = LayoutInflater.from(CapitalCitiesActivity.this);
                final View saveResultPopup = inflater.inflate(R.layout.save_result_popup, null);

                dialogBuilder.setView(saveResultPopup);
                dialog = dialogBuilder.create();
                dialog.show();

                TextView finalResultTv;
                Button saveBtn;
                Button cancelBtn;
                EditText playerNameEt;

                playerNameEt = saveResultPopup.findViewById(R.id.enteredNameEt);
                finalResultTv = saveResultPopup.findViewById(R.id.finalResultTv);
                saveBtn = saveResultPopup.findViewById(R.id.saveBtn);
                cancelBtn = saveResultPopup.findViewById(R.id.cancelBtn);

                finalResultTv.setText(getResources().getString(R.string.finalScore) + currentScore);
                saveBtn.setOnClickListener(new View.OnClickListener() {

                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        LocalDateTime dateTime = LocalDateTime.now();
                        String playerName = playerNameEt.getText().toString();
                        String score = currentScore + "/" + numberOfQuestions;
                        gameResult.setDate(dateTime.toString());
                        gameResult.setPlayerName(playerName);
                        gameResult.setScore(score);

                        GameResultService.writeGameResult(gameResult, CapitalCitiesActivity.this);
                        GameResultService.readGameResult(CapitalCitiesActivity.this);
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
        if (answerIsCorrect) {
            Toast.makeText(this, getResources().getString(R.string.youHaveGivenAnAnswer), Toast.LENGTH_SHORT).show();
        } else {
            Button btn = (Button) findViewById(v.getId());
            String selectedAnswer = btn.getText().toString();


            AlertDialog.Builder dialogBuilder;
            AlertDialog dialog;
            dialogBuilder = new AlertDialog.Builder(CapitalCitiesActivity.this);//ISPRED KOG CONTEXT-A DA PRIKAZE POPUP
            LayoutInflater inflater = LayoutInflater.from(CapitalCitiesActivity.this);
            final View AnswerConfirmationPopup = inflater.inflate(R.layout.answer_confirmation_popup, null);

            Button yesBtn = AnswerConfirmationPopup.findViewById(R.id.yesBtn);
            Button noBtn = AnswerConfirmationPopup.findViewById(R.id.noBtn);


            dialogBuilder.setView(AnswerConfirmationPopup);
            dialog = dialogBuilder.create();
            dialog.show();


            yesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Answer answer = new Answer(questionTv.getText().toString(), selectedAnswer, null);
                    if (selectedAnswer.equals(selectedLanguage.equals("en") ? currentCountry.getCapitalCityEn() : currentCountry.getCapitalCitySr())) {
                        Toast.makeText(CapitalCitiesActivity.this, getResources().getString(R.string.correctAnswer), Toast.LENGTH_SHORT).show();
                        btn.setBackgroundColor(getResources().getColor(R.color.green, null));
                        answerIsCorrect = true;
                        currentScore++;
                        currentScoreTv.setText(getResources().getString(R.string.currentScore) + currentScore);
                    } else {
                        Toast.makeText(CapitalCitiesActivity.this, getResources().getString(R.string.incorrectAnswer), Toast.LENGTH_SHORT).show();
                        btn.setBackgroundColor(getResources().getColor(R.color.red, null));
                        hintBtn.setEnabled(false);
                        if (answer1Btn.getText().toString().equals(selectedLanguage.equals("en") ? currentCountry.getCapitalCityEn() : currentCountry.getCapitalCitySr())) {
                            answer1Btn.setBackgroundColor(getResources().getColor(R.color.green, null));
                        } else if (answer2Btn.getText().toString().equals(selectedLanguage.equals("en") ? currentCountry.getCapitalCityEn() : currentCountry.getCapitalCitySr())) {
                            answer2Btn.setBackgroundColor(getResources().getColor(R.color.green, null));
                        } else if (answer3Btn.getText().toString().equals(selectedLanguage.equals("en") ? currentCountry.getCapitalCityEn() : currentCountry.getCapitalCitySr())) {
                            answer3Btn.setBackgroundColor(getResources().getColor(R.color.green, null));
                        } else
                            answer4Btn.setBackgroundColor(getResources().getColor(R.color.green, null));
                    }
                    answer.setCorrect(true);
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
    }

    private void setQuestion() {
        if (numberOfCurrentQuestion != numberOfQuestions) {
            answerIsCorrect = false;

            numberOfCurrentQuestion++;
            numOfQuestionTv.setText(getResources().getString(R.string.question) + numberOfCurrentQuestion + "/" + numberOfQuestions);

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
            LinkedList<String> answers = new LinkedList<>();
            for (Country c : data) {

                answers.add(selectedLanguage.equals("en") ? c.getCapitalCityEn() : c.getCapitalCitySr());
            }
            String country = null;

            if (selectedLanguage.equals("en")) {
                country = currentCountry.getNameEn();

            } else {
                country = currentCountry.getNameSr();
            }
            questionTv.setText(getResources().getString(R.string.capitalCitiesQuestion) + " " + country + " ?");
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
        } else {
            Toast.makeText(this, getResources().getString(R.string.thisIsLastQuestion), Toast.LENGTH_SHORT).show();
        }

    }

    private void loadSetting() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        selectedLanguage = sp.getString("LANGUAGE", "en");

        String number = sp.getString("NUMBER_OF_QUESTION", "5");
        numberOfQuestions = Integer.parseInt(number);


        newsCaching = sp.getBoolean("CACHING", false);
    }

    @Override
    public void onBackPressed() {
        endGameBtn.performClick();
    }

    private boolean internetIsConnected() {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                connected = true;
            }
//            else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
//                connected = true;
//            }
        }
        return connected;

    }


}