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
import br.com.bbex.transactions.model.OperationType;
import br.com.bbex.transactions.model.Transaction;

import java.util.Date;
import java.util.Optional;

import br.com.bbex.transactions.repository.AccountRepository;
import br.com.bbex.transactions.repository.OperationTypeRepository;
import br.com.bbex.transactions.repository.TransactionRepository;

@RestController
@RequestMapping("/api")
public class TransactionCrontroller {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    OperationTypeRepository operationTypeRepository;

    @GetMapping(value = "/transactions/{transactionId}")
    public ResponseEntity<Transaction> listTransaction(@PathVariable long transactionId) {
        Optional<Transaction> transactionData = transactionRepository.findById(transactionId);

        if (transactionData.isPresent()) {
            return new ResponseEntity<>(transactionData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/transactions")
    public ResponseEntity<Transaction> createTransaction(@RequestBody TransactionBody transactionBody) {

        Transaction transactionNew = new Transaction(transactionBody.amount);

        Optional<Account> accountData = accountRepository.findById(transactionBody.account_id);
        Optional<OperationType> operationTypeData = operationTypeRepository.findById(transactionBody.operation_type_id);
        if (accountData.isPresent() && operationTypeData.isPresent()) {
            transactionNew.setAccount(accountData.get());
            transactionNew.setOperationType(operationTypeData.get());
            transactionNew.setEventDate(new Date());
            Transaction transactionCreated = transactionRepository.save(transactionNew);
            return new ResponseEntity<>(transactionCreated, HttpStatus.CREATED);
        } else {
            new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    /**
     * Encapsulates the request for new Transaction.
     */
    public static class TransactionBody {
        private long account_id;

        private long operation_type_id;

        private double amount;

        public TransactionBody() {
            this.account_id = 0;
            this.amount = 0;
        }

        public void setOperation_type_id(long operation_type_id) {
            this.operation_type_id = operation_type_id;
        }

        public long getOperation_type_id() {
            return this.operation_type_id;
        }

        public void setAccount_id(long account_id) {
            this.account_id = account_id;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public long getAccount_id() {
            return this.account_id;
        }

        public double getAmount() {
            return this.amount;
        }
    }

}
