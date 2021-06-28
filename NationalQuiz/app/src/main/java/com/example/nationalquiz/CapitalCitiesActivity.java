package com.example.nationalquiz;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nationalquiz.data_base.CountryDBHelper;
import com.example.nationalquiz.data_base.CountryDBService;
import com.example.nationalquiz.model_view.CapitalCitiesActivityModelView;
import com.example.nationalquiz.models.Country;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

public class CapitalCitiesActivity extends AppCompatActivity {

    private LinkedList<Country> countriesDataList;
    private TextView questionTv;
    private Button answer1Btn;
    private Button answer2Btn;
    private Button answer3Btn;
    private Button answer4Btn;
    Country currentQuestion;

    int imageToShow=0;
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
        CountryDBService.fillDadaBase(countryDBHelper,this);
        countriesDataList= countryDBHelper.getCountries();
        Log.i("Baza","broj redova"+countryDBHelper.numberOfRows());

        questionTv=findViewById(R.id.questionTv);
        answer1Btn=findViewById(R.id.answer1Btn);
        answer2Btn=findViewById(R.id.answer2Btn);
        answer3Btn=findViewById(R.id.answer3Btn);
        answer4Btn=findViewById(R.id.answer4Btn);

        showQuestion();

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

    }

    private void checkAnswer(View v) {
        Button btn = (Button) findViewById(v.getId());
        String selectedAnswer= btn.getText().toString();
        String correctAnswer= Locale.getDefault().getDisplayLanguage().equals("sr")?currentQuestion.getCapitalCitySr():currentQuestion.getCapitalCityEn();

        AlertDialog.Builder dialogBuilder;
        AlertDialog dialog;
        dialogBuilder = new AlertDialog.Builder(CapitalCitiesActivity.this);//ISPRED KOG CONTEXT-A DA PRIKAZE POPUP
        LayoutInflater inflater = LayoutInflater.from(CapitalCitiesActivity.this);
        final View AnswerConfirmationPopup = inflater.inflate(R.layout.answer_confirmation_popup, null);

        Button yesBtn=AnswerConfirmationPopup.findViewById(R.id.yesBtn);
        Button noBtn=AnswerConfirmationPopup.findViewById(R.id.noBtn);



        dialogBuilder.setView(AnswerConfirmationPopup);
        dialog = dialogBuilder.create();
        dialog.show();


        yesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectedAnswer.equals(currentQuestion.getCapitalCitySr()))
                    Toast.makeText(CapitalCitiesActivity.this, "Tacan odgovor", Toast.LENGTH_LONG).show();
                else

                    Toast.makeText(CapitalCitiesActivity.this, "pogresan odgovor", Toast.LENGTH_LONG).show();
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

    private void showQuestion() {
        LinkedList<Country>data=new LinkedList<>();
        Random ran=new Random();
        int i;
        while(data.size()!=4){
             i=ran.nextInt(20);
            if(!data.contains(countriesDataList.get(i)))
                data.add(countriesDataList.get(i));
        }
         currentQuestion=data.get(0);
        LinkedList<String> answers=new LinkedList<>();
        for(Country c : data){
            answers.add(c.getCapitalCitySr());
        }
        questionTv.setText("Glavni grad drzave "+currentQuestion.getCapitalCitySr()+" je?");
        i=ran.nextInt(4);
        answer1Btn.setText(answers.get(i));
        answers.remove(i);
        i=ran.nextInt(3);
        answer2Btn.setText(answers.get(i));
        answers.remove(i);
        i=ran.nextInt(4);
        answer3Btn.setText(answers.get(i));
        answers.remove(i);

        answer4Btn.setText(answers.get(0));

    }

    private void prikaziSliku(){
//        ImageView imageView=findViewById(R.id.imageHolder);
//        Button button=findViewById(R.id.switchImageBtn);
//
//        String imgname=countriesDataList.get(imageToShow).getFlagImage();
//        imageToShow++;
//        int resID = getResources().getIdentifier(imgname , "drawable", getPackageName());
//        Drawable drawable = getResources().getDrawable(resID); //<----line with error
//        imageView.setImageDrawable(drawable);
//
//        Log.i("Baza","broj listi"+countriesDataList.size());
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String imgname=countriesDataList.get(imageToShow).getFlagImage();
//                Log.i("slika: ",imgname);
//                int resID = getResources().getIdentifier(imgname , "drawable", getPackageName());
//                Drawable drawable = getDrawable(resID);
//
//                imageView.setImageDrawable(drawable);
//
//                imageToShow=(imageToShow+1)%20;
//
//                viewModel.doAction("novo");
//            }
//        });
//        <ImageView
//        android:id="@+id/imageHolder"
//        android:layout_width="238dp"
//        android:layout_height="207dp"
//        app:layout_constraintBottom_toBottomOf="parent"
//        app:layout_constraintEnd_toEndOf="parent"
//        app:layout_constraintHorizontal_bias="0.497"
//        app:layout_constraintStart_toStartOf="parent"
//        app:layout_constraintTop_toTopOf="parent"
//        app:layout_constraintVertical_bias="0.112" />
//
//    <Button
//        android:id="@+id/switchImageBtn"
//        android:text="Sledeca slika"
//        android:layout_width="144dp"
//        android:layout_height="63dp"
//        app:layout_constraintBottom_toBottomOf="parent"
//        app:layout_constraintEnd_toEndOf="parent"
//        app:layout_constraintStart_toStartOf="parent"
//        app:layout_constraintTop_toBottomOf="@+id/imageHolder" />
    }
}