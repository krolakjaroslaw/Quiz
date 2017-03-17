package com.example.hp.quiz3.database;

import com.example.hp.quiz3.model.Highscore;

import java.util.List;

public interface IHighscoresDatabase {

    List<Highscore> getScores();

    void addScore(Highscore score);
}
