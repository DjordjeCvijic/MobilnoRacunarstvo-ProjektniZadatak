package com.example.nationalquiz.model_view;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CapitalCitiesActivityModelView extends ViewModel {
    public final MutableLiveData<String> text = new MutableLiveData<String>();

    public CapitalCitiesActivityModelView() {
        // trigger user load.
        Log.i("model","kreiranje");
        text.setValue("text");
    }

    public void doAction(String t) {
        text.setValue(t);
    }

}
