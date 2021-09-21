package com.MRProject.nationalquiz.game_result;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.MRProject.nationalquiz.R;
import com.MRProject.nationalquiz.models.Answer;

import java.util.List;

public class GameResultDetailsAdapter extends RecyclerView.Adapter<GameResultDetailsAdapter.ViewHolder> {
    Context context;
    List<Answer> answerList;

    public GameResultDetailsAdapter(Context context, List<Answer> a) {
        this.context = context;
        this.answerList = a;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.result_details_items, parent, false);
        return new GameResultDetailsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Answer answer = answerList.get(position);
        holder.questionTv.setText(answer.getQuestion());
        holder.answerTv.setText(context.getResources().getString(R.string.answer)+" "+answer.getEnteredAnswer());
        if (answer.getImageName() != null)
            holder.questionImageIv.setImageResource(context.getResources().getIdentifier(answer.getImageName(), "drawable", context.getPackageName()));
        if (answer.isCorrect()) {
            holder.correctAnswerIv.setBackgroundResource(R.drawable.ic_correct_answer);

        } else {
            holder.correctAnswerIv.setBackgroundResource(R.drawable.ic_incorrect_answer);
        }


    }

    @Override
    public int getItemCount() {
        return answerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CardView cardViewForResult;
        TextView questionTv;
        TextView answerTv;
        ImageView questionImageIv;
        ImageView correctAnswerIv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardViewForResult = itemView.findViewById(R.id.cardViewForResultDetails);
            questionTv = itemView.findViewById(R.id.questionTv);
            answerTv = itemView.findViewById(R.id.answerTv);
            questionImageIv = itemView.findViewById(R.id.questionImageIv);
            correctAnswerIv = itemView.findViewById(R.id.correctAnswerIv);

        }
    }
}
