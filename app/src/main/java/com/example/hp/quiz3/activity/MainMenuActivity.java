package com.example.hp.quiz3.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.hp.quiz3.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainMenuActivity extends AppCompatActivity {

    public static final String GAME_TYPE = "gameType";
    public static final String QUESTIONS_NUMBER = "questionsNumber";

    @BindView(R.id.single_game_button)
    Button mSinglePlayerButton;
    @BindView(R.id.multi_game_button)
    Button mMultiPlayerButton;
    @BindView(R.id.high_scores_button)
    Button mHighScoresButton;
    @BindView(R.id.options_button)
    Button mOptionsButton;
    @BindView(R.id.exit_game_button)
    Button mExitGameButton;

    public static int mQuestionsNumber = 7; // liczba pytań

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        ButterKnife.bind(this);

        mSinglePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, StartGameActivity.class);
                intent.putExtra(GAME_TYPE, true);
                intent.putExtra(QUESTIONS_NUMBER, mQuestionsNumber);
                startActivity(intent);
            }
        });

        mMultiPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, StartGameActivity.class);
                intent.putExtra(GAME_TYPE, false);
                intent.putExtra(QUESTIONS_NUMBER, mQuestionsNumber);
                startActivity(intent);
            }
        });

        mHighScoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, HighscoreActivity.class);
                startActivity(intent);
            }
        });

        mOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenuActivity.this, SettingsActivity.class);
                startActivity(intent);
            }
        });

        mExitGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}