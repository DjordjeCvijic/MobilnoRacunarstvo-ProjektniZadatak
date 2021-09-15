package com.example.nationalquiz.game_result;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.nationalquiz.R;
import com.example.nationalquiz.models.GameResult;
import com.example.nationalquiz.news.Adapter;
import com.example.nationalquiz.news.NewsActivity;
import com.example.nationalquiz.services.GameResultService;

import java.util.ArrayList;
import java.util.List;

public class GamesResultsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    GamesResultsAdapter gamesResultsAdapter;
    List<GameResult> gameResultList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_results);

        recyclerView=findViewById(R.id.recyclerViewForResult);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        gamesResultsAdapter=new GamesResultsAdapter(GamesResultsActivity.this,gameResultList);
        recyclerView.setAdapter(gamesResultsAdapter);

        getGamesResultsFromFile();
    }

    private void getGamesResultsFromFile() {

        gameResultList= GameResultService.getGamesResultsFromFile(GamesResultsActivity.this);


        gamesResultsAdapter=new GamesResultsAdapter(GamesResultsActivity.this,gameResultList);
        recyclerView.setAdapter(gamesResultsAdapter);
        gamesResultsAdapter.notifyDataSetChanged();

    }
}