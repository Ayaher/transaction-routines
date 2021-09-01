package br.com.bbex.transactions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import br.com.bbex.transactions.model.Account;
import java.util.Optional;

import br.com.bbex.transactions.repository.AccountRepository;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @GetMapping(value = "/accounts/{accountId}")
    public ResponseEntity<Account> listAccount(@PathVariable long accountId) {
        Optional<Account> accountData = accountRepository.findById(accountId);

        if (accountData.isPresent()) {
            return new ResponseEntity<>(accountData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        try {
			Account accountCreated = accountRepository.save(new Account(account.getDocument_number()));
			return new ResponseEntity<>(accountCreated, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
    }

}
