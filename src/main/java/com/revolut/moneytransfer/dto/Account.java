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
public class Account {
    private Integer id;
    private Integer userId;
    private BigDecimal accountBalance;
    private Integer accountType;
    private Boolean open;
    private Timestamp creationDate;
    private Timestamp updateDate;
}
