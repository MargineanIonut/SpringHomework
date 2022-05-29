package com.example.demo.service;

import com.example.demo.repo.TransactionDao;
import com.example.demo.repo.TransactionRepository;
import org.assertj.core.api.Assertions;
import org.hibernate.service.spi.InjectService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    TransactionRepository transactionRepository;

    @Mock
    TransactionDao transactionDao;

    @Spy
    @InjectMocks
    private TransactionService transactionService;


    @Test
    void globalFilterByParatameters() {

        Mockito.when(transactionDao.findTransactionByIdAndQuantity(Mockito.anyInt(), Mockito.anyDouble())).thenReturn(new ArrayList<>());

        transactionService.findTransactionByIdAndQuantity(1, 20.0);

        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Double> quantityCaptor = ArgumentCaptor.forClass(Double.class);

        Mockito.verify(transactionDao, Mockito.times(1)).findTransactionByIdAndQuantity(idCaptor.capture(),quantityCaptor.capture());
        Assertions.assertThat(idCaptor.getValue()).isEqualTo(1);
        Assertions.assertThat(quantityCaptor.getValue()).isEqualTo(20.0);
    }

    @Test
    void hasGlobalFilterByParatameters() {
        Mockito.when(transactionService.findTransactionByIdAndQuantity(Mockito.anyInt(), Mockito.anyDouble())).thenReturn(new ArrayList<>());
        Mockito.doReturn(new ArrayList<>()).when(transactionService).findTransactionByIdAndQuantity(Mockito.anyInt(),Mockito.anyDouble());

        boolean byIdAndQuantityBool = transactionService.hasByIdAndQuantity(1, 20.0);

        ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
        ArgumentCaptor<Double> quantityCaptor = ArgumentCaptor.forClass(Double.class);

        Mockito.verify(transactionService, Mockito.times(2)).findTransactionByIdAndQuantity(idCaptor.capture(),quantityCaptor.capture());
        Assertions.assertThat(idCaptor.getValue()).isEqualTo(1);
        Assertions.assertThat(quantityCaptor.getValue()).isEqualTo(20.0);
        Assertions.assertThat(byIdAndQuantityBool).isEqualTo(false);
    }
}