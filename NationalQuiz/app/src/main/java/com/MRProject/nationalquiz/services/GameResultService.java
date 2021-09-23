package com.MRProject.nationalquiz.services;

import android.content.Context;
import android.util.Log;

import com.MRProject.nationalquiz.models.Answer;
import com.MRProject.nationalquiz.models.GameResult;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

public class GameResultService {
    public static final String fileName = "game_result.txt";

    public static void writeGameResult(GameResult gameResultToSave, Context context) {
        try {
            FileOutputStream fileout = context.openFileOutput(fileName, context.MODE_APPEND);//treba . txt
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.append("$" + gameResultToSave.toString());
            outputWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readGameResult(Context context) {
        final int READ_BLOCK_SIZE = 100;
        String result = "";
        try {
            FileInputStream fileIn = context.openFileInput(fileName);
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];

            int charRead;

            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                // char to string conversion
                String readstring = String.copyValueOf(inputBuffer, 0, charRead);
                result += readstring;
            }
            InputRead.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<GameResult> getGamesResultsFromFile(Context context) {
        LinkedList<GameResult> gameResultList = new LinkedList<>();
        GameResult gameResult;
        Answer a;
        String dataFromFile = readGameResult(context);
        String[] gamesResultsData = dataFromFile.split("\\$");
       // Log.d("rezultati", "duzina "+gamesResultsData[0]);
        for (int i = 1; i < gamesResultsData.length; i++) {
            gameResult = new GameResult();
            String[] gamesData = gamesResultsData[i].split("#");
            String[] playerInfo = gamesData[0].split(",");
            gameResult.setPlayerName(playerInfo[0]);
            gameResult.setDate(playerInfo[1]);
            gameResult.setScore(playerInfo[2]);
            for (int j = 1; j < gamesData.length; j++) {
                String[] gameInfo = gamesData[j].split(",");
                a = new Answer(gameInfo[0], gameInfo[1], gameInfo[2].equals("1")?true:false, gameInfo[3]);
                Log.d("Podaci", a.toString());
                gameResult.addAnswer(a);
            }
            Log.d("rezultati", gameResult.toString());
            gameResultList.add(gameResult);
        }


        return gameResultList;
    }

    public static List<Answer> getAnswersOnDate(Context context,String gameDate) {
        List<Answer>answerList=new LinkedList<>();

        List<GameResult> gameResultList=getGamesResultsFromFile(context);
        for(GameResult gs:gameResultList){
            if(gs.getDate().equals(gameDate))
                answerList=gs.getAnswers();
        }
        return answerList;
    }
}
