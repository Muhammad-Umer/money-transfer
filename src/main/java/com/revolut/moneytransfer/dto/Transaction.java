package com.revolut.moneytransfer.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Transaction {
    private Integer id;
    private Integer fromAccount;
    private Integer toAccount;
    private BigDecimal amount;
    private Timestamp creationDate;
    private Timestamp updateDate;
}
