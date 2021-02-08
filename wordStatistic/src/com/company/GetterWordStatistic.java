package com.company;

import java.io.*;

public class GetterWordStatistic {

    private final InputStream inputStream;
    private WordStatistic wordStatistic;

    private final WordStatisticReader wordCounter;
    private final StatisticWriter statisticWriter;

    public GetterWordStatistic(InputStream inputStream) {
        this.inputStream = inputStream;
        wordCounter = new FileWordCounter();  // default reader&writer from file
        statisticWriter = new FileStatisticWriter();
    }

    public GetterWordStatistic(InputStream inputStream, WordStatisticReader wordCounter, StatisticWriter statisticWriter) {
        this.inputStream = inputStream;
        this.wordCounter = wordCounter;  // User's reader&writer
        this.statisticWriter = statisticWriter;
    }

    public void calcStatistic() {
        wordStatistic = wordCounter.getStatistic(inputStream);
    }

    public void toCsv(FileOutputStream outputCsv) {
        statisticWriter.writeStatistic(outputCsv, wordStatistic);
    }

}
