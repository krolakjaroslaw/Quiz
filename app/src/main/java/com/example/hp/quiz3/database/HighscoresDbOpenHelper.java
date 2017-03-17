package com.example.hp.quiz3.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.hp.quiz3.model.Highscore;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class HighscoresDbOpenHelper extends OrmLiteSqliteOpenHelper {

    public static final String DATABASE_NAME = "highscores.db";
    public static final int DATABASE_VERSION = 1;

    public HighscoresDbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Highscore.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource,
                          int oldVersion, int newVersion) {

    }
}
