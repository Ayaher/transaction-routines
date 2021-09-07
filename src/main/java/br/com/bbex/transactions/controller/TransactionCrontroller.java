package br.com.bbex.transactions.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import br.com.bbex.transactions.controller.request.TransactionRequest;
import br.com.bbex.transactions.model.Transaction;

import br.com.bbex.transactions.service.TransactionService;

@RestController
@RequestMapping("/api")
public class TransactionCrontroller {

    @Autowired
    TransactionService transactionService;

    @GetMapping(value = "/transactions/{transactionId}")
    public ResponseEntity<Transaction> listTransaction(@PathVariable long transactionId) {
        return transactionService.listTransaction(transactionId);
    }

    @PostMapping("/transactions")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionRequest transactionRequest) {
        return transactionService.createTransaction(transactionRequest);
    }

}
