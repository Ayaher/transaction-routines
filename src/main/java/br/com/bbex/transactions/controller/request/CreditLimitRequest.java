package br.com.bbex.transactions.controller.request;

public class CreditLimitRequest {

    private double creditLimit;

    public CreditLimitRequest() {
        this.creditLimit = 0;
    }

    public CreditLimitRequest(double creditLimit) {
        this.creditLimit = creditLimit;
    }

    public double getCreditLimit() {
        return this.creditLimit;
    }

    public void setCreditLimit(double creditLimit) {
        this.creditLimit = creditLimit;
    }
    
}
