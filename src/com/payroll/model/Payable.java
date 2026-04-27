package com.payroll.model;

public interface Payable {

    double calculatePay();

    default String getPaymentStatus() {
        return calculatePay() > 0 ? "APPROVED" : "PENDING";
    }
}