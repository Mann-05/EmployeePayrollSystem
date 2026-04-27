package com.payroll.model;

public class ContractEmployee extends Employee {

    private double hoursWorked;
    private double hourlyRate;

    public ContractEmployee(String id, String name, String department, double hoursWorked, double hourlyRate) {
        super(id, name, department);
        this.hoursWorked = hoursWorked;
        this.hourlyRate  = hourlyRate;
    }

    @Override
    public double calculateSalary() {
        return hoursWorked * hourlyRate;
    }

    public double getHoursWorked()                  { return hoursWorked; }
    public double getHourlyRate()                   { return hourlyRate; }
    public void setHoursWorked(double hoursWorked)  { this.hoursWorked = hoursWorked; }
    public void setHourlyRate(double hourlyRate)    { this.hourlyRate = hourlyRate; }

    @Override
    public String toString() {
        return "CONTRACT  | " + super.toString();
    }
}