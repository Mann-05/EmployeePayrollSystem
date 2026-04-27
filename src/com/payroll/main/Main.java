package com.payroll.main;

import com.payroll.exception.PayrollException;
import com.payroll.model.ContractEmployee;
import com.payroll.model.SalariedEmployee;
import com.payroll.service.PayrollService;
import com.payroll.ui.PayrollUI;
import com.payroll.util.FileHandler;
import java.util.Scanner;

public class Main {

    private static final PayrollService service = new PayrollService();
    private static final FileHandler    fileHandler = new FileHandler();
    private static final Scanner        scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=== Employee Payroll System ===");
        loadData();
        PayrollUI payrollUI = new PayrollUI();
        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();
            switch (choice) {
                case "1" -> addSalariedEmployee();
                case "2" -> addContractEmployee();
                case "3" -> removeEmployee();
                case "4" -> service.listEmployees();
                case "5" -> service.calculatePayroll();
                case "6" -> saveData();
                case "7" -> { saveData(); running = false; }
                default  -> System.out.println("Invalid option. Try again.");
            }
        }
        System.out.println("Goodbye!");
        scanner.close();
    }

    private static void printMenu() {
        System.out.println("""
                
                --- MENU ---
                1. Add Salaried Employee
                2. Add Contract Employee
                3. Remove Employee
                4. List Employees
                5. Calculate Payroll
                6. Save Data
                7. Save & Exit
                Choose: """);
    }

    private static void addSalariedEmployee() {
        try {
            System.out.print("ID: ");         String id   = scanner.nextLine().trim();
            System.out.print("Name: ");       String name = scanner.nextLine().trim();
            System.out.print("Department: "); String dept = scanner.nextLine().trim();
            System.out.print("Monthly Salary: "); double salary = Double.parseDouble(scanner.nextLine().trim());

            if (salary <= 0) throw new PayrollException("Salary must be positive.", 400);

            service.addEmployee(new SalariedEmployee(id, name, dept, salary));

        } catch (NumberFormatException e) {
            System.out.println("Invalid number input.");
        } catch (PayrollException e) {
            System.out.println("Error: " + e);
        }
    }

    private static void addContractEmployee() {
        try {
            System.out.print("ID: ");           String id   = scanner.nextLine().trim();
            System.out.print("Name: ");         String name = scanner.nextLine().trim();
            System.out.print("Department: ");   String dept = scanner.nextLine().trim();
            System.out.print("Hours Worked: "); double hours = Double.parseDouble(scanner.nextLine().trim());
            System.out.print("Hourly Rate: ");  double rate  = Double.parseDouble(scanner.nextLine().trim());

            if (hours <= 0 || rate <= 0) throw new PayrollException("Hours and rate must be positive.", 400);

            service.addEmployee(new ContractEmployee(id, name, dept, hours, rate));

        } catch (NumberFormatException e) {
            System.out.println("Invalid number input.");
        } catch (PayrollException e) {
            System.out.println("Error: " + e);
        }
    }

    private static void removeEmployee() {
        try {
            System.out.print("Enter Employee ID to remove: ");
            String id = scanner.nextLine().trim();
            service.removeEmployee(id);
        } catch (PayrollException e) {
            System.out.println("Error: " + e);
        }
    }

    private static void saveData() {
        try {
            fileHandler.saveToFile(service.getEmployees());
        } catch (PayrollException e) {
            System.out.println("Error: " + e);
        }
    }

    private static void loadData() {
        try {
            service.setEmployees(fileHandler.loadFromFile());
        } catch (PayrollException e) {
            System.out.println("Error: " + e);
        }
    }
}