package com.company;

import java.util.HashMap;

public class WordStatistic {
    private final HashMap<String, Integer> words;
    private final Integer wordCounter;

    WordStatistic(HashMap<String, Integer> words, int wordCounter) {
        this.words = words;
        this.wordCounter = wordCounter;
    }
    public HashMap<String, Integer> getWords() {
        return words;
    }
    public Integer getWordCounter() {
        return wordCounter;
    }
}
