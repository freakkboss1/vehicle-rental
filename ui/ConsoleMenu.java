package ui;

import exception.VehicleNotAvailableException;
import io.FileStorage;
import model.*;
import service.RentalService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class ConsoleMenu {
    private final RentalService service;
    private final FileStorage storage;
    private final Scanner sc = new Scanner(System.in);

    public ConsoleMenu(RentalService service, FileStorage storage) {
        this.service = service;
        this.storage = storage;
    }

    public void run() {
        try {
            storage.load(service);
        } catch (Exception e) {
            System.out.println("Не удалось загрузить данные: " + e.getMessage());
        }

        while (true) {
            printMenu();
            String cmd = sc.nextLine().trim();
            try {
                switch (cmd) {
                    case "1" -> showVehicles();
                    case "2" -> addCar();
                    case "3" -> addMotorcycle();
                    case "4" -> rent();
                    case "5" -> returnVehicle();
                    case "6" -> showRentals();
                    case "7" -> showRevenue();
                    case "8" -> showAvailable();
                    case "0" -> {
                        save();
                        System.out.println("Выход.");
                        return;
                    }
                    default -> System.out.println("Неизвестная команда");
                }
            } catch (VehicleNotAvailableException | IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            } finally {
                System.out.println();
            }
        }
    }

    private void printMenu() {
        System.out.println("""
                === Аренда транспортных средств ===
                1. Показать все ТС
                2. Добавить автомобиль
                3. Добавить мотоцикл
                4. Оформить аренду
                5. Вернуть ТС
                6. Показать аренды
                7. Показать общую выручку
                8. Показать доступные ТС
                0. Выход
                """);
        System.out.print("Выбор: ");
    }

    private void showVehicles() {
        List<Vehicle> list = service.getVehicles();
        if (list.isEmpty()) System.out.println("Список пуст");
        else list.forEach(System.out::println);
    }

    private void showAvailable() {
        List<Vehicle> avail = service.findAvailable();
        if (avail.isEmpty()) System.out.println("Нет доступных ТС");
        else avail.forEach(System.out::println);
    }

    private void addCar() {
        System.out.print("ID: "); String id = sc.nextLine();
        System.out.print("Марка: "); String b = sc.nextLine();
        System.out.print("Модель: "); String m = sc.nextLine();
        System.out.print("Ставка/сутки: "); double rate = Double.parseDouble(sc.nextLine());
        System.out.print("Мест: "); int seats = Integer.parseInt(sc.nextLine());
        service.addVehicle(new Car(id, b, m, rate, seats));
        System.out.println("Автомобиль добавлен");
    }

    private void addMotorcycle() {
        System.out.print("ID: "); String id = sc.nextLine();
        System.out.print("Марка: "); String b = sc.nextLine();
        System.out.print("Модель: "); String m = sc.nextLine();
        System.out.print("Ставка/сутки: "); double rate = Double.parseDouble(sc.nextLine());
        System.out.print("Объём двигателя: "); int cc = Integer.parseInt(sc.nextLine());
        service.addVehicle(new Motorcycle(id, b, m, rate, cc));
        System.out.println("Мотоцикл добавлен");
    }

    private void rent() throws VehicleNotAvailableException {
        System.out.print("ID ТС: "); String id = sc.nextLine();
        System.out.print("Дней: "); int days = Integer.parseInt(sc.nextLine());
        Rental r = service.rentVehicle(id, LocalDate.now(), days);
        System.out.println("Оформлено: " + r);
    }

    private void returnVehicle() throws VehicleNotAvailableException {
        System.out.print("ID ТС: "); String id = sc.nextLine();
        service.returnVehicle(id);
        System.out.println("ТС возвращено");
    }

    private void showRentals() {
        List<Rental> list = service.getRentals();
        if (list.isEmpty()) System.out.println("Аренд нет");
        else list.forEach(System.out::println);
    }

    private void showRevenue() {
        System.out.printf("Общая выручка: %.2f%n", service.totalRevenue());
    }

    private void save() {
        try {
            storage.save(service);
            System.out.println("Данные сохранены");
        } catch (IOException e) {
            System.out.println("Ошибка сохранения: " + e.getMessage());
        }
    }
}