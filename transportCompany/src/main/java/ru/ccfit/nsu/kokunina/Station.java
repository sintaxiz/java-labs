package ru.ccfit.nsu.kokunina;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;

public class Station {

    public static final Logger log = LogManager.getLogger();

    private int trainPaths = 0;
    private int maxTrainPaths;
    private HashMap<String, Storage> storages;

    public Station(int maxTrainPaths) {
        this.maxTrainPaths = maxTrainPaths;
        this.storages = new HashMap<>();
    }

    public void addStorage(String productName, Storage storage) {
        storages.put(productName, storage);
    }

    // synchronized because only one train can take path
    public synchronized void takePath() throws InterruptedException {
        if (maxTrainPaths == trainPaths) { // if there is no free paths
            this.wait();
        }
        trainPaths++;
    }

    public synchronized void freePath() {
        trainPaths--;
    }

    public synchronized Storage getStorage(String productName) {
        return storages.get(productName);
    }
}
