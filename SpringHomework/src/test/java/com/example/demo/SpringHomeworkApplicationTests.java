package com.example.demo;

import com.example.demo.model.Transaction;
import com.example.demo.repo.TransactionDao;
import com.example.demo.service.TransactionService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;

@SpringBootTest
class SpringHomeworkApplicationTests {

    @Autowired
    private TransactionDao transactionService;

    @Test
    @DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
    void contextLoads() {
        List<Transaction> transactionByIdAndQuantity = transactionService.findTransactionByIdAndQuantity(1, 20.0);
        Assertions.assertThat(transactionByIdAndQuantity.size()).isEqualTo(0);
    }

    @Test
    void contextLoadsSecond() {
        System.out.println("Second Test");
    }

}
