package model;

public interface Rentable {
    double calculateRentCost(int days);
    boolean isAvailable();
}