package com.MRProject.nationalquiz.game_result;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.os.Bundle;

import com.MRProject.nationalquiz.R;
import com.MRProject.nationalquiz.models.GameResult;
import com.MRProject.nationalquiz.services.GameResultService;

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

        gamesResultsAdapter=new GamesResultsAdapter(GamesResultsActivity.this,gameResultList,this);
        recyclerView.setAdapter(gamesResultsAdapter);

        getGamesResultsFromFile();
    }

    private void getGamesResultsFromFile() {

        gameResultList= GameResultService.getGamesResultsFromFile(GamesResultsActivity.this);
        gamesResultsAdapter=new GamesResultsAdapter(GamesResultsActivity.this,gameResultList,this);
        recyclerView.setAdapter(gamesResultsAdapter);
        gamesResultsAdapter.notifyDataSetChanged();

    }

}