package br.com.bbex.transactions.repository;

import br.com.bbex.transactions.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, Long> {
    
}
