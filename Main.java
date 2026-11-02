import io.FileStorage;
import service.RentalService;
import ui.ConsoleMenu;

public class Main {
    public static void main(String[] args) {
        RentalService service = new RentalService();
        FileStorage storage = new FileStorage("data/vehicles.dat");
        new ConsoleMenu(service, storage).run();
    }
}