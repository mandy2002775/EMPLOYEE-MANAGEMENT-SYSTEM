package ui;

import model.Employee;
import model.Intern;
import model.Manager;
import model.RegularEmployee;
import service.EmployeeManager;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

public class GUIInterface {
    private final EmployeeManager manager;
    private final JFrame frame;
    private final JTable table;
    private final DefaultTableModel tableModel;

    public GUIInterface(EmployeeManager manager) {
        this.manager = manager;
        this.frame = new JFrame("Employee Management System");
        this.tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Salary", "Type"}, 0);
        this.table = new JTable(tableModel);
        initializeGUI();
    }

    private void initializeGUI() {
        frame.setSize(800, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Top Panel
        JPanel topPanel = new JPanel();
        JButton addBtn = new JButton("Add");
        JButton updateBtn = new JButton("Update");
        JButton refreshBtn = new JButton("Refresh");
        JComboBox<String> sortCombo = new JComboBox<>(new String[]{
                "Sort By Name Asc", "Sort By Name Desc",
                "Sort By Salary Asc", "Sort By Salary Desc"
        });

        topPanel.add(addBtn);
        topPanel.add(updateBtn);
        topPanel.add(sortCombo);
        topPanel.add(refreshBtn);
        frame.add(topPanel, BorderLayout.NORTH);

        // Table Panel
        frame.add(new JScrollPane(table), BorderLayout.CENTER);

        // Event Handlers
        addBtn.addActionListener(e -> showAddDialog());
        updateBtn.addActionListener(e -> showUpdateDialog());
        refreshBtn.addActionListener(e -> refreshTable());

        sortCombo.addActionListener((ActionEvent e) -> {
            int selected = sortCombo.getSelectedIndex();
            switch (selected) {
                case 0 -> manager.sortByNameAsc();
                case 1 -> manager.sortByNameDesc();
                case 2 -> manager.sortBySalaryAsc();
                case 3 -> manager.sortBySalaryDesc();
            }
            refreshTable();
        });

        refreshTable();
        frame.setVisible(true);
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Employee> employees = manager.getAllEmployees();
        for (Employee e : employees) {
            tableModel.addRow(new Object[]{e.getId(), e.getName(), e.getSalary(), e.getClass().getSimpleName()});
        }
    }

    private void showAddDialog() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField salaryField = new JTextField();
        JComboBox<String> typeBox = new JComboBox<>(new String[]{"Manager", "Intern", "RegularEmployee"});

        Object[] fields = {
                "ID:", idField,
                "Name:", nameField,
                "Salary:", salaryField,
                "Type:", typeBox
        };

        int result = JOptionPane.showConfirmDialog(frame, fields, "Add Employee", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int id = Integer.parseInt(idField.getText().trim());
                String name = nameField.getText().trim();
                double salary = Double.parseDouble(salaryField.getText().trim());
                String type = (String) typeBox.getSelectedItem();

                Employee e = switch (type) {
                    case "Manager" -> new Manager(id, name, salary);
                    case "Intern" -> new Intern(id, name, salary);
                    default -> new RegularEmployee(id, name, salary);
                };

                manager.addEmployee(e);
                refreshTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid ID or salary input.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showUpdateDialog() {
        String input = JOptionPane.showInputDialog(frame, "Enter ID to update:");
        if (input == null || input.isEmpty()) return;

        try {
            int id = Integer.parseInt(input);
            Employee e = manager.getEmployeeById(id);

            if (e == null) {
                JOptionPane.showMessageDialog(frame, "Employee not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            JTextField nameField = new JTextField(e.getName());
            JTextField salaryField = new JTextField(String.valueOf(e.getSalary()));

            Object[] fields = {
                    "Name:", nameField,
                    "Salary:", salaryField
            };

            int option = JOptionPane.showConfirmDialog(frame, fields, "Update Employee", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String newName = nameField.getText().trim();
                double newSalary = Double.parseDouble(salaryField.getText().trim());
                manager.updateEmployee(id, newName, newSalary);
                refreshTable();
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid ID or salary input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
