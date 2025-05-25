package service;

import model.Employee;
import java.util.ArrayList;
import java.util.List;

public class EmployeeManager {
    private final List<Employee> employees;

    public EmployeeManager() {
        this.employees = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public Employee getEmployeeById(int id) {
        for (Employee e : employees) {
            if (e.getId() == id) {
                return e;
            }
        }
        return null;
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

    public boolean deleteEmployee(int id) {
        Employee e = getEmployeeById(id);
        if (e != null) {
            employees.remove(e);
            return true;
        }
        return false;
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    public void sortByNameAsc() {
        employees.sort((a, b) -> a.getName().compareToIgnoreCase(b.getName()));
    }

    public void sortByNameDesc() {
        employees.sort((a, b) -> b.getName().compareToIgnoreCase(a.getName()));
    }

    public void sortBySalaryAsc() {
        employees.sort((a, b) -> Double.compare(a.getSalary(), b.getSalary()));
    }

    public void sortBySalaryDesc() {
        employees.sort((a, b) -> Double.compare(b.getSalary(), a.getSalary()));
    }
}
