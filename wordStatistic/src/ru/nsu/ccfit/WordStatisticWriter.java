package ru.nsu.ccfit;

import java.io.OutputStream;

public interface WordStatisticWriter {
    void writeStatistic(OutputStream statisticStream, WordStatistic wordStatistic);
}
