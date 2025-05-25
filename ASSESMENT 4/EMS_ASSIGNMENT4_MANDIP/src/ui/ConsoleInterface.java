package ui;

import model.Employee;
import model.Intern;
import model.Manager;
import model.RegularEmployee;
import service.EmployeeManager;

import java.util.List;
import java.util.Scanner;

public class ConsoleInterface {
    private final EmployeeManager manager;
    private final Scanner scanner;

    public ConsoleInterface(EmployeeManager manager) {
        this.manager = manager;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        int choice;
        do {
            System.out.println("\n--- Employee Management System ---");
            System.out.println("1. Add Employee");
            System.out.println("2. Delete Employee by ID");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Display All Employees");
            System.out.println("5. Sort Employees");
            System.out.println("6. Update Employee");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            try {
                choice = Integer.parseInt(scanner.nextLine());

                switch (choice) {
                    case 1 -> addEmployee();
                    case 2 -> deleteEmployee();
                    case 3 -> searchEmployee();
                    case 4 -> displayEmployees();
                    case 5 -> sortEmployees();
                    case 6 -> updateEmployee();
                    case 7 -> System.out.println("Exiting...");
                    default -> System.out.println("Invalid choice. Try again.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid numeric choice.");
                choice = -1;
            }
        } while (choice != 7);
    }

    private void addEmployee() {
        try {
            System.out.println("Enter type: 1.Manager 2.Intern 3.Regular");
            int type = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter Name: ");
            String name = scanner.nextLine();
            System.out.print("Enter Salary: ");
            double salary = Double.parseDouble(scanner.nextLine());

            Employee emp;
            switch (type) {
                case 1 -> emp = new Manager(id, name, salary);
                case 2 -> emp = new Intern(id, name, salary);
                case 3 -> emp = new RegularEmployee(id, name, salary);
                default -> {
                    System.out.println("Invalid employee type.");
                    return;
                }
            }

            manager.addEmployee(emp);
            System.out.println("Employee added!");

        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter numeric values where required.");
        }
    }

    private void deleteEmployee() {
        try {
            System.out.print("Enter ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine());
            // Corrected method name here to deleteEmployee
            if (manager.deleteEmployee(id)) {
                System.out.println("Deleted successfully.");
            } else {
                System.out.println("Employee not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    private void searchEmployee() {
        try {
            System.out.print("Enter ID to search: ");
            int id = Integer.parseInt(scanner.nextLine());
            Employee e = manager.getEmployeeById(id);
            if (e != null) {
                System.out.println(e);
            } else {
                System.out.println("Not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID format.");
        }
    }

    private void displayEmployees() {
        List<Employee> list = manager.getAllEmployees();
        if (list.isEmpty()) {
            System.out.println("No employees.");
            return;
        }
        for (Employee e : list) {
            System.out.println(e);
        }
    }

    private void sortEmployees() {
        System.out.println("Sort by: 1.Name Asc 2.Name Desc 3.Salary Asc 4.Salary Desc");
        try {
            int option = Integer.parseInt(scanner.nextLine());
            switch (option) {
                case 1 -> manager.sortByNameAsc();
                case 2 -> manager.sortByNameDesc();
                case 3 -> manager.sortBySalaryAsc();
                case 4 -> manager.sortBySalaryDesc();
                default -> {
                    System.out.println("Invalid sort option.");
                    return;
                }
            }
            System.out.println("Sorted successfully.");
            displayEmployees();
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
        }
    }

    private void updateEmployee() {
        try {
            System.out.print("Enter ID of employee to update: ");
            int id = Integer.parseInt(scanner.nextLine());

            Employee e = manager.getEmployeeById(id);
            if (e == null) {
                System.out.println("Employee not found.");
                return;
            }

            System.out.print("Enter new name: ");
            String name = scanner.nextLine();
            System.out.print("Enter new salary: ");
            double salary = Double.parseDouble(scanner.nextLine());

            if (manager.updateEmployee(id, name, salary)) {
                System.out.println("Employee updated.");
            } else {
                System.out.println("Update failed.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter correct data types.");
        }
    }
}
