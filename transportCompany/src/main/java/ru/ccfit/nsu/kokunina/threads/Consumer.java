package ru.ccfit.nsu.kokunina.threads;

import ru.ccfit.nsu.kokunina.Product;
import ru.ccfit.nsu.kokunina.Storage;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class Consumer extends Thread {

    final Storage storage;
    final int consumingTime;

    private static final Logger log = LogManager.getLogger();

    public Consumer(Storage storage, int consumingTime) {
        this.storage = storage;
        this.consumingTime = consumingTime;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                Product product = storage.getProduct();
                sleep(consumingTime);
                log.info(this.toString() + " got a new product " + product.toString());
            } catch (InterruptedException e) {
                log.debug("Consumer was interrupted.");
                break;
            }
        }
    }

    @Override
    public String toString() {
        return "Consumer#" + hashCode();
    }
}
