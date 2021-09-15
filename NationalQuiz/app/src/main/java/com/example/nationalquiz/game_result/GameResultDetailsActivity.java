package com.example.nationalquiz.game_result;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.nationalquiz.R;
import com.example.nationalquiz.models.Answer;
import com.example.nationalquiz.models.GameResult;
import com.example.nationalquiz.news.Adapter;
import com.example.nationalquiz.news.NewsActivity;
import com.example.nationalquiz.services.GameResultService;

import java.util.ArrayList;
import java.util.List;

public class GameResultDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView titleTv;
    GameResultDetailsAdapter gameResultDetailsAdapter;
    List<Answer> answerList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_result_details);
        Intent intent=getIntent();
        String gameDate = intent.getStringExtra("date");
        String title = intent.getStringExtra("title");
        recyclerView=findViewById(R.id.recyclerViewForResultDetails);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        titleTv=findViewById(R.id.titleTv);
        titleTv.setText(title);
        gameResultDetailsAdapter=new GameResultDetailsAdapter(GameResultDetailsActivity.this,answerList);
        recyclerView.setAdapter(gameResultDetailsAdapter);

        getAnswers(gameDate);
    }

    private void getAnswers(String gameDate) {
        answerList= GameResultService.getAnswersOnDate(GameResultDetailsActivity.this,gameDate);
        gameResultDetailsAdapter=new GameResultDetailsAdapter(GameResultDetailsActivity.this,answerList);
        recyclerView.setAdapter(gameResultDetailsAdapter);
        gameResultDetailsAdapter.notifyDataSetChanged();

    }
}