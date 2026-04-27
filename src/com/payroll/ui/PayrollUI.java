package com.payroll.ui;

import com.payroll.exception.PayrollException;
import com.payroll.model.ContractEmployee;
import com.payroll.model.SalariedEmployee;
import com.payroll.service.PayrollService;
import com.payroll.util.FileHandler;

import javax.swing.*;
import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class PayrollUI extends JFrame {

    private final PayrollService service     = new PayrollService();
    private final FileHandler    fileHandler = new FileHandler();

    private final JTextField idField     = new JTextField(8);
    private final JTextField nameField   = new JTextField(10);
    private final JTextField deptField   = new JTextField(10);
    private final JTextField salaryField = new JTextField(8);
    private final JTextField hoursField  = new JTextField(6);
    private final JTextField rateField   = new JTextField(6);
    private final JTextArea  outputArea  = new JTextArea(16, 55);

    public PayrollUI() {
        setTitle("Employee Payroll System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        add(buildInputPanel(), BorderLayout.NORTH);
        add(buildOutputPanel(), BorderLayout.CENTER);
        add(buildButtonPanel(), BorderLayout.SOUTH);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel buildInputPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 6));
        panel.setBorder(BorderFactory.createTitledBorder("Employee Details"));

        panel.add(new JLabel("ID:"));       panel.add(idField);
        panel.add(new JLabel("Name:"));     panel.add(nameField);
        panel.add(new JLabel("Dept:"));     panel.add(deptField);
        panel.add(new JLabel("Salary:"));   panel.add(salaryField);
        panel.add(new JLabel("Hours:"));    panel.add(hoursField);
        panel.add(new JLabel("Rate:"));     panel.add(rateField);

        return panel;
    }

    private JScrollPane buildOutputPanel() {
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(outputArea);
        scroll.setBorder(BorderFactory.createTitledBorder("Output"));
        return scroll;
    }

    private JPanel buildButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 8));

        JButton addSalaried = new JButton("Add Salaried");
        JButton addContract = new JButton("Add Contract");
        JButton showEmp     = new JButton("Show Employees");
        JButton calcPayroll = new JButton("Calculate Payroll");
        JButton save        = new JButton("Save");
        JButton load        = new JButton("Load");

        addSalaried.addActionListener(e -> addSalariedEmployee());
        addContract.addActionListener(e -> addContractEmployee());
        showEmp.addActionListener(e    -> showEmployees());
        calcPayroll.addActionListener(e -> calculatePayroll());
        save.addActionListener(e       -> saveData());
        load.addActionListener(e       -> loadData());

        panel.add(addSalaried);
        panel.add(addContract);
        panel.add(showEmp);
        panel.add(calcPayroll);
        panel.add(save);
        panel.add(load);

        return panel;
    }

    private void addSalariedEmployee() {
        try {
            String id     = getField(idField,     "ID");
            String name   = getField(nameField,   "Name");
            String dept   = getField(deptField,   "Department");
            double salary = parseDouble(salaryField, "Salary");

            if (salary <= 0) throw new PayrollException("Salary must be positive.", 400);

            service.addEmployee(new SalariedEmployee(id, name, dept, salary));
            print("Added Salaried Employee: " + name);
            clearFields();

        } catch (PayrollException ex) {
            print("Error: " + ex);
        } catch (NumberFormatException ex) {
            print("Invalid number input for Salary.");
        }
    }

    private void addContractEmployee() {
        try {
            String id    = getField(idField,   "ID");
            String name  = getField(nameField, "Name");
            String dept  = getField(deptField, "Department");
            double hours = parseDouble(hoursField, "Hours");
            double rate  = parseDouble(rateField,  "Rate");

            if (hours <= 0 || rate <= 0) throw new PayrollException("Hours and Rate must be positive.", 400);

            service.addEmployee(new ContractEmployee(id, name, dept, hours, rate));
            print("Added Contract Employee: " + name);
            clearFields();

        } catch (PayrollException ex) {
            print("Error: " + ex);
        } catch (NumberFormatException ex) {
            print("Invalid number input for Hours/Rate.");
        }
    }

    private void showEmployees() {
        outputArea.setText("");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);
        service.listEmployees();
        System.setOut(old);
        print(baos.toString());
    }

    private void calculatePayroll() {
        outputArea.setText("");
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;
        System.setOut(ps);
        service.calculatePayroll();
        System.setOut(old);
        print(baos.toString());
    }

    private void saveData() {
        try {
            fileHandler.saveToFile(service.getEmployees());
            print("Data saved successfully.");
        } catch (PayrollException ex) {
            print("Error: " + ex);
        }
    }

    private void loadData() {
        try {
            service.setEmployees(fileHandler.loadFromFile());
            print("Data loaded successfully.");
        } catch (PayrollException ex) {
            print("Error: " + ex);
        }
    }

    private String getField(JTextField field, String label) throws PayrollException {
        String val = field.getText().trim();
        if (val.isEmpty()) throw new PayrollException(label + " cannot be empty.", 400);
        return val;
    }

    private double parseDouble(JTextField field, String label) throws PayrollException {
        String val = field.getText().trim();
        if (val.isEmpty()) throw new PayrollException(label + " cannot be empty.", 400);
        return Double.parseDouble(val);
    }

    private void print(String msg) {
        outputArea.append(msg + "\n");
    }

    private void clearFields() {
        idField.setText("");
        nameField.setText("");
        deptField.setText("");
        salaryField.setText("");
        hoursField.setText("");
        rateField.setText("");
    }
}