package com.example.nationalquiz.services;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.nationalquiz.models.GameResult;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

public class GameResultService {
    public static final String fileName="game_result.txt";

    public static void writeGameResult(GameResult gameResultToSave, Context context) {
        try {
            FileOutputStream fileout = context.openFileOutput(fileName, context.MODE_APPEND);//treba . txt
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
            outputWriter.append("$" + gameResultToSave.toString());
            outputWriter.close();

            //display file saved message
//            Toast.makeText(getBaseContext(), "File saved successfully!",
//                    Toast.LENGTH_SHORT).show();
            //Log.i("sadrzaj fajla", "$"+gameResultToSave.toString());

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

    public static List<GameResult> getGamesResultsFromFile(){
        LinkedList<GameResult> gameResultList=new LinkedList<>();

        //dohvatiti i parsirati rezultate






        return gameResultList;
    }
}
