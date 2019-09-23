package com.revolut.moneytransfer.service;

import com.revolut.moneytransfer.dto.Account;
import com.revolut.moneytransfer.dto.Transaction;
import com.revolut.moneytransfer.dto.User;
import com.revolut.moneytransfer.exception.ServiceException;
import com.revolut.moneytransfer.repository.AccountRepository;
import com.revolut.moneytransfer.repository.TransactionRepository;
import com.revolut.moneytransfer.repository.UserRepository;
import com.revolut.moneytransfer.service.impl.AccountServiceImpl;
import com.revolut.moneytransfer.service.impl.TransactionServiceImpl;
import com.revolut.moneytransfer.type.application.AccountType;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasProperty;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        transactionService = new TransactionServiceImpl(transactionRepository, userRepository, accountRepository, forexService);
    }

    @Test
    @DisplayName("Test find transaction by id use-case")
    public void testFindById() {
        Transaction transaction = getMockTransaction();
        when(transactionRepository.findById(anyInt())).thenReturn(transaction);

        Transaction actualTransaction = transactionService.findById(String.valueOf(1));

        assertThat(actualTransaction, allOf(
                hasProperty(ID, is(actualTransaction.getId())),
                hasProperty(FROM_ACCOUNT, is(actualTransaction.getFromAccount())),
                hasProperty(TO_ACCOUNT, is(actualTransaction.getToAccount())),
                hasProperty(AMOUNT, is(actualTransaction.getAmount()))
        ));

        verify(transactionRepository, times(1)).findById(anyInt());
    }

    @Test
    @DisplayName("Test find debit transaction use-case")
    public void testGetDebitTransactions() {
        Account account = getMockAccount();
        Transaction transaction = getMockTransaction();
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        when(accountRepository.findById(anyInt())).thenReturn(account);
        when(transactionRepository.getDebitTransactions(anyInt())).thenReturn(transactionList);

        List<Transaction> actualTransactions = transactionService.getDebitTransactions(account.getId().toString());

        assertThat(actualTransactions.get(0), allOf(
                hasProperty(ID, is(transactionList.get(0).getId())),
                hasProperty(FROM_ACCOUNT, is(transactionList.get(0).getFromAccount())),
                hasProperty(TO_ACCOUNT, is(transactionList.get(0).getToAccount())),
                hasProperty(AMOUNT, is(transactionList.get(0).getAmount()))
        ));

        verify(accountRepository, times(1)).findById(anyInt());
        verify(transactionRepository, times(1)).getDebitTransactions(anyInt());
    }

    @Test
    @DisplayName("Test find credit transaction use-case")
    public void testGetCreditTransactions() {
        Account account = getMockAccount();
        Transaction transaction = getMockTransaction();
        List<Transaction> transactionList = new ArrayList<>();
        transactionList.add(transaction);

        when(accountRepository.findById(anyInt())).thenReturn(account);
        when(transactionRepository.getCreditTransactions(anyInt())).thenReturn(transactionList);

        List<Transaction> actualTransactions = transactionService.getCreditTransactions(account.getId().toString());

        assertThat(actualTransactions.get(0), allOf(
                hasProperty(ID, is(transactionList.get(0).getId())),
                hasProperty(FROM_ACCOUNT, is(transactionList.get(0).getFromAccount())),
                hasProperty(TO_ACCOUNT, is(transactionList.get(0).getToAccount())),
                hasProperty(AMOUNT, is(transactionList.get(0).getAmount()))
        ));

        verify(accountRepository, times(1)).findById(anyInt());
        verify(transactionRepository, times(1)).getCreditTransactions(anyInt());
    }

    private Account getMockAccount() {
        return Account.builder()
                .id(1)
                .userId(1)
                .accountBalance(BigDecimal.TEN)
                .accountType(AccountType.CURRENT.getCode())
                .open(true)
                .creationDate(new Timestamp(1))
                .updateDate(new Timestamp(1))
                .build();
    }

    private User getMockUser() {
        return User.builder()
                .id(1)
                .firstName("Nikola")
                .lastName("Tesla")
                .email("ntesla@yahoo.com")
                .country("SRB")
                .currency("DIN")
                .creationDate(new Timestamp(1))
                .updateDate(new Timestamp(1))
                .build();
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
