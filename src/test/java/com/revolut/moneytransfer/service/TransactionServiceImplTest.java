package com.revolut.moneytransfer.service;

import com.revolut.moneytransfer.dto.Transaction;
import com.revolut.moneytransfer.repository.AccountRepository;
import com.revolut.moneytransfer.repository.TransactionRepository;
import com.revolut.moneytransfer.repository.UserRepository;
import com.revolut.moneytransfer.service.impl.TransactionServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TransactionServiceImplTest {

    private static final String ID = "id";
    private static final String FROM_ACCOUNT = "fromAccount";
    private static final String TO_ACCOUNT = "toAccount";
    private static final String AMOUNT = "amount";

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ForexService forexService;

    private TransactionService transactionService;

    @Before
    public void setup() {
        transactionService = new TransactionServiceImpl(
                transactionRepository, userRepository, forexService, accountRepository);
    }

    @Test
    @DisplayName("Test get transaction by it's id use-case")
    public void testFindTransactionById() {
        Transaction mockTransaction = getMockTransaction();

        when(transactionRepository.findById(anyInt())).thenReturn(mockTransaction);

        Transaction actualTransaction = transactionService.findById(mockTransaction.getId().toString());

        assertThat(actualTransaction, allOf(
                hasProperty(ID, is(mockTransaction.getId())),
                hasProperty(FROM_ACCOUNT, is(mockTransaction.getFromAccount())),
                hasProperty(TO_ACCOUNT, is(mockTransaction.getToAccount())),
                hasProperty(AMOUNT, is(mockTransaction.getAmount()))
        ));

        verify(transactionRepository, times(1)).findById(anyInt());
    }

    private Transaction getMockTransaction() {
        return Transaction.builder()
                .fromAccount(1)
                .toAccount(1)
                .amount(BigDecimal.TEN)
                .creationDate(new Timestamp(System.currentTimeMillis()))
                .updateDate(new Timestamp(System.currentTimeMillis()))
                .build();
    }
}
