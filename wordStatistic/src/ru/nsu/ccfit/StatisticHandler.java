package ru.nsu.ccfit;

import java.io.InputStream;
import java.io.FileOutputStream;

public class StatisticHandler {

    private WordStatistic wordStatistic;

    private final WordStatisticReader wordCounter;
    private final WordStatisticWriter statisticWriter;

    public StatisticHandler() {
        wordCounter = new FileWordStatisticReader();  // default reader&writer from file
        statisticWriter = new CsvWordStatisticWriter();
        wordStatistic = new WordStatistic();
    }

    public StatisticHandler(WordStatisticReader wordCounter, WordStatisticWriter statisticWriter) {
        this.wordCounter = wordCounter;  // User's reader&writer
        this.statisticWriter = statisticWriter;
    }

    public void calcStatistic(InputStream inputStream) {
        wordCounter.getStatistic(inputStream, wordStatistic);
    }

    public void toCsv(FileOutputStream outputCsv) {
        statisticWriter.writeStatistic(outputCsv, wordStatistic);
    }

    public void addData(InputStream inputStream) {
        calcStatistic(inputStream);
    }
}
