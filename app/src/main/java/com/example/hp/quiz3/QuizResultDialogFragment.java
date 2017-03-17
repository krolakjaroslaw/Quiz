package com.example.hp.quiz3;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.example.hp.quiz3.activity.HighscoreActivity;
import com.example.hp.quiz3.activity.StartGameActivity;
import com.example.hp.quiz3.database.IHighscoresDatabase;
import com.example.hp.quiz3.database.SQLiteHighscoresDatabase;
import com.example.hp.quiz3.model.Highscore;

import java.util.Date;

public class QuizResultDialogFragment extends DialogFragment {

    public static QuizResultDialogFragment createDialog(String playerOneName, String playerTwoName,
                                                        int correctAnswersOne, int correctAnswersTwo,
                                                        int questionsCount) {

        QuizResultDialogFragment fragment = new QuizResultDialogFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        args.putString("playerOneName", playerOneName);
        args.putString("playerTwoName", playerTwoName);
        args.putInt("playerOneResult", correctAnswersOne);
        args.putInt("playerTwoResult", correctAnswersTwo);
        args.putInt("questionsCount", questionsCount);

        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        String playerOneName = getArguments().getString("playerOneName");
        String playerTwoName = getArguments().getString("playerTwoName");
        int playerOneResult = getArguments().getInt("playerOneResult");
        int playerTwoResult = getArguments().getInt("playerTwoResult");
        int questionsCount = getArguments().getInt("questionsCount");

        String message = String.format("Wynik gracza %s: %d / %d." +
                (!StartGameActivity.mIsSinglePlayerGame ? "\nWynik gracza %s: %d / %d." : ""),
                playerOneName, playerOneResult, questionsCount, playerTwoName,
                playerTwoResult, questionsCount);

        setCancelable(false);
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle("Wynik quizu")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getActivity().finish();
                    }
                })
                .create();

        return dialog;
    }
}
