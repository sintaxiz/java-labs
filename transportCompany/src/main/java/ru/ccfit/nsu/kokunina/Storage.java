package ru.ccfit.nsu.kokunina;


import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Storage {

    private static final Logger log = LogManager.getLogger();

    private final int capacity;
    private final String productName;

    private final ArrayList<Product> products;

    public Storage(int capacity, String productName) {
        this.capacity = capacity;
        this.productName = productName;
        products = new ArrayList<>();
    }

    public synchronized void addProduct(Product product) throws InterruptedException {
        while (products.size() >= capacity) {
            this.wait();
        }
        products.add(product);
        log.info("Added " + product + " to " + this);
        this.notifyAll(); // Somebody can except product
    }

    public synchronized Product getProduct() throws InterruptedException {
        while (products.size() <= 0) {
            wait();
        }
        Product product = products.remove(0);
        log.info("Remove " + product + " from " + this);
        notifyAll();
        return product;
    }

    @Override
    public String toString() {
        return productName + "Storage#" + hashCode();
    }
}
