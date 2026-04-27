package com.payroll.service;

import com.payroll.exception.PayrollException;
import com.payroll.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class PayrollService {

    private final List<Employee> employees = new ArrayList<>();

    public void addEmployee(Employee e) throws PayrollException {
        for (Employee emp : employees) {
            if (emp.getId().equals(e.getId())) {
                throw new PayrollException("Duplicate ID: " + e.getId(), 200);
            }
        }
        employees.add(e);
        System.out.println("Added: " + e.getName());
    }

    public void removeEmployee(String id) throws PayrollException {
        Employee found = findById(id);
        employees.remove(found);
        System.out.println("Removed: " + found.getName());
    }

    public Employee findById(String id) throws PayrollException {
        for (Employee e : employees) {
            if (e.getId().equals(id)) return e;
        }
        throw new PayrollException("Employee not found: " + id, 100);
    }

    public void listEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees on record.");
            return;
        }
        System.out.println("\n--- Employee List ---");
        for (Employee e : employees) {
            System.out.println(e);
        }
    }

    public void calculatePayroll() {
        if (employees.isEmpty()) {
            System.out.println("No employees to process.");
            return;
        }
        double total = 0;
        System.out.println("\n--- Payroll Summary ---");
        for (Employee e : employees) {
            double pay = e.calculatePay();
            System.out.printf("%-20s -> Rs. %10.2f [%s]%n",
                    e.getName(), pay, e.getPaymentStatus());
            total += pay;
        }
        System.out.printf("%-20s -> Rs. %10.2f%n", "TOTAL PAYROLL", total);
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> loaded) {
        employees.clear();
        employees.addAll(loaded);
    }
}