package com.example.nationalquiz.game_result;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nationalquiz.R;
import com.example.nationalquiz.models.GameResult;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GamesResultsAdapter extends RecyclerView.Adapter<GamesResultsAdapter.ViewHolder> {
    Context context;
    List<GameResult> gameResultList;

    public GamesResultsAdapter(Context context, List<GameResult> gr) {
        this.context = context;
        this.gameResultList = gr;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardViewForResult;
        Button shareGameResultBtn;
        Button openResultDetailsBtn;
        TextView playerNameTv;
        TextView dateTv;
        TextView scoreTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardViewForResult = itemView.findViewById(R.id.cardViewForResult);
            shareGameResultBtn = itemView.findViewById(R.id.shareGameResultBtn);
            openResultDetailsBtn = itemView.findViewById(R.id.openResultDetailsBtn);
            playerNameTv = itemView.findViewById(R.id.playerNameTv);
            dateTv = itemView.findViewById(R.id.dateTv);
            scoreTv = itemView.findViewById(R.id.scoreTv);

        }
    }


    @NonNull
    @Override
    public GamesResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_items, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GameResult gameResult = gameResultList.get(position);
        holder.playerNameTv.setText(gameResult.getPlayerName());

        LocalDateTime localDateTime=LocalDateTime.parse(gameResult.getDate());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        holder.dateTv.setText(localDateTime.format(formatter));

        holder.scoreTv.setText(context.getResources().getString(R.string.finalScore)+" "+gameResult.getScore());

        holder.openResultDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, GameResultDetailsActivity.class);
                intent.putExtra("date",gameResult.getDate());
                intent.putExtra("title",gameResult.getPlayerName()+" "+localDateTime.format(formatter));
                context.startActivity(intent);

            }
        });
        holder.shareGameResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Podijeli", Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return gameResultList.size();
    }
}
