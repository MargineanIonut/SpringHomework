package com.example.demo.repo;

import com.example.demo.dto.TransactionDTO;
import com.example.demo.model.Transaction;
import com.example.demo.model.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionDao {
    @Autowired
    EntityManager entityManager;


    /**
     * specific query for specific transaction
     */
    public List<Transaction> findTransactionByIdAndQuantity(Integer uuid, double quantity) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> criteriaQuery = criteriaBuilder.createQuery(Transaction.class);

        Root<Transaction> transaction = criteriaQuery.from(Transaction.class);

        Predicate transactionUUID = criteriaBuilder.equal(transaction.get("uuid"), uuid);
        Predicate transactionQuantity = criteriaBuilder.equal(transaction.get("amount"), quantity);
        criteriaQuery.where(transactionUUID, transactionQuantity);
        TypedQuery<Transaction> query = entityManager.createQuery(criteriaQuery);
        return query.getResultList();
    }

    /**
     * custom query for filtering by parameters
     */
    public List<Transaction> findTransactionByParameters(TransactionDTO transactionDTO) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> criteriaQuery = criteriaBuilder.createQuery(Transaction.class);
        Root<Transaction> transaction = criteriaQuery.from(Transaction.class);
        List<Predicate> predicates = new ArrayList<>();
        if ((transactionDTO.getUuid() != null)) {
            predicates.add(criteriaBuilder.equal(transaction.get("uuid"), transactionDTO.getUuid()));
        }
        if (transactionDTO.getType() != null) {
            predicates.add(criteriaBuilder.equal(transaction.get("type"), transactionDTO.getType()));
        }
        if (transactionDTO.getAmount() != 0.0) {
            predicates.add(criteriaBuilder.equal(transaction.get("amount"), transactionDTO.getAmount()));
        }
        if (transactionDTO.getType() != null && (transactionDTO.equals(Type.SELL) || transactionDTO.equals(Type.BUY))) {
            predicates.add(criteriaBuilder.equal(transaction.get("type"), transactionDTO.getType()));
        }
        if (transactionDTO.getLocalDateTime() != null) {
            predicates.add(criteriaBuilder.equal(transaction.get("localDateTime"), transactionDTO.getLocalDateTime()));
        }
        if(transactionDTO.getEndDate() != null){
            predicates.add(criteriaBuilder.lessThanOrEqualTo(transaction.<LocalDateTime>get("localDateTime"),transactionDTO.getEndDate()));
        }
        if(transactionDTO.getStartDate() != null){
            predicates.add(criteriaBuilder.greaterThan(transaction.<LocalDateTime>get("localDateTime"),transactionDTO.getStartDate()));
        }
        if (transactionDTO.getIsConfirmed() != null) {
            predicates.add(criteriaBuilder.equal(transaction.get("isConfirmed"), transactionDTO.getIsConfirmed()));
        }
        Predicate[] predicates1 = new Predicate[predicates.size()];
        predicates.toArray(predicates1);
        criteriaQuery.where(predicates1);
        TypedQuery<Transaction> query = entityManager.createQuery(criteriaQuery);
        if (transactionDTO.getPageNumber() != null) {
            query.setFirstResult(transactionDTO.getPageNumber());
        }

        if (transactionDTO.getMaxResultOnPage() != null) {
            query.setMaxResults(transactionDTO.getMaxResultOnPage());
        }
        return query.getResultList();
    }
}
