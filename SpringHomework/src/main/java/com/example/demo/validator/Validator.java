package com.example.demo.validator;

import com.example.demo.dto.TransactionDTO;
import com.example.demo.model.Transaction;
import com.example.demo.repo.TransactionDao;
import com.example.demo.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.NotAuthorizedException;
import java.util.NoSuchElementException;

@Component
public class Validator {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    TransactionDao transactionDao;

    public void checkIfTransactionExists(Integer id) {
        if (transactionRepository.findTransactionByUuid(id) == null)
            throw new NoSuchElementException("This transaction is not present in database");
    }

    public void checkIfDatabaseIsEmpty() {
        if (transactionRepository.findAll().isEmpty()) {
            throw new EntityNotFoundException("No transactions are present in database");
        }
    }

    public void validateTransaction(Transaction transaction) {
        if (transaction.getProducts() == null
                && transaction.getType() == null
                && transaction.getIsConfirmed() == null &&
                transaction.getAmount() == 0.0) {
            throw new NotAuthorizedException("This transaction is null");
        }
    }

    public void checkIfSuchTransactionExists(Double maxAmount, Double minAmount) {
        if (transactionRepository.findTransactionsByMinAmountAndMaxAmount(maxAmount, minAmount).isEmpty())
            throw new NoSuchElementException("This transaction doesnt exist");
    }

    public void checkIfSuchTransactionExists(TransactionDTO transactionDTO) {
        if (transactionDao.findTransactionByParameters(transactionDTO).isEmpty()) {
            throw new NoSuchElementException("No transaction available with this filter criterias");
        }
    }
}


