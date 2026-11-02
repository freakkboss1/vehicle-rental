package model;

import java.io.Serializable;
import java.util.Objects;

public abstract class Vehicle implements Rentable, Serializable {
    private static final long serialVersionUID = 1L;

    private final String id;
    private final String brand;
    private final String model;
    private double dailyRate;
    private boolean available;

    protected Vehicle(String id, String brand, String model, double dailyRate) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID не может быть пустым");
        if (dailyRate <= 0) throw new IllegalArgumentException("Ставка должна быть > 0");
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.dailyRate = dailyRate;
        this.available = true;
    }

    public String getId() { return id; }
    public String getBrand() { return brand; }
    public String getModel() { return model; }
    public double getDailyRate() { return dailyRate; }

    public void setDailyRate(double rate) {
        if (rate <= 0) throw new IllegalArgumentException("Ставка должна быть > 0");
        this.dailyRate = rate;
    }

    @Override
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    public abstract String getType();

    @Override
    public double calculateRentCost(int days) {
        if (days <= 0) throw new IllegalArgumentException("Количество дней должно быть > 0");
        return dailyRate * days;
    }

    // Перегрузка метода
    public double calculateRentCost(int days, double extraFee) {
        return calculateRentCost(days) + extraFee;
    }

    @Override
    public String toString() {
        return String.format("[%s] %s %s (%s) — %.2f/сутки, %s",
                id, brand, model, getType(), dailyRate,
                available ? "доступен" : "арендован");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vehicle v)) return false;
        return id.equals(v.id);
    }

    @Override
    public int hashCode() { return Objects.hash(id); }
}