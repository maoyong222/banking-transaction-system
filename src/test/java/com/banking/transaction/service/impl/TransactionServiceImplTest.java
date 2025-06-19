package com.banking.transaction.service.impl;

import com.banking.transaction.entity.Transaction;
import com.banking.transaction.repository.TransactionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private Transaction transaction;

    @BeforeEach
    void setUp() {
        transaction = new Transaction();
        transaction.setId("1");
        transaction.setAccountNumber("ACC123");
        transaction.setAmount(BigDecimal.valueOf(100.0));
        transaction.setType("DEPOSIT");
    }

    @Test
    void getAllTransactions() {
        List<Transaction> transactions = Collections.singletonList(transaction);
        when(transactionRepository.findAll(0, 10)).thenReturn(transactions);

        List<Transaction> result = transactionService.getAllTransactions(0, 10);

        assertEquals(1, result.size());
        verify(transactionRepository, times(1)).findAll(0, 10);
    }

    @Test
    void getTransactionById() {
        when(transactionRepository.findById("1")).thenReturn(Optional.of(transaction));

        Optional<Transaction> result = transactionService.getTransactionById("1");

        assertTrue(result.isPresent());
        assertEquals("1", result.get().getId());
        verify(transactionRepository, times(1)).findById("1");
    }

    @Test
    void getTransactionByIdNotFound() {
        when(transactionRepository.findById("1")).thenReturn(Optional.empty());

        Optional<Transaction> result = transactionService.getTransactionById("1");

        assertFalse(result.isPresent());
        verify(transactionRepository, times(1)).findById("1");
    }

    @Test
    void createTransaction() {
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction result = transactionService.createTransaction(transaction);

        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void updateTransaction() {
        when(transactionRepository.findById("1")).thenReturn(Optional.of(transaction));
        when(transactionRepository.save(any(Transaction.class))).thenReturn(transaction);

        Transaction updatedTransaction = new Transaction();
        updatedTransaction.setAmount(BigDecimal.valueOf(200.0));
        updatedTransaction.setType("WITHDRAWAL");

        Transaction result = transactionService.updateTransaction("1", updatedTransaction);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(200.0), result.getAmount());
        assertEquals("WITHDRAWAL", result.getType());
        verify(transactionRepository, times(1)).findById("1");
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void updateTransactionNotFound() {
        when(transactionRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> transactionService.updateTransaction("1", transaction));

        verify(transactionRepository, times(1)).findById("1");
        verify(transactionRepository, never()).save(any(Transaction.class));
    }

    @Test
    void deleteTransaction() {
        when(transactionRepository.existsById("1")).thenReturn(true);
        doNothing().when(transactionRepository).deleteById("1");

        assertDoesNotThrow(() -> transactionService.deleteTransaction("1"));

        verify(transactionRepository, times(1)).existsById("1");
        verify(transactionRepository, times(1)).deleteById("1");
    }

    @Test
    void deleteTransactionNotFound() {
        when(transactionRepository.existsById("1")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> transactionService.deleteTransaction("1"));

        verify(transactionRepository, times(1)).existsById("1");
        verify(transactionRepository, never()).deleteById(anyString());
    }
}