package com.banking.transaction.repository;

import com.banking.transaction.entity.Transaction;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class TransactionRepository {

    private final Map<String, Transaction> transactionMap = new ConcurrentHashMap<>();

    @Cacheable("transactions")
    public List<Transaction> findAll(int page, int size) {
        return transactionMap.values().stream()
                .sorted(Comparator.comparing(Transaction::getTimestamp).reversed())
                .skip((long) page * size)
                .limit(size)
                .collect(Collectors.toList());
    }

    @Cacheable(value = "transactions", key = "#id")
    public Optional<Transaction> findById(String id) {
        return Optional.ofNullable(transactionMap.get(id));
    }

    @CacheEvict(value = "transactions", allEntries = true)
    public Transaction save(Transaction transaction) {
        transactionMap.put(transaction.getId(), transaction);
        return transaction;
    }

    @CacheEvict(value = "transactions", allEntries = true)
    public void deleteById(String id) {
        transactionMap.remove(id);
    }

    public boolean existsById(String id) {
        return transactionMap.containsKey(id);
    }

    public long count() {
        return transactionMap.size();
    }
}