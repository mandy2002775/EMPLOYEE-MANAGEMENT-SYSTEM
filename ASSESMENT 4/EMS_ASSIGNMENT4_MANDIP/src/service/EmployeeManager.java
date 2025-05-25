package service;

import model.Employee;

import java.io.*;
import java.util.*;

public class EmployeeManager {
    private List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee e) {
        employees.add(e);
    }

    public boolean removeEmployeeById(int id) {
        return employees.removeIf(e -> e.getId() == id);
    }

    public Employee getEmployeeById(int id) {
        for (Employee e : employees) {
            if (e.getId() == id) return e;
        }
        return null;
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    public boolean updateEmployee(int id, String newName, double newSalary) {
        Employee e = getEmployeeById(id);
        if (e != null) {
            e.setName(newName);
            e.setSalary(newSalary);
            return true;
        }
        return false;
    }

    public void sortByNameAsc() {
        employees.sort(Comparator.comparing(Employee::getName));
    }

    public void sortByNameDesc() {
        employees.sort(Comparator.comparing(Employee::getName).reversed());
    }

    public void sortBySalaryAsc() {
        employees.sort(Comparator.comparingDouble(Employee::getSalary));
    }

    public void sortBySalaryDesc() {
        employees.sort(Comparator.comparingDouble(Employee::getSalary).reversed());
    }

    public void saveToFile(String filename) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename))) {
            out.writeObject(employees);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadFromFile(String filename) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename))) {
            employees = (List<Employee>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
