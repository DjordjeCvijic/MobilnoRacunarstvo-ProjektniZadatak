package com.example.nationalquiz.models;

public class Answer {
    private String question;
    private String enteredAnswer;
    private boolean isCorrect;
    String imageName;

    public Answer(String question, String enteredAnswer, boolean isCorrect, String imageName) {
        this.question = question;
        this.enteredAnswer = enteredAnswer;
        this.isCorrect = isCorrect;
        this.imageName = imageName;
    }

    public Answer(String text, String enteredAnswer, String image) {
        question=text;
        this.enteredAnswer=enteredAnswer;
        imageName=image;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getEnteredAnswer() {
        return enteredAnswer;
    }

    public void setEnteredAnswer(String enteredAnswer) {
        this.enteredAnswer = enteredAnswer;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Override
    public String toString() {
        return question+","+enteredAnswer+","+isCorrect+","+imageName;
    }
}
