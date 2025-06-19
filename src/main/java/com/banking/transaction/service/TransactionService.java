package com.banking.transaction.service;

import com.banking.transaction.entity.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    /**
     * 查询全部交易信息
     * @param page
     * @param size
     * @return
     */
    List<Transaction> getAllTransactions(int page, int size);

    /**
     * 根据ID查询特定的交易信息
     * @param id
     * @return
     */
    Optional<Transaction> getTransactionById(String id);

    /**
     * 创建新的交易信息
     * @param transaction
     * @return
     */
    Transaction createTransaction(Transaction transaction);

    /***
     * 更新交易信息
     * @param id
     * @param transactionDetails
     * @return
     */
    Transaction updateTransaction(String id, Transaction transactionDetails);

    /**
     * 删除交易信息
     * @param id
     */
    void deleteTransaction(String id);
}