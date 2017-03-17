package com.example.hp.quiz3.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.hp.quiz3.R;
import com.example.hp.quiz3.adapter.HighscoresAdapter;
import com.example.hp.quiz3.database.IHighscoresDatabase;
import com.example.hp.quiz3.database.SQLiteHighscoresDatabase;
import com.example.hp.quiz3.model.Highscore;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HighscoreActivity extends AppCompatActivity {

    @BindView(R.id.highscore)
    RecyclerView mScoresView;

    private IHighscoresDatabase mHighscoresDatabase;
    private HighscoresAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscore);
        ButterKnife.bind(this);

        mHighscoresDatabase = new SQLiteHighscoresDatabase(this);

        mScoresView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new HighscoresAdapter(mHighscoresDatabase.getScores());
        mScoresView.setAdapter(mAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.setScores(mHighscoresDatabase.getScores());
    }
}
