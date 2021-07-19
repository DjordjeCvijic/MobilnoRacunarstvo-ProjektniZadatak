package com.example.nationalquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;

import java.util.Locale;


public class Preference extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.prefs);
        load_setting();
    }

    private void load_setting() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        //ovo je za postavljanje vrijednosti na osnovu parametra kada se otvara ovaj aktiviti
        boolean chk_night = sp.getBoolean("NIGHT", false);
        if (chk_night) {
            //ovdje mijenjamo boju pozadine
            Toast.makeText(this, "nocni mod load on", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "nocni mod load of", Toast.LENGTH_SHORT).show();
        }
        //ovo je za postavljanje listener-a za promjenu
        CheckBoxPreference checkBoxPreference_instant = (CheckBoxPreference) findPreference("NIGHT");
        checkBoxPreference_instant.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(android.preference.Preference prefs, Object obj) {
                boolean yes = (boolean) obj;

                if (yes) {
                    Toast.makeText(Preference.this, "nocni mod change on", Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(Preference.this, "nocni mod change off", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        ListPreference lp = (ListPreference) findPreference("LANGUAGE");
        String language = sp.getString("LANGUAGE", "false");
//        if ("sr".equals(orient)) {
//            //Toast.makeText(this, "orijentacija load 1", Toast.LENGTH_SHORT).show();
//            lp.setSummary(lp.getEntry());//ovo da pri pokretanju popuni onaj natpis ispod
//        }else
        lp.setSummary(lp.getEntry());

        lp.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(android.preference.Preference preference, Object newValue) {
                String item = (String) newValue;
                if (preference.getKey().equals("LANGUAGE")) {
//                    switch (item) {
//                        case "sr":
//                            Toast.makeText(Preference.this, item, Toast.LENGTH_SHORT).show();
//                            //setLocale("sr");
//                            break;
//                        case "en":
//                            Toast.makeText(Preference.this, item, Toast.LENGTH_SHORT).show();
//                            //setLocale("en");
//                            break;
//
//                    }

                    //da promijeni onaj naziv ispod
                    ListPreference lpp = (ListPreference) preference;
                    lpp.setSummary(lpp.getEntries()[lpp.findIndexOfValue(item)]);

                    setLocale(item);
                    finish();
                    startActivity(getIntent());
//                    setPreferenceScreen(null);
//                    addPreferencesFromResource(R.xml.prefs);  radi ali ne ispise ispod izabran jezik
                }
                return true;
            }
        });

        EditTextPreference etp=(EditTextPreference) findPreference("NUMBER_OF_QUESTION");
        String number = sp.getString("NUMBER_OF_QUESTION", "5");
//        if ("sr".equals(orient)) {
//            //Toast.makeText(this, "orijentacija load 1", Toast.LENGTH_SHORT).show();
//            lp.setSummary(lp.getEntry());//ovo da pri pokretanju popuni onaj natpis ispod
//        }else
        etp.setSummary(number);
        etp.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(android.preference.Preference preference, Object newValue) {
                String newSummary = (String) newValue;
                if (preference.getKey().equals("NUMBER_OF_QUESTION")) {


//                    //da promijeni onaj naziv ispod
//                    ListPreference lpp = (ListPreference) preference;
//                    lpp.setSummary(lpp.getEntries()[lpp.findIndexOfValue(item)]);
                    EditTextPreference etp1=(EditTextPreference)preference;
                    etp1.setSummary(newSummary);

                }
                return true;
            }
        });

    }
    public void setLocale(String languageToSet){
        Locale locale = new Locale(languageToSet);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,
                getBaseContext().getResources().getDisplayMetrics());


    }

    @Override
    protected void onResume() {
        load_setting();
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(Preference.this,MainActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
}
