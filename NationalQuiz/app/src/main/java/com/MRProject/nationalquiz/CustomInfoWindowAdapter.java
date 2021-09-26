package com.MRProject.nationalquiz;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

public class CustomInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View mWindow;
    private Context mContext;


    public CustomInfoWindowAdapter(Context context) {
        this.mContext = context;
        mWindow= LayoutInflater.from(context).inflate(R.layout.custom_info_window, null);
    }

    private void renderWindowText(Marker marker,View view){
        String title= marker.getTitle();
        TextView txtTitle=(TextView) view.findViewById(R.id.markerTitle);

        if(!title.equals("")){
            txtTitle.setText(title);
        }
        ImageView imageView = (ImageView) view.findViewById(R.id.markerImg);
        imageView.setImageResource(mContext.getResources().getIdentifier(marker.getSnippet(),"drawable",mContext.getPackageName()));
    }

    @Override
    public View getInfoWindow(Marker marker) {
        renderWindowText(marker,mWindow);
        return mWindow;
    }

    @Override
    public View getInfoContents(Marker marker) {
        renderWindowText(marker,mWindow);
        return mWindow;
    }
}