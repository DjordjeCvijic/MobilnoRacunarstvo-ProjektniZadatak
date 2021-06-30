package com.example.nationalquiz;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.nationalquiz.models.Articles;
import com.example.nationalquiz.models.Headlines;
import com.example.nationalquiz.news.Adapter;
import com.example.nationalquiz.news.ApiClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    final String API_KEY="d4209ef4443c4d378913d47460bc854a";
    Adapter adapter;
    List<Articles> articles=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Intent intent=getIntent();
        String countryMark = intent.getStringExtra("country");

        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter=new Adapter(NewsActivity.this,articles);
        recyclerView.setAdapter(adapter);

        retrieveJson(countryMark,API_KEY);

    }
    public void retrieveJson(String country,String apiKey){
        Call<Headlines> call= ApiClient.getInstance().getApi().getHeadlines(country,apiKey);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if(response.isSuccessful() && response.body().getArticles()!=null){
                    Log.i("Drzava","Radi");
                    articles.clear();
                    articles=response.body().getArticles();
                    Log.i("Drzava","a"+articles.size());
                    adapter=new Adapter(NewsActivity.this,articles);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    //getData();;
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                Toast.makeText(NewsActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Greska",t.getLocalizedMessage());
            }
        });
    }
}