package com.example.nationalquiz.game_result;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nationalquiz.R;
import com.example.nationalquiz.models.GameResult;

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
            shareGameResultBtn = itemView.findViewById(R.id.cardViewForResult);
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

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final GameResult gameResult = gameResultList.get(position);
        holder.playerNameTv.setText(gameResult.getPlayerName());
        holder.dateTv.setText(gameResult.getDate());
        holder.scoreTv.setText(gameResult.getScore());

        holder.openResultDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent(context, NewsDetailsActivity.class);  ovako otvorim detalje igre
//                intent.putExtra("title",a.getTitle());
//                intent.putExtra("source",a.getSource().getName());
//                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return gameResultList.size();
    }
}
