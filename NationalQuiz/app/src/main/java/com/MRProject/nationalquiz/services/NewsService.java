package com.MRProject.nationalquiz.services;

import android.content.Context;

import com.MRProject.nationalquiz.models.Answer;
import com.MRProject.nationalquiz.models.Articles;
import com.MRProject.nationalquiz.models.GameResult;
import com.MRProject.nationalquiz.models.Source;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

public class NewsService {
    public static final String fileName = "news_cache.txt";

    public static void writeNewsCache(List<Articles> articlesList, Context context, String countryMark) {
        try {
            FileOutputStream fileOut = context.openFileOutput(fileName, Context.MODE_APPEND);
            OutputStreamWriter outputWriter = new OutputStreamWriter(fileOut);
            for (Articles articles : articlesList) {
                outputWriter.append("$terminated$" + countryMark + "#terminated#" + articles.getTitle() + "#terminated#" + articles.getSource().getName() + "#terminated#" + articles.getPublishedAt());
            }
            outputWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String readNewsCache(Context context) {
        final int READ_BLOCK_SIZE = 100;
        String result = "";
        try {
            FileInputStream fileIn = context.openFileInput(fileName);
            InputStreamReader InputRead = new InputStreamReader(fileIn);

            char[] inputBuffer = new char[READ_BLOCK_SIZE];

            int charRead;

            while ((charRead = InputRead.read(inputBuffer)) > 0) {
                // char to string conversion
                String readString = String.copyValueOf(inputBuffer, 0, charRead);
                result += readString;
            }
            InputRead.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static List<Articles> getNewsCacheFromFileForCountry(Context context, String countryMark) {
        LinkedList<Articles> articlesList = new LinkedList<>();
        String dataFromFile = readNewsCache(context);
        String[] newsCacheData = dataFromFile.split("\\$terminated\\$");
        for (int i = 1; i < newsCacheData.length; i++) {
            Articles articles = new Articles();
            String[] articlesData = newsCacheData[i].split("#terminated#");
            if (countryMark.equals(articlesData[0]) || countryMark.equals("*")) {
                articles.setTitle(articlesData[1]);
                Source source = new Source();
                source.setName(articlesData[2]);
                articles.setSource(source);
                articles.setPublishedAt(articlesData[3]);

                articlesList.add(articles);
            }


        }


        return articlesList;
    }
}
