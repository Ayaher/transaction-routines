package br.com.bbex.transactions.controller.request;

/**
 * Encapsulates specific request for new Transaction.
 */
public class TransactionRequest {

    private long account_id;

    private long operation_type_id;

    private double amount;

    public TransactionRequest() {
        this.account_id = 0;
        this.amount = 0;
    }

    public void setOperation_type_id(long operation_type_id) {
        this.operation_type_id = operation_type_id;
    }

    public long getOperation_type_id() {
        return this.operation_type_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getAccount_id() {
        return this.account_id;
    }

    public double getAmount() {
        return this.amount;
    }
}
