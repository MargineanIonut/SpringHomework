package com.example.demo.controller;

import com.example.demo.dto.TransactionDTO;
import com.example.demo.model.*;
import com.example.demo.service.RoleService;
import com.example.demo.service.TransactionService;
import com.example.demo.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("transactions")
@AllArgsConstructor
public class TransactionController {


    private final TransactionService transactionService;

    private final UserService userService;
    private final RoleService roleService;

    private final JmsTemplate jmsTemplate;



    @GetMapping
    public ResponseEntity<Iterable<Transaction>> getAllTransaction() {
        return new ResponseEntity<>(transactionService.getAllTransactions(), HttpStatus.OK);
    }

    @GetMapping("/reports/type")
    public ResponseEntity<Map<Type, List<Transaction>>> getTransactionTypeReport() {
        return new ResponseEntity<>(transactionService.reportOfTransactionTypeWithAmount(), HttpStatus.OK);
    }

    @GetMapping("/reports/product")
    public ResponseEntity<Map<List<Product>, List<Transaction>>> getTransactionProductReport() {
        return new ResponseEntity<>(transactionService.reportOfTransactionProducts(), HttpStatus.OK);
    }

    @GetMapping({"/{transactionId}"})
    public ResponseEntity<Transaction> getTransactionById(@PathVariable("transactionId") Integer id) {
        return new ResponseEntity<>(transactionService.getTransactionById(id), HttpStatus.OK);
    }

    @PostMapping("/post")
    public ResponseEntity<Transaction> createTransaction(@RequestBody Transaction transactionParam) {
        Transaction transaction = new Transaction(transactionParam.getUuid(), transactionParam.getProducts(), transactionParam.getType(), transactionParam.getAmount(), LocalDateTime.now(), transactionParam.getIsConfirmed());
        transactionService.saveTransaction(transaction);
        jmsTemplate.convertAndSend("TransactionQueueExternal", transaction);

        jmsTemplate.convertAndSend("TransactionQueue", transaction);
        return ResponseEntity.created(URI.create("/" + transaction.getUuid())).body(transaction);
    }

    @PutMapping
    public ResponseEntity<Transaction> updateTransaction(@RequestBody Transaction transactionParam) {
        transactionService.updateTransaction(transactionParam);
        return ResponseEntity.created(URI.create("/" + transactionParam.getUuid())).body(transactionParam);
    }

    @DeleteMapping({"/{transactionId}"})
    public ResponseEntity<Boolean> deleteTransactionById(@PathVariable("transactionId") Integer id) {
        return new ResponseEntity<>(transactionService.deleteTransaction(id), HttpStatus.OK);
    }

    @GetMapping("/globalFilter")
    public ResponseEntity<List<Transaction>> globalFilter(TransactionDTO transactionDTO) {
        return new ResponseEntity<>(transactionService.globalFilterByParatameters(transactionDTO), HttpStatus.OK);
    }

    @GetMapping("/specificTransaction")
    public ResponseEntity<List<Transaction>> getByIdAndQuantity(@RequestParam(required = false) Integer uuid, @RequestParam(required = false) double quntity) {
        return new ResponseEntity<>(transactionService.findTransactionByIdAndQuantity(uuid, quntity), HttpStatus.OK);
    }

    @GetMapping({"/minMax"})
    public ResponseEntity<List> getTransactionByMaxAndMinOrMaxOrMin(@RequestParam(required = false) Double maxValue, @RequestParam(required = false) Double minValue) {
        return new ResponseEntity<>(transactionService.findTransactionsByMinAmountAndMaxAmountWithJPQL(maxValue, minValue), HttpStatus.OK);
    }

    @PostMapping("/user")
    public ResponseEntity<Users> saveUser(@RequestBody Users user) {
        return new ResponseEntity<>(userService.saveUser(user), HttpStatus.OK);
    }

    @PostMapping("/role")
    public  ResponseEntity<Role> saveRole(@RequestBody Role role) {
        return new ResponseEntity<>(roleService.saveRole(role), HttpStatus.OK);
    }

}
