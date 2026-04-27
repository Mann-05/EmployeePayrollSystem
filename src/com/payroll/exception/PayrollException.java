package com.payroll.exception;

public class PayrollException extends Exception {

    private final int errorCode;

    public PayrollException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "PayrollException [code=" + errorCode + "] " + getMessage();
    }
}