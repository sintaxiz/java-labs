package com.company;

import java.io.OutputStream;

public interface StatisticWriter {
    void writeStatistic(OutputStream statisticStream, WordStatistic wordStatistic);
}
