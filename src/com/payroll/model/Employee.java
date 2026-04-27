package com.payroll.model;

import java.io.Serializable;

public abstract class Employee implements Serializable, Payable {

    private final String id;
    private String name;
    private String department;

    public Employee(String id, String name, String department) {
        this.id = id;
        this.name = name;
        this.department = department;
    }

    public abstract double calculateSalary();

    @Override
    public double calculatePay() {
        return calculateSalary();
    }

    public String getId()           { return id; }
    public String getName()         { return name; }
    public String getDepartment()   { return department; }

    public void setName(String name)             { this.name = name; }
    public void setDepartment(String department) { this.department = department; }

    @Override
    public String toString() {
        return String.format("[%s] %s | Dept: %s | Pay: %.2f | Status: %s",
                id, name, department, calculatePay(), getPaymentStatus());
    }
}