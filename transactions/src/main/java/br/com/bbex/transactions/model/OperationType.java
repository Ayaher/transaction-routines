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
@Table(name = "operations_type")
public class OperationType {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long operation_type_id;

    @Column(name = "description")    
    private String description;

    @Column(name = "decrease")
    private boolean decrease;

    public OperationType() {
        this.description = "";
        this.decrease = true;
    }

    public OperationType(String description, boolean decrease) {
        this.description = description;
        this.decrease = decrease;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDecrease() {
        return this.decrease;
    }

    public boolean getDecrease() {
        return this.decrease;
    }

    public void setDecrease(boolean decrease) {
        this.decrease = decrease;
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
