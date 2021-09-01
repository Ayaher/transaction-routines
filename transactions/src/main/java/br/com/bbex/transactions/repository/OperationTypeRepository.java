package br.com.bbex.transactions.repository;

import br.com.bbex.transactions.model.OperationType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OperationTypeRepository extends JpaRepository<OperationType, Long> {
    
}
