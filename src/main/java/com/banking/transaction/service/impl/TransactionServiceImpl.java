package com.banking.transaction.service.impl;

import com.banking.transaction.entity.Transaction;
import com.banking.transaction.repository.TransactionRepository;
import com.banking.transaction.service.TransactionService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @Override
    @Cacheable("transactions")
    public List<Transaction> getAllTransactions(int page, int size) {
        return transactionRepository.findAll(page, size);
    }

    @Override
    @Cacheable(value = "transactions", key = "#id")
    public Optional<Transaction> getTransactionById(String id) {
        return transactionRepository.findById(id);
    }

    @Override
    @CacheEvict(value = "transactions", allEntries = true)
    public Transaction createTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    @CacheEvict(value = "transactions", allEntries = true)
    public Transaction updateTransaction(String id, Transaction transactionDetails) {
        return transactionRepository.findById(id)
                .map(transaction -> {
                    transaction.setAccountNumber(transactionDetails.getAccountNumber());
                    transaction.setAmount(transactionDetails.getAmount());
                    transaction.setType(transactionDetails.getType());
                    transaction.setDescription(transactionDetails.getDescription());
                    return transactionRepository.save(transaction);
                })
                .orElseThrow(() -> new RuntimeException("Transaction not found with id " + id));
    }

    @Override
    @CacheEvict(value = "transactions", allEntries = true)
    public void deleteTransaction(String id) {
        if (!transactionRepository.existsById(id)) {
            throw new RuntimeException("Transaction not found with id " + id);
        }
        transactionRepository.deleteById(id);
    }
}