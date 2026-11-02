package service;

import exception.VehicleNotAvailableException;
import model.Rental;
import model.Vehicle;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class RentalService {
    private final Repository<Vehicle> vehicles = new Repository<>();
    private final Repository<Rental> rentals = new Repository<>();

    public void addVehicle(Vehicle v) { vehicles.add(v); }
    public List<Vehicle> getVehicles() { return vehicles.getAll(); }

    public List<Vehicle> findAvailable() {
        return vehicles.filter(Vehicle::isAvailable);
    }

    public Optional<Vehicle> findById(String id) {
        return vehicles.findFirst(v -> v.getId().equals(id));
    }

    public boolean removeVehicle(String id) {
        Optional<Vehicle> v = findById(id);
        return v.map(vehicles::remove).orElse(false);
    }

    public Rental rentVehicle(String vehicleId, LocalDate start, int days)
            throws VehicleNotAvailableException {
        Vehicle v = findById(vehicleId)
                .orElseThrow(() -> new VehicleNotAvailableException("ТС с ID " + vehicleId + " не найдено"));

        if (!v.isAvailable()) {
            throw new VehicleNotAvailableException("ТС " + vehicleId + " уже арендовано");
        }

        double cost = v.calculateRentCost(days);
        v.setAvailable(false);
        Rental r = new Rental(vehicleId, start, days, cost);
        rentals.add(r);
        return r;
    }

    public void returnVehicle(String vehicleId) throws VehicleNotAvailableException {
        Vehicle v = findById(vehicleId)
                .orElseThrow(() -> new VehicleNotAvailableException("ТС не найдено"));
        if (v.isAvailable()) {
            throw new VehicleNotAvailableException("ТС " + vehicleId + " и так свободно");
        }
        v.setAvailable(true);
    }

    public List<Rental> getRentals() { return rentals.getAll(); }

    public double totalRevenue() {
        return rentals.getAll().stream()
                .mapToDouble(Rental::getTotalCost)
                .sum();
    }
}