package com.example.hp.quiz3.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.hp.quiz3.database.IQuestionsDatabase;
import com.example.hp.quiz3.model.Question;
import com.example.hp.quiz3.R;
import com.example.hp.quiz3.database.RandomQuestionsDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartGameActivity extends AppCompatActivity {

    public static final String PLAYER_ONE_NAME = "playerOneName";
    public static final String PLAYER_TWO_NAME = "playerTwoName";
    public static final String QUESTIONS = "questions";

    @BindView(R.id.player_one)
    TextView mPlayerOneTextView;
    @BindView(R.id.player_two)
    TextView mPlayerTwoTextView;
    @BindView(R.id.player_one_name)
    EditText mPlayerOneEditText;
    @BindView(R.id.player_two_name)
    EditText mPlayerTwoEditText;
    @BindView(R.id.next_button)
    Button mNextButton;

    public static boolean mIsSinglePlayerGame = false;
    private int mQuestionsNumber;

    private IQuestionsDatabase mQuestionsDatabase = new RandomQuestionsDatabase();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_start_game);
        ButterKnife.bind(this);

        mIsSinglePlayerGame = getIntent().getExtras().getBoolean(MainMenuActivity.GAME_TYPE);
        mQuestionsNumber = getIntent().getExtras().getInt(MainMenuActivity.QUESTIONS_NUMBER);

        if (mIsSinglePlayerGame) {
            mPlayerTwoTextView.setVisibility(View.GONE);
            mPlayerTwoEditText.setVisibility(View.GONE);
            mPlayerOneEditText.setHint("Podaj imię");
        }

        if (!mIsSinglePlayerGame) {
            mQuestionsNumber *= 2;
        }
    }

    @OnClick(R.id.next_button)
    void openNextScreen() {
        String playerOneName = mPlayerOneEditText.getText().toString();
        String playerTwoName = mPlayerTwoEditText.getText().toString();

        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(PLAYER_ONE_NAME, playerOneName);
        intent.putExtra(PLAYER_TWO_NAME, playerTwoName);

        // losowanie pytan
        List<Question> questions = mQuestionsDatabase.getQuestions();
        Random random = new Random();
        while (questions.size() > mQuestionsNumber) {
            questions.remove(random.nextInt(questions.size()));
        }
        Collections.shuffle(questions);

        intent.putExtra(QUESTIONS, new ArrayList<>(questions));

        startActivity(intent);
    }
}