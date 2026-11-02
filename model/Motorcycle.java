package model;

public class Motorcycle extends Vehicle {
    private final int engineVolume;

    public Motorcycle(String id, String brand, String model, double dailyRate, int engineVolume) {
        super(id, brand, model, dailyRate);
        if (engineVolume <= 0) throw new IllegalArgumentException("Объём двигателя должен быть > 0");
        this.engineVolume = engineVolume;
    }

    public int getEngineVolume() { return engineVolume; }

    @Override
    public String getType() { return "Мотоцикл"; }

    @Override
    public double calculateRentCost(int days) {
        return super.calculateRentCost(days) * 1.05; // +5% экипировка
    }
}