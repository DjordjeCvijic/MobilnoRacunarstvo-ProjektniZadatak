package com.example.nationalquiz;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.widget.Toast;


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

        ListPreference lp = (ListPreference) findPreference("ORIENTATION");
        String orient = sp.getString("ORIENTATION", "false");
        if ("1".equals(orient)) {
            Toast.makeText(this, "orijentacija load 1", Toast.LENGTH_SHORT).show();
            lp.setSummary(lp.getEntry());//ovo da pri pokretanju popuni onaj natpis ispod
        }

        lp.setOnPreferenceChangeListener(new android.preference.Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(android.preference.Preference preference, Object newValue) {
                String item = (String) newValue;
                if (preference.getKey().equals("ORIENTATION")) {
                    switch (item) {
                        case "1":
                            Toast.makeText(Preference.this, "Orjentacija 1", Toast.LENGTH_SHORT).show();
                            break;
                        case "2":
                            Toast.makeText(Preference.this, "Orjentacija 2", Toast.LENGTH_SHORT).show();
                            break;
                        case "3":
                            Toast.makeText(Preference.this, "Orjentacija 4", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    //da promijeni onaj naziv ispod
                    ListPreference lpp = (ListPreference) preference;
                    lpp.setSummary(lpp.getEntries()[lpp.findIndexOfValue(item)]);
                }
                return true;
            }
        });
    }

    @Override
    protected void onResume() {
        load_setting();
        super.onResume();
    }
}
