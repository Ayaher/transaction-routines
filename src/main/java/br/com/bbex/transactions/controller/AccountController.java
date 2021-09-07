package br.com.bbex.transactions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import br.com.bbex.transactions.model.Account;
import br.com.bbex.transactions.service.AccountService;

@RestController
@RequestMapping("/api")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<Account> listAccount(@PathVariable long accountId) {
        return accountService.listAccount(accountId);
    }

    @PostMapping("/accounts")
    public ResponseEntity<Account> createAccount(@RequestBody Account account) {
        return accountService.createAccount(account);
    }

}
