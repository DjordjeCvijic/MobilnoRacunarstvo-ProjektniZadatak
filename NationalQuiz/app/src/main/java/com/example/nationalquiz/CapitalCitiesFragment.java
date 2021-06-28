package com.example.nationalquiz;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nationalquiz.data_base.CountryDBHelper;
import com.example.nationalquiz.data_base.CountryDBService;
import com.example.nationalquiz.models.Country;

import java.util.LinkedList;


public class CapitalCitiesFragment extends Fragment {

    private LinkedList<Country>countriesDataList;
    int imageToShow=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_capital_cities, container, false);

        countriesDataList=GameActivity.countriesDataList;
        ImageView imageView=root.findViewById(R.id.imageHolder);
        Button button=root.findViewById(R.id.switchImageBtn);

        String imgname=countriesDataList.get(imageToShow).getFlagImage();
        imageToShow++;
        int resID = getResources().getIdentifier(imgname , "drawable", getActivity().getPackageName());
        Drawable drawable = getResources().getDrawable(resID); //<----line with error
        imageView.setImageDrawable(drawable);

        Log.i("Baza","broj listi"+countriesDataList.size());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String imgname=countriesDataList.get(imageToShow).getFlagImage();
                Log.i("slika: ",imgname);
                int resID = getResources().getIdentifier(imgname , "drawable", getActivity().getPackageName());
                Drawable drawable = getActivity().getDrawable(resID);

                imageView.setImageDrawable(drawable);

                imageToShow=(imageToShow+1)%20;


            }
        });

        return root;
    }
}