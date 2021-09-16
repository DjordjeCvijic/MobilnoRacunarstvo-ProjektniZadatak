package com.example.nationalquiz.news;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nationalquiz.R;
import com.example.nationalquiz.models.Articles;
import com.squareup.picasso.Picasso;

import org.ocpsoft.prettytime.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    Context context;
    List<Articles> articles;
    private boolean connection;

    public Adapter(Context context, List<Articles> articles,boolean connection) {
        this.context = context;
        this.articles = articles;
        this.connection=connection;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.items,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Articles a=articles.get(position);
        holder.tvTitle.setText(a.getTitle());
        holder.tvSource.setText(a.getSource().getName());
        holder.tvDate.setText(dateTime(a.getPublishedAt()));

        if(connection){
            String imageUrl=a.getUrlToImage();
            String url=a.getUrl();
            Picasso.with(context).load(imageUrl).into(holder.imageView);

            //Log.i("clanak: ",a.toString());

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("Drzava", a.getPublishedAt());
                    Intent intent=new Intent(context, NewsDetailsActivity.class);
                    intent.putExtra("title",a.getTitle());
                    intent.putExtra("source",a.getSource().getName());
                    intent.putExtra("time",dateTime(a.getPublishedAt()));
                    intent.putExtra("desc",a.getDescription());
                    intent.putExtra("imageUrl",a.getUrlToImage());
                    intent.putExtra("url",a.getUrl());
                    context.startActivity(intent);
                }
            });
        }else{
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, context.getResources().getString(R.string.noInternetConnection), Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle,tvSource,tvDate;
        ImageView imageView;
        CardView cardView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvTitle=itemView.findViewById(R.id.tvTitle);
            tvSource=itemView.findViewById(R.id.tvSource);
            tvDate=itemView.findViewById(R.id.tvDate);
            imageView=itemView.findViewById(R.id.image);
            cardView=itemView.findViewById(R.id.cardView);

        }
    }

    public String dateTime(String t){
        PrettyTime prettyTime=new PrettyTime(new Locale(getCountry()));
        String time=null;
        try{
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:",Locale.ENGLISH);
            Date date=simpleDateFormat.parse(t);
            time=prettyTime.format(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return time;
    }

    public String getCountry(){
        Locale locale=Locale.getDefault();
        String country=locale.getCountry();
        //Log.i("Drzava",country);
        return  country.toLowerCase();
        //return "rs";

    }
}
