package com.example.hp.quiz3.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.hp.quiz3.database.IHighscoresDatabase;
import com.example.hp.quiz3.database.SQLiteHighscoresDatabase;
import com.example.hp.quiz3.model.Highscore;
import com.example.hp.quiz3.model.Question;
import com.example.hp.quiz3.QuizResultDialogFragment;
import com.example.hp.quiz3.R;

import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GameActivity extends AppCompatActivity {

    public static final String CURRENT_QUESTION = "currentQuestion";
    public static final String CHOICES = "choices";

    @BindView(R.id.player_one_name)
    TextView mPlayerOneTextView;
    @BindView(R.id.player_two_name)
    TextView mPlayerTwoTextView;
    @BindView(R.id.question)
    TextView mQuestion;
    @BindView(R.id.answers)
    RadioGroup mAnswers;
    @BindViews({R.id.answer_1, R.id.answer_2, R.id.answer_3})
    List<RadioButton> mRadioButtons;
    @BindView(R.id.back_button)
    Button mBackButton;
    @BindView(R.id.next_button)
    Button mNextButton;

    private String mPlayerOneName;
    private String mPlayerTwoName;
    private List<Question> mQuestions;
    private int mCurrentQuestion;
    private int[] mChoices;

    private IHighscoresDatabase mHighscoreDatabase;
    private Highscore mScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game);
        ButterKnife.bind(this);

        mHighscoreDatabase = new SQLiteHighscoresDatabase(this);

        mPlayerOneName = getIntent().getStringExtra(StartGameActivity.PLAYER_ONE_NAME);
        mPlayerTwoName = getIntent().getStringExtra(StartGameActivity.PLAYER_TWO_NAME);
        mQuestions = (List<Question>) getIntent().getSerializableExtra(StartGameActivity.QUESTIONS);
        mChoices = new int[mQuestions.size()];

        mPlayerOneTextView.setText("Odpowiada: " + mPlayerOneName);
        mPlayerTwoTextView.setText("Odpowiada: " + mPlayerTwoName);

        if (StartGameActivity.mIsSinglePlayerGame) {
            mPlayerTwoTextView.setVisibility(View.GONE);
        }

        refreshView();
    }

    private void refreshView() {
        Question question = mQuestions.get(mCurrentQuestion);
        mQuestion.setText(question.getQuestion());

        int index = 0;
        for (RadioButton rb : mRadioButtons) {
            rb.setText(question.getAnswers().get(index++));
        }

        mBackButton.setVisibility(mCurrentQuestion == 0 ? View.GONE : View.VISIBLE);
        mNextButton.setText(mCurrentQuestion == mQuestions.size()-1 ? "Zakończ" : "Dalej");

        // czyszczenie zaznaczenia przy przejsciach
        mAnswers.clearCheck();

        if (mChoices[mCurrentQuestion] > 0) {
            mAnswers.check(mChoices[mCurrentQuestion]);
        }

        if(!StartGameActivity.mIsSinglePlayerGame) {
            if (mCurrentQuestion < mQuestions.size() / 2) {
                mPlayerTwoTextView.setVisibility(View.GONE);
                mPlayerOneTextView.setVisibility(View.VISIBLE);
            } else {
                mPlayerOneTextView.setVisibility(View.GONE);
                mPlayerTwoTextView.setVisibility(View.VISIBLE);
            }

            if (mCurrentQuestion == mQuestions.size() / 2) {
                mBackButton.setVisibility(View.GONE);
            }
        }
    }

    @OnClick(R.id.back_button)
    void onBackClick() {
        // zapisanie zaznaczonej odpowiedzi na biezace pytanie (przy przejsciach)
        mChoices[mCurrentQuestion] = mAnswers.getCheckedRadioButtonId();

        mCurrentQuestion--;
        refreshView();
    }

    @OnClick(R.id.next_button)
    void onNextClick() {
        // zapisanie zaznaczonej odpowiedzi na biezace pytanie (przy przejsciach)
        mChoices[mCurrentQuestion] = mAnswers.getCheckedRadioButtonId();

        boolean isLastQuestion = mCurrentQuestion == mQuestions.size() - 1;
        if (isLastQuestion) {
            countResult();
            return;
        }
        mCurrentQuestion++;
        refreshView();
    }

    private void countResult() {
        Integer questionsNumber = StartGameActivity.mIsSinglePlayerGame ?
                mQuestions.size() : mQuestions.size()/2;

        Integer correctAnswersOne = 0;
        Integer correctAnswersTwo = 0;

        for (int i=0; i<questionsNumber; i++) {
            int correctAnswerIndex = mQuestions.get(i).getCorrectAnswer(); // index poprawnej odpowiedzi
            int choiceRadioButtonsId = mChoices[i]; // id zaznaczonej odpowiedzi
            int choiceIndex = -1;
            for (int j=0; j<mRadioButtons.size(); j++) {
                if (mRadioButtons.get(j).getId() == choiceRadioButtonsId) {
                    choiceIndex = j; // index zaznaczonej odpowiedzi
                    break;
                }
            }
            if (correctAnswerIndex == choiceIndex) {
                correctAnswersOne++;
            }
        }

        if (!StartGameActivity.mIsSinglePlayerGame) {
            for (int i = questionsNumber; i < mQuestions.size(); i++) {
                int correctAnswerIndex = mQuestions.get(i).getCorrectAnswer();
                int choiceRadioButtonsId = mChoices[i];
                int choiceIndex = -1;
                for (int j = 0; j < mRadioButtons.size(); j++) {
                    if (mRadioButtons.get(j).getId() == choiceRadioButtonsId) {
                        choiceIndex = j;
                        break;
                    }
                }
                if (correctAnswerIndex == choiceIndex) {
                    correctAnswersTwo++;
                }
            }
        }

        mScore = new Highscore();
        mScore.setDateCreated(new Date());
        mScore.setPlayerName(mPlayerOneName);
        mScore.setScore(Double.parseDouble((double) Math.round(
                correctAnswersOne / (double) questionsNumber * 10000d) / 100d + ""));
        mHighscoreDatabase.addScore(mScore);

        if (!StartGameActivity.mIsSinglePlayerGame) {
            mScore = new Highscore();
            mScore.setDateCreated(new Date());
            mScore.setPlayerName(mPlayerTwoName);
            mScore.setScore(Double.parseDouble((double) Math.round(
                    correctAnswersTwo / (double) questionsNumber * 10000d) / 100d + ""));
            mHighscoreDatabase.addScore(mScore);
        }

        QuizResultDialogFragment.createDialog(mPlayerOneName, mPlayerTwoName, correctAnswersOne,
                correctAnswersTwo, questionsNumber)
                .show(getSupportFragmentManager(), "");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        mChoices[mCurrentQuestion] = mAnswers.getCheckedRadioButtonId();
        outState.putInt(CURRENT_QUESTION, mCurrentQuestion); // biezace pytanie
        outState.putIntArray(CHOICES, mChoices); // zaznaczone odpowiedzi
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mCurrentQuestion = savedInstanceState.getInt(CURRENT_QUESTION);
        mChoices = savedInstanceState.getIntArray(CHOICES);

        refreshView();
    }
}