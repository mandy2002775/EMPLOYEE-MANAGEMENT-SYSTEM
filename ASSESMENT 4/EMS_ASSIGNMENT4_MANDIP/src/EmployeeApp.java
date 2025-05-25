import java.util.Scanner;
import service.EmployeeManager;
import ui.ConsoleInterface;
import ui.GUIInterface;

public class EmployeeApp {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
        EmployeeManager manager = new EmployeeManager();

        System.out.println("Choose Interface:");
        System.out.println("1. Text-Based Interface (TBI)");
        System.out.println("2. Graphical User Interface (GUI)");

        int choice = scanner.nextInt();

        if (choice == 1) {
            ConsoleInterface console = new ConsoleInterface(manager);
            console.start();
        } else {
            new GUIInterface(manager); // ✅ Pass manager to GUI
        }
    }
}
}