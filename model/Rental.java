package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Rental implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String vehicleId;
    private final LocalDate startDate;
    private final int days;
    private final double totalCost;

    public Rental(String vehicleId, LocalDate startDate, int days, double totalCost) {
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.days = days;
        this.totalCost = totalCost;
    }

    public String getVehicleId() { return vehicleId; }
    public LocalDate getStartDate() { return startDate; }
    public int getDays() { return days; }
    public double getTotalCost() { return totalCost; }

    @Override
    public String toString() {
        return String.format("Аренда %s с %s на %d дн., стоимость %.2f",
                vehicleId, startDate, days, totalCost);
    }
}