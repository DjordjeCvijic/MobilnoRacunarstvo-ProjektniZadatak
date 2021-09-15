package com.example.nationalquiz.games;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import java.util.List;
import java.util.Random;

public class CountryFlagActivity extends AppCompatActivity {
    private LinkedList<Country> countriesDataList;
    private String selectedLanguage;
    private int numberOfQuestions;
    private int numberOfCurrentQuestion;
    private int numberOfHints = 3;
    private int currentScore = 0;
    private GameResult gameResult=new GameResult();

    private Button nextQuestionBtn;
    private Button endGameBtn;
    private ImageButton hintBtn;
    private ImageButton enterAnswerBtn;
    private TextView numOfQuestionTv;
    private TextView numberOfHintsTv;
    private TextView currentScoreTv;
    private TextView lettersTv;
    private TextView questionTv;
    private EditText answerEt;
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
        endGameBtn = findViewById(R.id.endGameBtn);
        enterAnswerBtn = findViewById(R.id.enterAnswerBtn);
        hintBtn = findViewById(R.id.hintBtn);
        currentScoreTv = findViewById(R.id.currentScoreTv);
        lettersTv = findViewById(R.id.lettersTv);
        questionTv=findViewById(R.id.questionTv);
        imageHolderIv = findViewById(R.id.imageHolderIv);
        answerEt = findViewById(R.id.answerEt);

        numberOfHintsTv.setText(getResources().getString(R.string.hint) + numberOfHints);
        currentScoreTv.setText(getResources().getString(R.string.currentScore) + currentScore);

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

                    String currentText = answerEt.getText().toString();
                    char nextLetter = (selectedLanguage.equals("en") ? currentCountry.getNameEn() : currentCountry.getNameSr().toLowerCase().toLowerCase()).charAt(currentText.length());
                    String newText = currentText.concat(String.valueOf(nextLetter));
                    answerEt.setText(newText);
                    numberOfHints--;
                    numberOfHintsTv.setText(getResources().getString(R.string.hint) + numberOfHints);
                }

            }
        });
        enterAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Answer answer=new Answer(questionTv.getText().toString(),answerEt.getText().toString().toLowerCase(),currentCountry.getFlagImage());
                if (answerEt.getText().toString().toLowerCase().equals(
                        (selectedLanguage.equals("en") ? currentCountry.getNameEn() : currentCountry.getNameSr().toLowerCase()).toLowerCase())) {
                    Toast.makeText(CountryFlagActivity.this, getResources().getString(R.string.correctAnswer), Toast.LENGTH_LONG).show();
                    currentScore++;
                    currentScoreTv.setText(getResources().getString(R.string.currentScore) + currentScore);
                    enterAnswerBtn.setBackgroundColor(getResources().getColor(R.color.green, null));
                    answer.setCorrect(true);
                } else {
                    Toast.makeText(CountryFlagActivity.this, getResources().getString(R.string.incorrectAnswer), Toast.LENGTH_LONG).show();
                    enterAnswerBtn.setBackgroundColor(getResources().getColor(R.color.red, null));
                    answer.setCorrect(false);
                }
                nextQuestionBtn.setEnabled(true);
                hintBtn.setEnabled(false);
                enterAnswerBtn.setEnabled(false);
                closeKeyboard();
                gameResult.addAnswer(answer);
            }
        });

        endGameBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder;
                AlertDialog dialog;
                dialogBuilder = new AlertDialog.Builder(CountryFlagActivity.this);//ISPRED KOG CONTEXT-A DA PRIKAZE POPUP
                LayoutInflater inflater = LayoutInflater.from(CountryFlagActivity.this);
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

                        GameResultService.writeGameResult(gameResult,CountryFlagActivity.this);

                        dialog.dismiss();
                        finish();
                    }
                });
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                        //CountryFlagActivity.super.onBackPressed();
                        dialog.dismiss();
                    }
                });


            }
        });
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void setQuestion() {
        if (numberOfCurrentQuestion != numberOfQuestions) {

            hintBtn.setEnabled(true);
            enterAnswerBtn.setEnabled(true);
            answerEt.setText("");
            numberOfCurrentQuestion++;
            enterAnswerBtn.setBackgroundColor(getResources().getColor(R.color.purple_500, null));
            numOfQuestionTv.setText(getResources().getString(R.string.question) + numberOfCurrentQuestion + "/" + numberOfQuestions);
            Random ran = new Random();
            currentCountry = countriesDataList.get(ran.nextInt(20));
            imageHolderIv.setImageResource(getResources().getIdentifier(currentCountry.getFlagImage(), "drawable", getPackageName()));

            List<String> letters = new LinkedList<>();
            String answer = selectedLanguage.equals("en") ? currentCountry.getNameEn() : currentCountry.getNameSr().toLowerCase();
            for (int i = 0; i < answer.length(); i++)
                letters.add(String.valueOf(answer.charAt(i)));

            while (letters.size() < 20) {
                char tmp = (char) (ran.nextInt(26) + 'a');
                letters.add(String.valueOf(tmp));
            }
            String lettersToShow = "";
            while (letters.size() != 0) {
                lettersToShow = lettersToShow + " " + letters.remove(ran.nextInt(letters.size()));
            }

            lettersTv.setText(lettersToShow);
        } else {
            Toast.makeText(this, getResources().getString(R.string.thisIsLastQuestion), Toast.LENGTH_SHORT).show();
        }


    }

    private void loadSetting() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        selectedLanguage = sp.getString("LANGUAGE", "en");

        String number = sp.getString("NUMBER_OF_QUESTION", "5");
        numberOfQuestions = Integer.parseInt(number);

    }

        @Override
        public void onBackPressed() {
            endGameBtn.performClick();
        }




}