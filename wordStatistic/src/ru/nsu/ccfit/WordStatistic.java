package ru.nsu.ccfit;

import java.util.HashMap;

public class WordStatistic {
    private final HashMap<String, Integer> words;
    private Integer wordCounter;

    WordStatistic() {
        this.words = new HashMap<>();
        wordCounter = 0;
    }

    public HashMap<String, Integer> getWords() {
        return words;
    }
    public Integer getWordCounter() {
        return wordCounter;
    }
    public void addWord(String word) {
        if (words.containsKey(word)) {
            words.replace(word, words.get(word) + 1);
        } else {
            words.put(word, 1);
        }
        wordCounter++;
    }
}
