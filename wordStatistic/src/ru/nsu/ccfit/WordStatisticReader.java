package ru.nsu.ccfit;

import java.io.InputStream;

public interface WordStatisticReader {
    void getStatistic(InputStream inputStream, WordStatistic wordStatistic);
}