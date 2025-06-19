package com.banking.transaction.service;

import com.banking.transaction.entity.Transaction;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    /**
     * 查询全部交易信息
     * @param page 页数
     * @param size 数量
     * @return 交易信息列表
     */
    List<Transaction> getAllTransactions(int page, int size);

    /**
     * 根据ID查询特定的交易信息
     * @param id 唯一ID
     * @return  交易信息
     */
    Optional<Transaction> getTransactionById(String id);

    /**
     * 创建新的交易信息
     * @param transaction 交易信息
     * @return  交易信息
     */
    Transaction createTransaction(Transaction transaction);

    /***
     * 更新交易信息
     * @param id  唯一ID
     * @param transactionDetails    交易信息
     * @return  交易信息
     */
    Transaction updateTransaction(String id, Transaction transactionDetails);

    /**
     * 删除交易信息
     * @param id 唯一ID
     */
    void deleteTransaction(String id);
}