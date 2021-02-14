import ru.nsu.ccfit.StatisticHandler;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class Main {

    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Enter file(s) path");
            return;
        }

        StatisticHandler fileStat = new StatisticHandler();

        for (int i = 0; i < args.length; i++) {
            try {
                String filename = args[i];
                fileStat.addData(new FileInputStream(filename));
            } catch (FileNotFoundException exception) {
                System.out.println("No such file #" + i);
                exception.printStackTrace();
            }
        }

        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("output.csv");
        } catch (FileNotFoundException exception) {
            exception.printStackTrace();
        }
        fileStat.toCsv(fileOutputStream);
    }
}