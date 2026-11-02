package io;

import model.Vehicle;
import service.RentalService;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class FileStorage {
    private final Path dataFile;

    public FileStorage(String path) {
        this.dataFile = Path.of(path);
    }

    public void save(RentalService service) throws IOException {
        Files.createDirectories(dataFile.getParent());

        Path vehiclesFile = dataFile.resolveSibling("vehicles.dat");
        Path rentalsFile = dataFile.resolveSibling("rentals.dat");

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(vehiclesFile.toFile()))) {
            oos.writeObject(service.getVehicles());
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(rentalsFile.toFile()))) {
            oos.writeObject(service.getRentals());
        }
    }

    @SuppressWarnings("unchecked")
    public void load(RentalService service) throws IOException, ClassNotFoundException {
        Path vehiclesFile = dataFile.resolveSibling("vehicles.dat");

        if (Files.exists(vehiclesFile)) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(vehiclesFile.toFile()))) {
                List<Vehicle> list = (List<Vehicle>) ois.readObject();
                list.forEach(service::addVehicle);
            }
        }
    }
}