package com.company;

import java.io.InputStream;

public interface WordStatisticReader {
    WordStatistic getStatistic(InputStream inputStream);
}