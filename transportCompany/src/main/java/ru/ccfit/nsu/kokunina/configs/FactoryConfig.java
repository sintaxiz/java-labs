package ru.ccfit.nsu.kokunina.configs;

public class FactoryConfig {
    private String productName;

    private int capacityDepartureStorage;
    private int capacityArrivalStorage;

    private int produceTime;
    private int consumingTime;

    private int loadTime;
    private int unloadTime;

    public FactoryConfig() {
    }

    public FactoryConfig(String productName, int capacityDepartureStorage, int capacityArrivalStorage,
                         int produceTime, int consumingTime, int loadTime, int unloadTime) {
        this.productName = productName;
        this.capacityDepartureStorage = capacityDepartureStorage;
        this.capacityArrivalStorage = capacityArrivalStorage;
        this.produceTime = produceTime;
        this.consumingTime = consumingTime;
        this.loadTime = loadTime;
        this.unloadTime = unloadTime;
    }

    public String getProductName() {
        return productName;
    }

    public int getCapacityDepartureStorage() {
        return capacityDepartureStorage;
    }

    public int getCapacityArrivalStorage() {
        return capacityArrivalStorage;
    }

    public int getProduceTime() {
        return produceTime;
    }

    public int getConsumingTime() {
        return consumingTime;
    }

    public int getLoadTime() {
        return loadTime;
    }

    public int getUnloadTime() {
        return unloadTime;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCapacityDepartureStorage(int capacityDepartureStorage) {
        this.capacityDepartureStorage = capacityDepartureStorage;
    }

    public void setCapacityArrivalStorage(int capacityArrivalStorage) {
        this.capacityArrivalStorage = capacityArrivalStorage;
    }

    public void setProduceTime(int produceTime) {
        this.produceTime = produceTime;
    }

    public void setConsumingTime(int consumingTime) {
        this.consumingTime = consumingTime;
    }

    public void setLoadTime(int loadTime) {
        this.loadTime = loadTime;
    }

    public void setUnloadTime(int unloadTime) {
        this.unloadTime = unloadTime;
    }
}
