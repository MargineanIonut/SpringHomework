package com.example.demo.scheduler;

import com.example.demo.model.Transaction;
import com.example.demo.service.TransactionService;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
public class Scheduler {
    TransactionService transactionService;

    public Scheduler(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Scheduled(fixedDelay = 300000)
    public void generateReportOfConfirmedTransactions(){
        transactionService.confirmTransactionsScheduler();

    }

    @Scheduled(cron = "0 0 0 1 * *")
    public Iterable<Transaction> generateReportOfTransactions(){
        return transactionService.generateMonthlyReport();

    }
}
