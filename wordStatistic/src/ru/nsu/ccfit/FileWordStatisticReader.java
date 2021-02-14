package ru.nsu.ccfit;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;


public class FileWordStatisticReader implements WordStatisticReader {
    @Override
    public void getStatistic(InputStream inputStream, WordStatistic wordStatistic) {
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
                    wordStatistic.addWord(word);
                    wordBuild = new StringBuilder();
                }
            }
            if (wordBuild.length() > 0) {
                String word = wordBuild.toString();
                wordStatistic.addWord(word);
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
    }
}

