package model;

public class Car extends Vehicle {
    private final int seats;

    public Car(String id, String brand, String model, double dailyRate, int seats) {
        super(id, brand, model, dailyRate);
        if (seats < 2) throw new IllegalArgumentException("Мест должно быть >= 2");
        this.seats = seats;
    }

    public int getSeats() { return seats; }

    @Override
    public String getType() { return "Автомобиль"; }

    @Override
    public double calculateRentCost(int days) {
        return super.calculateRentCost(days) * 1.10; // +10% страховка
    }
}