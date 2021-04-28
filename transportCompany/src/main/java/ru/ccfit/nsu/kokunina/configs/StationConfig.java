package ru.ccfit.nsu.kokunina.configs;

public class StationConfig {

    private int distance;
    private int departurePathCount;
    private int arrivalPathCount;
    private int fromDepartureToArrivalPathCount;
    private int fromArrivalToDeparturePathCount;

    public StationConfig() {
    }

    public StationConfig(int distance, int departurePathCount,
                         int arrivalPathCount, int fromDepartureToArrivalPathCount,
                         int fromArrivalToDeparturePathCount) {
        this.distance = distance;
        this.departurePathCount = departurePathCount;
        this.arrivalPathCount = arrivalPathCount;
        this.fromDepartureToArrivalPathCount = fromDepartureToArrivalPathCount;
        this.fromArrivalToDeparturePathCount = fromArrivalToDeparturePathCount;
    }

    public int getDistance() {
        return distance;
    }

    public int getDeparturePathCount() {
        return departurePathCount;
    }

    public int getArrivalPathCount() {
        return arrivalPathCount;
    }

    public int getFromDepartureToArrivalPathCount() {
        return fromDepartureToArrivalPathCount;
    }

    public int getFromArrivalToDeparturePathCount() {
        return fromArrivalToDeparturePathCount;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setDeparturePathCount(int departurePathCount) {
        this.departurePathCount = departurePathCount;
    }

    public void setArrivalPathCount(int arrivalPathCount) {
        this.arrivalPathCount = arrivalPathCount;
    }

    public void setFromDepartureToArrivalPathCount(int fromDepartureToArrivalPathCount) {
        this.fromDepartureToArrivalPathCount = fromDepartureToArrivalPathCount;
    }

    public void setFromArrivalToDeparturePathCount(int fromArrivalToDeparturePathCount) {
        this.fromArrivalToDeparturePathCount = fromArrivalToDeparturePathCount;
    }
}
