package com.MRProject.nationalquiz;

import androidx.fragment.app.FragmentActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.MRProject.nationalquiz.games.CapitalCitiesActivity;
import com.MRProject.nationalquiz.models.Country;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback ,GoogleMap.OnInfoWindowClickListener{

    private GoogleMap mMap;
    private String selectedLanguage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        loadSetting();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Country countryToShow= CapitalCitiesActivity.currentCountry;
        mMap = googleMap;
        // prikazi kontrole za zoom
        mMap.getUiSettings().setZoomControlsEnabled(true);


        LatLng pointLatLgn = new LatLng(countryToShow.getCapitalCityLatitude(), countryToShow.getCapitalCityLongitude());
        mMap.setInfoWindowAdapter(new CustomInfoWindowAdapter(MapsActivity.this));

        MarkerOptions marker = new MarkerOptions()
                .position(pointLatLgn)
                .title(selectedLanguage.equals("en")?countryToShow.getCapitalCityEn():countryToShow.getCapitalCitySr())
                .snippet(countryToShow.getCityCoatOfArmsImage());
        mMap.addMarker(marker);



//        mMap.addMarker(new MarkerOptions()
//                .position(pointLatLgn)
//                .title(selectedLanguage.equals("en")?countryToShow.getCapitalCityEn():countryToShow.getCapitalCitySr())
//                .snippet("ide neki podnaslov")//kada se klikne na marker pojavi se ovo a ako se klikne poziva se setOnInfoWindowClickListener(this);
//                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
//                //.icon(BitmapDescriptorFactory.fromResource(R.drawable.pin))//pin prreko slicice
//        );

            float zL=6.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pointLatLgn,zL));


        // zumiranje mape
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
    }



    private void loadSetting() {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        selectedLanguage = sp.getString("LANGUAGE", "false");
    }


    @Override
    public void onInfoWindowClick(Marker marker) {

    }
}