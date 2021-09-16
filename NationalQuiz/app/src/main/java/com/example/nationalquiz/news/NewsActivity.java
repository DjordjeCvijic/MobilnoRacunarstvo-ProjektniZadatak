package com.example.nationalquiz.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.nationalquiz.R;
import com.example.nationalquiz.models.Articles;
import com.example.nationalquiz.models.Headlines;
import com.example.nationalquiz.services.NewsService;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private final String API_KEY = "d4209ef4443c4d378913d47460bc854a";
    private Adapter adapter;
    private List<Articles> articles = new ArrayList<>();
    private boolean connection;
    private boolean caching;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Intent intent = getIntent();
        String countryMark = intent.getStringExtra("country");
        connection = intent.getBooleanExtra("internet", false);
        caching = intent.getBooleanExtra("caching", false);

        Log.i("Drzava", "" + NewsService.readNewsCache(NewsActivity.this));
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter(NewsActivity.this, articles, connection);
        recyclerView.setAdapter(adapter);
        NewsService.readNewsCache(NewsActivity.this);
        if (connection)
            retrieveJson(countryMark, API_KEY);
        else {
            getCachedNews(countryMark);
        }

    }


    public void retrieveJson(String country, String apiKey) {
        Call<Headlines> call = ApiClient.getInstance().getApi().getHeadlines(country, apiKey);
        call.enqueue(new Callback<Headlines>() {
            @Override
            public void onResponse(Call<Headlines> call, Response<Headlines> response) {
                if (response.isSuccessful() && response.body().getArticles() != null) {
                    Log.i("Drzava", "Radi");
                    articles.clear();
                    articles = response.body().getArticles();
                    Log.i("Drzava", "a" + articles.size());
                    adapter = new Adapter(NewsActivity.this, articles, connection);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    //getData();;
                    if (caching) {
                        List<Articles> existedArticlesList = NewsService.getNewsCacheFromFileForCountry(NewsActivity.this, country);
                        for (Articles a : existedArticlesList)
                            Log.i("Drzava", "a" + a);
                        List<Articles> articlesToCacheList = new LinkedList<>();
                        for (Articles newArticle : articles) {
                            boolean exist = false;
                            for (Articles existedArticle : existedArticlesList) {
                                if (existedArticle.getPublishedAt().equals(newArticle.getPublishedAt()))
                                    exist = true;
                            }
                            if (!exist) {
                                articlesToCacheList.add(newArticle);
                            }
                        }
                        NewsService.writeNewsCache(articlesToCacheList, NewsActivity.this, country);

                    }
                }
            }

            @Override
            public void onFailure(Call<Headlines> call, Throwable t) {
                Toast.makeText(NewsActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.i("Greska", t.getLocalizedMessage());
            }
        });
    }

    private void getCachedNews(String countryMark) {

        articles.clear();
        articles = NewsService.getNewsCacheFromFileForCountry(NewsActivity.this, countryMark);
        if(articles.size()==0)
            Toast.makeText(this, "Nema kesiranih vijesti", Toast.LENGTH_LONG).show();
        for(Articles a:articles)
            Log.i("kes", "stanje povuceno " + a);
        adapter = new Adapter(NewsActivity.this, articles, connection);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}