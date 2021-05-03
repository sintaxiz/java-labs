package ru.ccfit.nsu.kokunina.threads;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import ru.ccfit.nsu.kokunina.Depot;
import ru.ccfit.nsu.kokunina.Product;
import ru.ccfit.nsu.kokunina.Station;
import ru.ccfit.nsu.kokunina.configs.TrainConfig;

import java.util.ArrayList;

public class Train extends Thread {

    private final Depot depot;
    private final TrainConfig config;
    private final String productName;
    private final Station departureStation;
    private final Station arrivalStation;
    private final int capacity;
    private final int velocity;
    private final int depreciation;
    private final double stationDistance;

    ArrayList<Product> products;

    public Train(Depot depot, TrainConfig trainConfig,
                 Station departureStation, Station arrivalStation,
                 double stationDistance) {
        this.depot = depot;
        this.config = trainConfig;

        this.productName = trainConfig.getProductName();
        this.capacity = trainConfig.getCapacity();
        this.velocity = trainConfig.getVelocity();
        this.depreciation = trainConfig.getDepreciation();

        this.departureStation = departureStation;
        this.arrivalStation = arrivalStation;

        this.stationDistance = stationDistance;

        products = new ArrayList<>();
    }

    private static final Logger log = LogManager.getLogger();

    @Override
    public void run() {
        long startTime = System.currentTimeMillis();
        while (!interrupted()) {
            try {
                long timeAlive = System.currentTimeMillis() - startTime;
                if (timeAlive > depreciation) {
                    depreciate();
                    log.debug(this.toString() + " ended up with work. Working " + timeAlive + "ms");
                    return;
                }
                loadProducts();
                deliverProducts();
                unloadProducts();
            } catch (InterruptedException e) {
                log.debug(this.toString() + " was interrupted.");
                interrupt();
            }
        }
    }

    private void loadProducts() throws InterruptedException {
        departureStation.takePath();
        log.info(this.toString() + " started loading products.");
        for (int i = 0; i < capacity; i++) {
            Product product;
            product = departureStation.getStorage(productName).getProduct();
            log.info(this.toString() + " loaded product: " + product.toString());
            products.add(product);
        }
        departureStation.freePath();
    }

    private void deliverProducts() throws InterruptedException {
        double timeToDeliver = (stationDistance / velocity) * 1000;
        Thread.sleep((long) timeToDeliver);
        log.info(this.toString() + " finished delivering products during " + timeToDeliver + "ms");
    }

    private void unloadProducts() throws InterruptedException {
        arrivalStation.takePath();
        log.info(this.toString() + " started unloading products.");
        for (Product product : products) {
            arrivalStation.getStorage(productName).addProduct(product);
            log.info(this.toString() + " unloaded " + product.toString());
        }
        arrivalStation.freePath();
    }

    private void depreciate() throws InterruptedException {
        log.info(this.toString() + " started utilizing.");
        depot.disposeTrain(this);
    }

    @Override
    public String toString() {
        return productName + "Train#" + hashCode();
    }

    public TrainConfig getConfig() {
        return config;
    }
}
