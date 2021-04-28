package ru.ccfit.nsu.kokunina;

public class Product {

    private final String productName;
    public Product(String productName) {
        this.productName = productName;
    }

    @Override
    public String toString() {
        return productName + "Product#" + hashCode();
    }
}
