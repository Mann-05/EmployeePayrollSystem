package com.payroll.model;

public class SalariedEmployee extends Employee {

    private double monthlySalary;

    public SalariedEmployee(String id, String name, String department, double monthlySalary) {
        super(id, name, department);
        this.monthlySalary = monthlySalary;
    }

    @Override
    public double calculateSalary() {
        return monthlySalary;
    }

    public double getMonthlySalary()                    { return monthlySalary; }
    public void setMonthlySalary(double monthlySalary)  { this.monthlySalary = monthlySalary; }

    @Override
    public String toString() {
        return "SALARIED | " + super.toString();
    }
}