package com.banking.transaction.controller;

import com.banking.transaction.entity.Transaction;
import com.banking.transaction.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    void getAllTransactions() throws Exception {
        Transaction transaction1 = new Transaction();
        transaction1.setId("1");
        transaction1.setAccountNumber("ACC123");
        transaction1.setAmount(BigDecimal.valueOf(100.0));
        transaction1.setType("DEPOSIT");
        transaction1.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));

        Transaction transaction2 = new Transaction();
        transaction2.setId("2");
        transaction2.setAccountNumber("ACC456");
        transaction2.setAmount(BigDecimal.valueOf(200.0));
        transaction2.setType("WITHDRAWAL");
        transaction2.setTimestamp(Timestamp.valueOf(LocalDateTime.now().minusHours(1)));

        when(transactionService.getAllTransactions(0, 10)).thenReturn(Arrays.asList(transaction1, transaction2));

        mockMvc.perform(get("/api/transactions"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id").value("1"))
                .andExpect(jsonPath("$[1].id").value("2"));

        verify(transactionService, times(1)).getAllTransactions(0, 10);
    }

    @Test
    void getTransactionById() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setId("1");
        transaction.setAccountNumber("ACC123");
        transaction.setAmount(BigDecimal.valueOf(100.0));
        transaction.setType("DEPOSIT");
        transaction.setTimestamp(Timestamp.valueOf(LocalDateTime.now()));

        when(transactionService.getTransactionById("1")).thenReturn(Optional.of(transaction));

        mockMvc.perform(get("/api/transactions/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.accountNumber").value("ACC123"));

        verify(transactionService, times(1)).getTransactionById("1");
    }

    @Test
    void getTransactionByIdNotFound() throws Exception {
        when(transactionService.getTransactionById("1")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/transactions/{id}", "1"))
                .andExpect(status().isNotFound());

        verify(transactionService, times(1)).getTransactionById("1");
    }

    @Test
    void createTransaction() throws Exception {
        Transaction transaction = new Transaction();
        transaction.setId("1");
        transaction.setAccountNumber("ACC123");
        transaction.setAmount(BigDecimal.valueOf(100.0));
        transaction.setType("DEPOSIT");

        when(transactionService.createTransaction(any(Transaction.class))).thenReturn(transaction);

        mockMvc.perform(post("/api/transactions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"accountNumber\":\"ACC123\",\"amount\":100.0,\"type\":\"DEPOSIT\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.accountNumber").value("ACC123"));

        verify(transactionService, times(1)).createTransaction(any(Transaction.class));
    }

    @Test
    void updateTransaction() throws Exception {
        Transaction existingTransaction = new Transaction();
        existingTransaction.setId("1");
        existingTransaction.setAccountNumber("ACC123");
        existingTransaction.setAmount(BigDecimal.valueOf(100.0));
        existingTransaction.setType("DEPOSIT");

        Transaction updatedTransaction = new Transaction();
        updatedTransaction.setId("1");
        updatedTransaction.setAccountNumber("ACC123");
        updatedTransaction.setAmount(BigDecimal.valueOf(200.0));
        updatedTransaction.setType("WITHDRAWAL");

        when(transactionService.getTransactionById("1")).thenReturn(Optional.of(existingTransaction));
        when(transactionService.updateTransaction(eq("1"), any(Transaction.class))).thenReturn(updatedTransaction);

        mockMvc.perform(put("/api/transactions/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":200.0,\"type\":\"WITHDRAWAL\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("1"))
                .andExpect(jsonPath("$.amount").value(200.0))
                .andExpect(jsonPath("$.type").value("WITHDRAWAL"));

        verify(transactionService, times(1)).updateTransaction(eq("1"), any(Transaction.class));
    }

    @Test
    void updateTransactionNotFound() throws Exception {
        when(transactionService.getTransactionById("2")).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/transactions/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"amount\":200.0,\"type\":\"WITHDRAWAL\"}"))
                .andExpect(status().isOk());

        verify(transactionService, times(1)).updateTransaction(anyString(), any(Transaction.class));
    }

    @Test
    void deleteTransaction() throws Exception {
        doNothing().when(transactionService).deleteTransaction("1");

        mockMvc.perform(delete("/api/transactions/{id}", "1"))
                .andExpect(status().isNoContent());

        verify(transactionService, times(1)).deleteTransaction("1");
    }

    @Test
    void deleteTransactionNotFound() throws Exception {
        doThrow(new RuntimeException("Transaction not found")).when(transactionService).deleteTransaction("1");

        mockMvc.perform(delete("/api/transactions/{id}", "1"))
                .andExpect(status().isNotFound());

        verify(transactionService, times(1)).deleteTransaction("1");
    }
}