package com.example.demo.repo;

import com.example.demo.model.Transaction;
import com.example.demo.model.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface TransactionRepository extends JpaRepository<Transaction,Integer> {
    Transaction findTransactionByUuid(Integer id);

    List<Transaction> findAllByType(Type type);

    @Query(value = "SELECT * FROM transaction t WHERE (:maxAmount is null or t.amount < :maxAmount) AND (:minAmount is null or t.amount > :minAmount)", nativeQuery = true)
    List<Transaction> findTransactionsByMinAmountAndMaxAmount(@Param("maxAmount") Double maxAmount,@Param("minAmount") Double minAmount);

    @Query(value = "SELECT * FROM transaction t WHERE (:maxAmount is null or t.amount < :maxAmount) AND (:minAmount is null or t.amount > :minAmount)", nativeQuery = true)
    List<Transaction> globalFilterNativeSql(@Param("maxAmount") Double maxAmount,@Param("minAmount") Double minAmount);
}
