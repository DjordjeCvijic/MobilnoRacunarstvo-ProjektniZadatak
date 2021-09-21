package com.MRProject.nationalquiz.models;

import java.util.LinkedList;
import java.util.List;

public class GameResult {

    private String playerName;
    private String date;
    private String score;
    private List<Answer> answers=new LinkedList<>();

    public GameResult(String date, String playerName, String score, List<Answer> answers) {
        this.date = date;
        this.playerName = playerName;
        this.score = score;
        this.answers = answers;
    }

    public GameResult() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public void addAnswer(Answer a){
        answers.add(a);
    }

    @Override
    public String toString() {
        String str= playerName+","+date+","+score;
        for(Answer a:answers){
            str=str.concat("#"+a);
        }
        return str;
    }
}
