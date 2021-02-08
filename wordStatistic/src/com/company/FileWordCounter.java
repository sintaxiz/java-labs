package com.company;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

public class FileWordCounter implements WordStatisticReader {
    @Override
    public WordStatistic getStatistic(InputStream inputStream) {
        HashMap<String, Integer> words = new HashMap<>();
        int wordCounter = 0;
        Reader reader = null;
        try {
            reader = new InputStreamReader(inputStream);
            StringBuilder wordBuild = new StringBuilder();
            int readChar;
            while ((readChar = reader.read()) > 0) {
                if (Character.isLetterOrDigit((char) readChar)) {
                    wordBuild.append((char) readChar);
                } else if (wordBuild.length() > 0) {
                    String word = wordBuild.toString();
                    if (words.containsKey(word)) {
                        words.replace(word, words.get(word) + 1);
                    } else {
                        words.put(word, 1);
                    }
                    wordBuild = new StringBuilder();
                    wordCounter++;
                }
            }
            if (wordBuild.length() > 0) {
                String word = wordBuild.toString();
                if (words.containsKey(word)) {
                    words.replace(word, words.get(word) + 1);
                } else {
                    words.put(word, 1);
                }
            }
        } catch (IOException e) {
            System.err.println("Error while reading file: " + e.getLocalizedMessage());
        } finally {
            if (null != reader) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace(System.err);
                }
            }
        }
        return new WordStatistic(words, wordCounter);
    }
}

