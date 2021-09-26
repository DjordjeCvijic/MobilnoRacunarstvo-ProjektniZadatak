package com.MRProject.nationalquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;

import java.util.Locale;


public class Preference extends PreferenceActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.PreferencesTheme);
        addPreferencesFromResource(R.xml.prefs);
        load_setting();
    }

    private void load_setting() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);

        //ovo je za postavljanje listener-a za promjenu
        CheckBoxPreference checkBoxPreference_instant = (CheckBoxPreference) findPreference("CACHING");
        checkBoxPreference_instant.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(android.preference.Preference prefs, Object obj) {
                boolean yes = (boolean) obj;
                return true;
            }
        });

        ListPreference lp = (ListPreference) findPreference("LANGUAGE");
        String language = sp.getString("LANGUAGE", "en");

        lp.setSummary(lp.getEntries()[lp.findIndexOfValue(language)]);
        lp.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(android.preference.Preference preference, Object newValue) {
                String item = (String) newValue;
                if (preference.getKey().equals("LANGUAGE")) {
                    //da promijeni onaj naziv ispod
                    ListPreference lpp = (ListPreference) preference;
                    lpp.setSummary(lpp.getEntries()[lpp.findIndexOfValue(item)]);

                    setLocale(item);
                    finish();
                    startActivity(getIntent());

                }
                return true;
            }
        });

        EditTextPreference etp = (EditTextPreference) findPreference("NUMBER_OF_QUESTION");
        String number = sp.getString("NUMBER_OF_QUESTION", "5");
        etp.setSummary(number);
        etp.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(android.preference.Preference preference, Object newValue) {
                String newSummary = (String) newValue;
                if (preference.getKey().equals("NUMBER_OF_QUESTION")) {

                    EditTextPreference etp1 = (EditTextPreference) preference;
                    etp1.setSummary(newSummary);

                }
                return true;
            }
        });

    }

    public void setLocale(String languageToSet) {
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
        Intent i = new Intent(Preference.this, MainActivity.class);
        startActivity(i);
        super.onBackPressed();
    }
}
