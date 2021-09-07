package br.com.bbex.transactions.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.bbex.transactions.model.Account;
import br.com.bbex.transactions.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public ResponseEntity<Account> listAccount(long accountId) {
        if (accountId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Account> accountData = accountRepository.findById(accountId);
        if (accountData.isPresent()) {
            return new ResponseEntity<>(accountData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Account> createAccount(Account account) {
        if (!account.getDocument_number().matches("[0-9]+")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
			Account accountCreated = accountRepository.save(new Account(account.getDocument_number()));
			return new ResponseEntity<>(accountCreated, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }
    
}
