package com.payroll.util;

import com.payroll.exception.PayrollException;
import com.payroll.model.Employee;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandler {

    private static final String FILE_PATH = "payroll_data.dat";

    public void saveToFile(List<Employee> employees) throws PayrollException {
        try (FileOutputStream fos = new FileOutputStream(FILE_PATH);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {

            oos.writeObject(employees);
            System.out.println("Data saved to " + FILE_PATH);

        } catch (IOException e) {
            throw new PayrollException("Save failed: " + e.getMessage(), 300);
        }
    }

    @SuppressWarnings("unchecked")
    public List<Employee> loadFromFile() throws PayrollException {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.out.println("No saved data found.");
            return new ArrayList<>();
        }

        try (FileInputStream fis = new FileInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(fis)) {

            List<Employee> employees = (List<Employee>) ois.readObject();
            System.out.println("Loaded " + employees.size() + " employee(s) from " + FILE_PATH);
            return employees;

        } catch (IOException | ClassNotFoundException e) {
            throw new PayrollException("Load failed: " + e.getMessage(), 300);
        }
    }
}