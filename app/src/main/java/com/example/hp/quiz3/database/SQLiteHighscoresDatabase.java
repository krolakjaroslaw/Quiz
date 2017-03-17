package com.example.hp.quiz3.database;

import android.content.Context;

import com.example.hp.quiz3.model.Highscore;
import com.j256.ormlite.android.AndroidConnectionSource;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

public class SQLiteHighscoresDatabase implements IHighscoresDatabase {

    private Dao<Highscore, Integer> mDao;

    public SQLiteHighscoresDatabase(Context context) {

        HighscoresDbOpenHelper dbHelper = new HighscoresDbOpenHelper(context);
        ConnectionSource cs = new AndroidConnectionSource(dbHelper);
        try {
            mDao = DaoManager.createDao(cs, Highscore.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Highscore> getScores() {
        try {
            return mDao.queryBuilder()
                    .orderBy("score", false)
                    .orderBy("dateCreated", true)
                    .query();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void addScore(Highscore score) {
        try {
            mDao.create(score);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
