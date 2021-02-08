package com.company;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FileStatisticWriter implements StatisticWriter {
    @Override
    public void writeStatistic(OutputStream statisticStream, WordStatistic wordStatistic) {
        LinkedHashMap<String, Integer> sortedMap = descendingSort(wordStatistic.getWords());

        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(statisticStream);
        try {
            for (Map.Entry<String, Integer> item : sortedMap.entrySet()) {
                outputStreamWriter.write(item.getKey());
                outputStreamWriter.write(",");
                outputStreamWriter.write(item.getValue().toString());
                outputStreamWriter.write(",");
                double percent =  (item.getValue().doubleValue() / wordStatistic.getWordCounter()) * 100;
                outputStreamWriter.write(percent + "%");
                outputStreamWriter.write(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStreamWriter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private LinkedHashMap<String, Integer> descendingSort(HashMap<String, Integer> inputMap) {
        return inputMap.entrySet()
                .stream() // getting a sequential stream to:
                // 1. Sort map by value in descending order
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                // 2. Create new map, but a linked one because order is important
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }
}
