package br.com.bbex.transactions.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long account_id;
    
    @Column(name = "document_number")
    private String document_number;

    public Account() {
        this.document_number = "";
    }

    public Account(String document_number) {
        this.document_number = document_number;
    }

    public long getAccount_id() {
        return this.account_id;
    }

    public void setAccount_id(long account_id) {
        this.account_id = account_id;
    }

    public String getDocument_number() {
        return this.document_number;
    }

    public void setDocument_number(String document_number) {
        this.document_number = document_number;
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