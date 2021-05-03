package ru.ccfit.nsu.kokunina;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ccfit.nsu.kokunina.configs.TrainConfig;
import ru.ccfit.nsu.kokunina.threads.Train;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Depot {

    ThreadPoolExecutor executor;
    private static final int THREAD_POOL_SIZE = 5;
    ConcurrentLinkedQueue<Train> trains = new ConcurrentLinkedQueue<>();

    private final ArrayList<TrainConfig> trainConfigs;
    private final ArrayList<TrainConfig> aliveTrains;
    private final Station departureStation;
    private final Station arrivalStation;
    private final int distanceBetweenStations;

    boolean interrupted = false;

    private static final Logger log = LogManager.getLogger();

    // executor service
    public Depot(int maxTrainsCount, ArrayList<TrainConfig> trainConfigs, Station departureStation, Station arrivalStations,
                 int distanceBetweenStations) {
        this.trainConfigs = trainConfigs;
        this.departureStation = departureStation;
        this.arrivalStation = arrivalStations;
        this.distanceBetweenStations = distanceBetweenStations;
        aliveTrains = new ArrayList<>();
    }

    /***
     * Depot creates thread pool and starting process trains using new threads.
     */
    public synchronized void start() {
        log.info("Starting creating new trains and start them.");
        executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        processTrains();
    }

    public synchronized void stop() {
        log.info("Depot starting utilizing trains...");
        interrupted = true;
        executor.shutdownNow();
        ArrayList<Train> trainsToJoin = new ArrayList<>();
        for (Train train : trains) {
            disposeTrain(train);
            trainsToJoin.add(train);
        }
        for (Train train : trainsToJoin) {
            try {
                train.join();
            } catch (InterruptedException e) {
                log.error("main thread was interrupted", e);
            }
        }
        log.info("Depot was successfully stopped.");
    }

    private synchronized void addTrain(Train newTrain) {
        if (interrupted) {
            return;
        }
        trains.add(newTrain);
        newTrain.start();
        log.info("Train " + newTrain + " was added and started.");
    }

    public synchronized void disposeTrain(Train train) {
        train.interrupt();
        if (aliveTrains.remove(train.getConfig())) {
            log.debug("Train " + train + " removed from alive trains");
        } else {
            log.error("Can not remove config for this train (Maybe forgot to add it previously?)");
        }
        trains.remove(train);
        processTrains();
    }

    private synchronized void processTrains() {
        if (interrupted) {
            return;
        }
        for (TrainConfig trainConfig : trainConfigs) {
            if (!aliveTrains.contains(trainConfig)) {
                executor.execute(() -> {                // TrainCreator
                    Train newTrain = new Train(this, trainConfig, departureStation, arrivalStation, distanceBetweenStations);
                    addTrain(newTrain);
                });
                aliveTrains.add(trainConfig);
            }
        }
    }
}
