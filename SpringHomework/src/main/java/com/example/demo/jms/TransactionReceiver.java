package com.example.demo.jms;

import com.example.demo.model.Transaction;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionReceiver {

    @JmsListener(destination = "TransactionQueue")
    public void receiveMessage(Transaction transaction){
        System.out.println("1. Received: " + transaction);
    }

    @JmsListener(destination = "TransactionQueueExternal")
    public void receiveMessage2(Transaction transaction){
        System.out.println("1. Received external: " + transaction);
    }
}
