package ru.ccfit.nsu.kokunina;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ccfit.nsu.kokunina.configs.FactoryConfig;
import ru.ccfit.nsu.kokunina.configs.StationConfig;
import ru.ccfit.nsu.kokunina.configs.TrainConfig;
import ru.ccfit.nsu.kokunina.threads.Consumer;
import ru.ccfit.nsu.kokunina.threads.Factory;

import java.io.*;
import java.nio.file.Paths;
import java.util.ArrayList;


public class TransportCompany {
    final public static Logger log = LogManager.getLogger();

    private final ArrayList<TrainConfig> trainConfigs;
    private final ArrayList<FactoryConfig> factoryConfigs;
    private StationConfig stationConfig;
    private final ArrayList<Factory> factories;
    private final ArrayList<Consumer> consumers;

    private final Depot depot;
    private final Station departureStation;
    private final Station arrivalStation;

    public TransportCompany(File configFile) {
        // 1. Firstly read information about trains, products and factories, that will be created
        trainConfigs = new ArrayList<>();
        factoryConfigs = new ArrayList<>();
        factories = new ArrayList<>();
        consumers = new ArrayList<>();
        try {
            readConfig(configFile);
        } catch (IOException e) {
            log.error("Can not read config from file " + configFile, e);
        }

        // 2. Create factories and storages for each product
        arrivalStation = new Station(stationConfig.getArrivalPathCount());
        departureStation = new Station(stationConfig.getDeparturePathCount());
        initFactoriesAndConsumers();

        // 3. Create depot controlling stations and trains
        depot = new Depot(trainConfigs.size(), trainConfigs, departureStation, arrivalStation, stationConfig.getDistance());
    }


    public TransportCompany() {
        this(Paths.get("config.json").toFile());
    }

    public void startWork() {
        for (Factory factory : factories) {
            factory.start();
        }
        for (Consumer consumer : consumers) {
            consumer.start();
        }
        depot.start();
    }

    public void stopWork() {
        for (Factory factory : factories) {
            factory.interrupt();
            try {
                factory.join();
            } catch (InterruptedException e) {
                log.error("Thread executing stopWork was interrupted", e);
            }
        }
        for (Consumer consumer : consumers) {
            consumer.interrupt();
            try {
                consumer.join();
            } catch (InterruptedException e) {
                log.error("Thread executing stopWork was interrupted", e);
            }
        }
        depot.stop();
    }

    private void readConfig(File configFile) throws IOException {

        final ObjectMapper mapper = new ObjectMapper();
        var rootNode = mapper.readTree(configFile);
        JsonNode factoriesNode = rootNode.get("factories");
        if (factoriesNode.isArray()) {
            for (final JsonNode jsonNode : factoriesNode) {
                String factoryInfo = jsonNode.toString();
                FactoryConfig newConfig = mapper.readValue(factoryInfo, FactoryConfig.class);
                factoryConfigs.add(newConfig);
            }
        }
        JsonNode trainsNode = rootNode.get("trains");
        if (trainsNode.isArray()) {
            for (final JsonNode jsonNode : trainsNode) {
                String trainInfo = jsonNode.toString();
                TrainConfig newConfig = mapper.readValue(trainInfo, TrainConfig.class);
                trainConfigs.add(newConfig);
            }
        }
        JsonNode stationNode = rootNode.get("station");
        if (stationNode.isObject()) {
            stationConfig = mapper.readValue(stationNode.toString(), StationConfig.class);
        }
    }

    private void initFactoriesAndConsumers() {
        for (FactoryConfig factoryConfig : factoryConfigs) {
            String productName = factoryConfig.getProductName();
            Storage departureStorage = new Storage(factoryConfig.getCapacityDepartureStorage(), productName);
            Storage arrivalStorage = new Storage(factoryConfig.getCapacityArrivalStorage(), productName);
            departureStation.addStorage(productName, departureStorage);
            arrivalStation.addStorage(productName, arrivalStorage);
            factories.add(new Factory(productName, departureStorage, factoryConfig.getProduceTime()));
            consumers.add(new Consumer(arrivalStorage, factoryConfig.getConsumingTime()));
        }
    }
}
