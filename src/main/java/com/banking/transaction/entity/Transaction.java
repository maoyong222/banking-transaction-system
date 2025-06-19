package com.banking.transaction.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Transaction  {
    private String id;

    private String accountNumber;

    private BigDecimal amount;

    private String type; // "DEPOSIT", "WITHDRAWAL", "TRANSFER"

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private Timestamp timestamp;

    private String description;

    public Transaction() {
        this.id = UUID.randomUUID().toString();
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
    }


}