package com.example.demo.service;

import com.example.demo.dto.TransactionDTO;
import com.example.demo.model.Product;
import com.example.demo.model.Transaction;
import com.example.demo.model.Type;
import com.example.demo.repo.TransactionDao;
import com.example.demo.repo.TransactionRepository;
import com.example.demo.validator.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.lang.Boolean.TRUE;

@Service
public class TransactionService{

    @Autowired
    TransactionRepository transactionRepository;
    @Autowired
    Validator validator;

    @Autowired
    TransactionDao transactionDao;

    public void updateTransaction(Transaction transaction) {
        validator.checkIfTransactionExists(transaction.getUuid());
        transactionRepository.save(transaction);
    }


    public Iterable<Transaction> generateMonthlyReport() {
        validator.checkIfDatabaseIsEmpty();
        return transactionRepository.findAll();
    }


    public void confirmTransactionsScheduler() {
       // validator.checkIfDatabaseIsEmpty();
        transactionRepository.findAll().forEach(transaction -> {
            if (!transaction.getIsConfirmed()) {
                transaction.setIsConfirmed(TRUE);
                transactionRepository.save(transaction);
            }
        });
    }

    public Transaction getTransactionById(Integer id) {
        validator.checkIfTransactionExists(id);
        return transactionRepository.findTransactionByUuid(id);
    }

    public List<Transaction> globalFilterByParatameters(TransactionDTO transactionDTO) {
        validator.checkIfSuchTransactionExists(transactionDTO);
        return transactionDao.findTransactionByParameters(transactionDTO);
    }

    public List<Transaction> findTransactionsByMinAmountAndMaxAmountWithJPQL(Double maxAmount, Double minAmount) {
        validator.checkIfSuchTransactionExists(maxAmount, minAmount);
        return transactionRepository.findTransactionsByMinAmountAndMaxAmount(maxAmount, minAmount);
    }


    public Iterable<Transaction> getAllTransactions() {
        validator.checkIfDatabaseIsEmpty();
        return transactionRepository.findAll();
    }

    public boolean deleteTransaction(Integer id) {
        validator.checkIfTransactionExists(id);
        transactionRepository.delete(transactionRepository.findTransactionByUuid(id));
        return true;
    }

    public void saveTransaction(Transaction transaction) {
        validator.validateTransaction(transaction);
        transactionRepository.save(transaction);
    }

    public List<Transaction> findTransactionByIdAndQuantity(Integer uuid, double quntity) {
        validator.checkIfTransactionExists(uuid);
        return transactionDao.findTransactionByIdAndQuantity(uuid,quntity);
    }

    public boolean hasByIdAndQuantity(Integer id, Double dabal){
        return findTransactionByIdAndQuantity(id,dabal).size() > 0;
    }


    public Map<Type, List<Transaction>> reportOfTransactionTypeWithAmount() {
        return transactionRepository.findAll().stream()
                .collect(Collectors.groupingBy(Transaction::getType));
    }

    public Map<List<Product>, List<Transaction>> reportOfTransactionProducts() {
        return transactionRepository.findAll().stream()
                .collect(Collectors.groupingBy(Transaction::getProducts));
    }

    }


