package com.payroll.main;

import com.payroll.exception.PayrollException;
import com.payroll.model.ContractEmployee;
import com.payroll.model.Employee;
import com.payroll.model.SalariedEmployee;
import com.payroll.util.FileHandler;

import java.util.ArrayList;
import java.util.List;

public class DataGenerator {

    public static void main(String[] args) {
        List<Employee> employees = new ArrayList<>();

        employees.add(new SalariedEmployee("S001", "Aanya Sharma",    "IT",      65000));
        employees.add(new SalariedEmployee("S002", "Rohan Mehta",     "Finance", 72000));
        employees.add(new SalariedEmployee("S003", "Priya Nair",      "HR",      48000));
        employees.add(new SalariedEmployee("S004", "Vikram Singh",    "Sales",   55000));
        employees.add(new SalariedEmployee("S005", "Sneha Patil",     "IT",      68000));
        employees.add(new SalariedEmployee("S006", "Arjun Kapoor",    "Finance", 71000));
        employees.add(new SalariedEmployee("S007", "Meena Iyer",      "HR",      46000));
        employees.add(new SalariedEmployee("S008", "Karan Verma",     "Sales",   53000));
        employees.add(new SalariedEmployee("S009", "Divya Reddy",     "IT",      62000));
        employees.add(new SalariedEmployee("S010", "Amit Joshi",      "Finance", 75000));

        employees.add(new ContractEmployee("C001", "Pooja Desai",     "IT",      160, 350));
        employees.add(new ContractEmployee("C002", "Rahul Gupta",     "Sales",   120, 280));
        employees.add(new ContractEmployee("C003", "Neha Kulkarni",   "HR",       80, 200));
        employees.add(new ContractEmployee("C004", "Suresh Pillai",   "Finance", 140, 320));
        employees.add(new ContractEmployee("C005", "Anjali Bose",     "IT",      100, 375));
        employees.add(new ContractEmployee("C006", "Manish Tiwari",   "Sales",   160, 260));
        employees.add(new ContractEmployee("C007", "Kavita Shah",     "HR",       90, 210));
        employees.add(new ContractEmployee("C008", "Deepak Rao",      "Finance", 130, 340));
        employees.add(new ContractEmployee("C009", "Sonal Mishra",    "IT",      150, 390));
        employees.add(new ContractEmployee("C010", "Nikhil Pandey",   "Sales",   110, 300));

        try {
            new FileHandler().saveToFile(employees);
            System.out.println("payroll_data.dat generated successfully with " + employees.size() + " employees.");
        } catch (PayrollException e) {
            System.out.println("Failed to generate data: " + e);
        }
    }
}