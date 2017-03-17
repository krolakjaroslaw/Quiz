package com.example.hp.quiz3.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.example.hp.quiz3.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    @BindView(R.id.game_type_txt)
    TextView mTypeGameTextView;
    @BindView(R.id.questions_number_txt)
    TextView mQuestionsNumberTextView;
    @BindView(R.id.background_color_txt)
    TextView mBackgroundColorTextView;
    @BindView(R.id.game_type)
    Switch mTypeGame;
    @BindView(R.id.questions_number)
    SeekBar mQuestionsNumber;
    @BindView(R.id.background_color)
    Spinner mBackgroundColor;
    @BindView(R.id.save_btn)
    Button mSaveButton;

    private boolean isSinglePlayer;
    private int questionsNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.save_btn)
    void onSaveClick() {
        Intent intent = new Intent(this, MainMenuActivity.class);
        intent.putExtra("gameType", isSinglePlayer);
        intent.putExtra("questionsNumber", questionsNumber);
        startActivity(intent);
    }
}
