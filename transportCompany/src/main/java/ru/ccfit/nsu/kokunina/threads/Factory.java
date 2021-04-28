package ru.ccfit.nsu.kokunina.threads;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.ccfit.nsu.kokunina.Product;
import ru.ccfit.nsu.kokunina.Storage;

public class Factory extends Thread {

    private static final Logger log = LogManager.getLogger();
    final Storage storage;
    final private int produceTime;
    final private String productName;

    public Factory(String productName, Storage storage, int produceTime) {
        this.produceTime = produceTime;
        this.storage = storage;
        this.productName = productName;
    }

    /***
     * Produce a new product.
     */
    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Product newProduct = new Product(productName);
                sleep(produceTime);
                log.info(newProduct + " was produced on " + this);

                storage.addProduct(newProduct);
            } catch (InterruptedException e) {
                log.debug(this.toString() + " was interrupted.");
                break;
            }
        }
    }

    @Override
    public String toString() {
        return productName + "Factory#" + hashCode();
    }
}
