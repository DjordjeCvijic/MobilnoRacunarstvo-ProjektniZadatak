package com.example.newsapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.newsapp.Model.Articles;
import com.example.newsapp.Model.Headlines;
import com.example.newsapp.data_base.NewsDbHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    final String API_KEY="d4209ef4443c4d378913d47460bc854a";
    Adapter adapter;
    List<Articles> articles=new ArrayList<>();

    NewsDbHelper newsDbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsDbHelper = new NewsDbHelper(MainActivity.this);

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        String country=getCountry();

        adapter=new Adapter(MainActivity.this,articles);
        recyclerView.setAdapter(adapter);

        Button b=findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrieveJson(getCountry(),API_KEY);

            }
        });


    }

    public void retrieveJson(String country,String apiKey){
        Call<Headlines> call=ApiClient.getInstance().getApi().getHeadlines(country,apiKey);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if(response.isSuccessful() && response.body().getArticles()!=null){
                    Log.i("Drzava","Radi");
                    articles.clear();
                    articles=response.body().getArticles();
                    Log.i("Drzava","a"+articles.size());
                    adapter=new Adapter(MainActivity.this,articles);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    getData();;
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Greska",t.getLocalizedMessage());
            }
        });
    }

    public String getCountry(){
//        Locale locale=Locale.getDefault();
//        String country=locale.getCountry();
//        Log.i("Drzava",country);
//        return  country.toLowerCase();
        return "rs";
    }
    public void getData(){
        for (Articles a:articles)
            newsDbHelper.insertNewsForSr(a);

        LinkedList<Articles>news=newsDbHelper.getNewsForSr();
        for (Articles a:news)
            Log.i("vjesti :",a.toString());
        Log.i("vjesti broj ",":"+newsDbHelper.numberOfRows());
    }

}