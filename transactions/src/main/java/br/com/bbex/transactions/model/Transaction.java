package br.com.bbex.transactions.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transaction_id;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "amount")
    private double amount;

    @Column(name = "eventDate")
    private Date eventDate;

    public double getAmount() {
        return this.amount;
    }

    public Transaction() {

    }

    public Transaction(double amount) {
        this.amount = amount;
    }

    public Account getAccount() {
        return this.account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getEventDate() {
        return this.eventDate;
    }

    public void setEventDate(Date eventDate) {
        this.eventDate = eventDate;
    }

    public long getTransaction_id() {
        return this.transaction_id;
    }

    public void setTransaction_id(long transaction_id) {
        this.transaction_id = transaction_id;
    }

    /**
     * Método de conveniência para converter este objeto para JSON.
     */
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        String s = "";
        try {
            s = objectMapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return s;
    }

}
