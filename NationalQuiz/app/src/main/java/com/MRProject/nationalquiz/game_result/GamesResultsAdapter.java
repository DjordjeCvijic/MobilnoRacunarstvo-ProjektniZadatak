package com.MRProject.nationalquiz.game_result;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
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

import com.MRProject.nationalquiz.R;
import com.MRProject.nationalquiz.models.GameResult;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GamesResultsAdapter extends RecyclerView.Adapter<GamesResultsAdapter.ViewHolder> {
    Context context;
    List<GameResult> gameResultList;
    Activity activity;


    public GamesResultsAdapter(Context context, List<GameResult> gr, Activity a) {
        this.context = context;
        this.gameResultList = gr;
        activity = a;

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

        LocalDateTime localDateTime = LocalDateTime.parse(gameResult.getDate());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        holder.dateTv.setText(localDateTime.format(formatter));

        holder.scoreTv.setText(context.getResources().getString(R.string.finalScore) + " " + gameResult.getScore());

        holder.openResultDetailsBtn.setOnClickListener(v -> {

            Intent intent = new Intent(context, GameResultDetailsActivity.class);
            intent.putExtra("date", gameResult.getDate());
            intent.putExtra("title", gameResult.getPlayerName() + " " + localDateTime.format(formatter));
            context.startActivity(intent);


        });
        holder.shareGameResultBtn.setOnClickListener(v -> {
            if (internetIsConnected()) {
                FacebookSdk.sdkInitialize(context.getApplicationContext());
                ShareDialog shareDialog;
                shareDialog = new ShareDialog(activity);


                ShareLinkContent linkContent = new ShareLinkContent.Builder().setQuote(context.getResources().getString(R.string.finalScore) + gameResult.getScore())
                        .setContentUrl(Uri.parse("https://play.google.com/store")).build();
                if (ShareDialog.canShow(ShareLinkContent.class)) {
                    shareDialog.show(linkContent);
                }
            } else
                Toast.makeText(context, context.getResources().getString(R.string.noInternetConnection), Toast.LENGTH_SHORT).show();
        });

    }


    @Override
    public int getItemCount() {
        return gameResultList.size();
    }

    private boolean internetIsConnected() {
        boolean connected = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                connected = true;
            }

        }
        return connected;

    }
}
