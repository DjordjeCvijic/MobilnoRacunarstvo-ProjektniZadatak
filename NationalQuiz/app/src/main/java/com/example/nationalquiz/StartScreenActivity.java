package com.example.nationalquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.Locale;
import android.content.res.Configuration;

public class StartScreenActivity extends AppCompatActivity {

    private RadioGroup group;
    private Button btnLanguageSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);

        group = findViewById(R.id.group);
        group.check(R.id.srb);
        btnLanguageSelect = findViewById(R.id.btnLanguageSelect);

        btnLanguageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int checked = group.getCheckedRadioButtonId();
                String languageSelected = (checked == R.id.srb) ? "S" : "E";
                setLocale(languageSelected);

                Intent intent = new Intent(StartScreenActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
    public void setLocale(String languageSelected){
        String language = (languageSelected.equals("S")) ? "sr" : "en";
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());
        this.setContentView(R.layout.activity_start_screen);
    }
}