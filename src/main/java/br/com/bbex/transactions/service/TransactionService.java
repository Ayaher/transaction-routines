package br.com.bbex.transactions.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import br.com.bbex.transactions.controller.request.TransactionRequest;
import br.com.bbex.transactions.model.Account;
import br.com.bbex.transactions.model.OperationType;
import br.com.bbex.transactions.model.Transaction;
import br.com.bbex.transactions.repository.AccountRepository;
import br.com.bbex.transactions.repository.OperationTypeRepository;
import br.com.bbex.transactions.repository.TransactionRepository;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    OperationTypeRepository operationTypeRepository;

    public ResponseEntity<Transaction> listTransaction(long transactionId) {
        if (transactionId <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<Transaction> transactionData = transactionRepository.findById(transactionId);
        if (transactionData.isPresent()) {
            return new ResponseEntity<>(transactionData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Transaction> createTransaction(TransactionRequest transactionBody) {
        if(transactionBody.getAccount_id() <= 0 || transactionBody.getOperation_type_id() <= 0 || transactionBody.getAmount() < 0) {
            new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Transaction transactionNew = new Transaction(transactionBody.getAmount());
        Optional<Account> accountData = accountRepository.findById(transactionBody.getAccount_id());
        Optional<OperationType> operationTypeData = operationTypeRepository.findById(transactionBody.getOperation_type_id());

        if (accountData.isPresent() && operationTypeData.isPresent()) {
            transactionNew.setAccount(accountData.get());
            transactionNew.setOperationType(operationTypeData.get());
            transactionNew.setEventDate(new Date());
            Transaction transactionCreated = transactionRepository.save(transactionNew);
            return new ResponseEntity<>(transactionCreated, HttpStatus.CREATED);
        } else {
            new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    
}
