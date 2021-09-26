package com.MRProject.nationalquiz.news;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.MRProject.nationalquiz.R;
import com.MRProject.nationalquiz.models.Articles;
import com.MRProject.nationalquiz.models.Headlines;
import com.MRProject.nationalquiz.services.NewsService;

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
                    articles.clear();
                    articles = response.body().getArticles();
                    adapter = new Adapter(NewsActivity.this, articles, connection);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    if (caching) {
                        List<Articles> existedArticlesList = NewsService.getNewsCacheFromFileForCountry(NewsActivity.this, country);
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
            }
        });
    }

    private void getCachedNews(String countryMark) {
        articles.clear();
        articles = NewsService.getNewsCacheFromFileForCountry(NewsActivity.this, countryMark);
        if(articles.size()==0)
            Toast.makeText(this, getResources().getString(R.string.noCachedNews), Toast.LENGTH_LONG).show();
        adapter = new Adapter(NewsActivity.this, articles, connection);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}