package com.example.hp.quiz3.database;

import com.example.hp.quiz3.model.Question;

import java.util.List;

public interface IQuestionsDatabase {

    List<Question> getQuestions();
}
