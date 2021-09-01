package br.com.bbex.transactions.repository;

import br.com.bbex.transactions.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{
    
}
