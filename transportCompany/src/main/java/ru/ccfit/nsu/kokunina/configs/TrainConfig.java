package ru.ccfit.nsu.kokunina.configs;

public class TrainConfig {

    private String productName;
    private int capacity;
    private int velocity;
    private int depreciation;

    public TrainConfig() {
    }

    public TrainConfig(String productName, int capacity, int velocity, int depreciation) {
        this.productName = productName;
        this.capacity = capacity;
        this.velocity = velocity;
        this.depreciation = depreciation;
    }

    public String getProductName() {
        return productName;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getVelocity() {
        return velocity;
    }

    public int getDepreciation() {
        return depreciation;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public void setDepreciation(int depreciation) {
        this.depreciation = depreciation;
    }
}
