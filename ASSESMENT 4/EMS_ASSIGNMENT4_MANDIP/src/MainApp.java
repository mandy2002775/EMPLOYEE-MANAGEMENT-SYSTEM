import service.EmployeeManager;
import ui.ConsoleInterface;
import ui.GUIInterface;

import javax.swing.*;

public class MainApp {
    public static void main(String[] args) {
        EmployeeManager manager = new EmployeeManager();

        String[] options = {"Console Interface", "GUI Interface"};
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose your preferred interface:",
                "EMS Startup",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );

        if (choice == 0) {
            ConsoleInterface console = new ConsoleInterface(manager);
            console.start();
        } else if (choice == 1) {
            SwingUtilities.invokeLater(() -> new GUIInterface(manager));
        } else {
            System.out.println("No selection made. Exiting.");
        }
    }
}
