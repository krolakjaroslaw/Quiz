package com.example.hp.quiz3.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.hp.quiz3.R;
import com.example.hp.quiz3.model.Highscore;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HighscoresAdapter extends RecyclerView.Adapter<HighscoresAdapter.HighscoresHolder> {

    private List<Highscore> mScores;

    public HighscoresAdapter(List<Highscore> mScores) {
        this.mScores = mScores;
    }

    public void setScores(List<Highscore> mScores) {
        this.mScores = mScores;
        notifyDataSetChanged();
    }

    @Override
    public HighscoresHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View rowView = inflater.inflate(R.layout.list_item_score, parent, false);

        return new HighscoresHolder(rowView);
    }

    @Override
    public void onBindViewHolder(HighscoresHolder holder, int position) {

        Highscore score = mScores.get(position);

        holder.mCurrentPosition = score.getId();
        holder.mCurrentScore = score;
        holder.mPlayerName.setText(score.getPlayerName());
        holder.mScore.setText(score.getScore() + " %");
    }

    @Override
    public int getItemCount() {
        return mScores.size();
    }

    public class HighscoresHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.player_name)
        TextView mPlayerName;
        @BindView(R.id.score)
        TextView mScore;

        Highscore mCurrentScore;
        int mCurrentPosition;

        public HighscoresHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
