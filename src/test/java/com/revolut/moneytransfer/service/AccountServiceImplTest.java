package com.revolut.moneytransfer.service;

import com.revolut.moneytransfer.dto.Account;
import com.revolut.moneytransfer.dto.User;
import com.revolut.moneytransfer.exception.ServiceException;
import com.revolut.moneytransfer.repository.AccountRepository;
import com.revolut.moneytransfer.repository.TransactionRepository;
import com.revolut.moneytransfer.repository.UserRepository;
import com.revolut.moneytransfer.service.impl.AccountServiceImpl;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    private static final String ID = "id";
    private static final String USER_ID = "userId";
    private static final String ACCOUNT_BALANCE = "accountBalance";
    private static final String ACCOUNT_TYPE = "accountType";
    private static final String OPEN = "open";

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionRepository transactionRepository;

    private AccountService accountService;

    @Before
    public void setup() {
        accountService = new AccountServiceImpl(accountRepository, userRepository, transactionRepository);
    }

    @Test
    @DisplayName("Test add an account when user id is null use-case")
    public void testAddAccountUserIdException() {
        Account mockAccount = getMockAccount();
        User mockUser = getMockUser();
        mockAccount.setUserId(null);

        when(accountRepository.add(mockAccount)).thenReturn(mockAccount);
        when(userRepository.findById(mockAccount.getUserId())).thenReturn(mockUser);

        ServiceException thrown =
                assertThrows(ServiceException.class, () -> accountService.add(mockAccount));

        assertTrue(thrown.getAppMessage().contains("Invalid user id"));
    }

    @Test
    @DisplayName("Test add an account when invalid account type use-case")
    public void testAddAccountInvalidAccountTypeException() {
        Account mockAccount = getMockAccount();
        User mockUser = getMockUser();
        mockAccount.setAccountType(3);

        when(accountRepository.add(mockAccount)).thenReturn(mockAccount);
        when(userRepository.findById(mockAccount.getUserId())).thenReturn(mockUser);

        ServiceException thrown =
                assertThrows(ServiceException.class, () -> accountService.add(mockAccount));

        assertTrue(thrown.getAppMessage().contains("Invalid account type"));
    }

    @Test
    @DisplayName("Test add an account when invalid user use-case")
    public void testAddAccountInvalidUserException() {
        Account mockAccount = getMockAccount();
        User mockUser = getMockUser();
        mockUser.setId(null);

        when(accountRepository.add(mockAccount)).thenReturn(mockAccount);
        when(userRepository.findById(mockAccount.getUserId())).thenReturn(mockUser);

        ServiceException thrown =
                assertThrows(ServiceException.class, () -> accountService.add(mockAccount));

        assertTrue(thrown.getAppMessage().contains("Cannot find the user"));
    }

    @Test
    @DisplayName("Test add an account use-case")
    public void testAddAccount() {
        Account mockAccount = getMockAccount();
        User mockUser = getMockUser();

        when(accountRepository.add(mockAccount)).thenReturn(mockAccount);
        when(userRepository.findById(mockAccount.getUserId())).thenReturn(mockUser);

        Account actualAccount = accountService.add(mockAccount);

        assertThat(actualAccount, allOf(
                hasProperty(ID, is(mockAccount.getId())),
                hasProperty(USER_ID, is(mockAccount.getUserId())),
                hasProperty(ACCOUNT_BALANCE, is(mockAccount.getAccountBalance())),
                hasProperty(ACCOUNT_TYPE, is(mockAccount.getAccountType())),
                hasProperty(OPEN, is(mockAccount.getOpen()))
        ));

        verify(accountRepository, times(1)).add(mockAccount);
        verify(userRepository, times(1)).findById(mockAccount.getUserId());
    }

    @Test
    @DisplayName("Test update an account when user id is null use-case")
    public void testUpdateAccountUserIdException() {
        Account mockAccount = getMockAccount();
        User mockUser = getMockUser();
        mockAccount.setUserId(null);

        when(accountRepository.update(mockAccount)).thenReturn(mockAccount);
        when(userRepository.findById(mockAccount.getUserId())).thenReturn(mockUser);

        ServiceException thrown =
                assertThrows(ServiceException.class, () -> accountService.update(mockAccount));

        assertTrue(thrown.getAppMessage().contains("Invalid user id"));
    }

    @Test
    @DisplayName("Test update an account when invalid account type use-case")
    public void testUpdateAccountInvalidAccountTypeException() {
        Account mockAccount = getMockAccount();
        User mockUser = getMockUser();
        mockAccount.setAccountType(3);

        when(accountRepository.update(mockAccount)).thenReturn(mockAccount);
        when(userRepository.findById(mockAccount.getUserId())).thenReturn(mockUser);

        ServiceException thrown =
                assertThrows(ServiceException.class, () -> accountService.update(mockAccount));

        assertTrue(thrown.getAppMessage().contains("Invalid account type"));
    }

    @Test
    @DisplayName("Test update an account when invalid user use-case")
    public void testUpdateAccountInvalidUserException() {
        Account mockAccount = getMockAccount();
        User mockUser = getMockUser();
        mockUser.setId(null);

        when(accountRepository.update(mockAccount)).thenReturn(mockAccount);
        when(userRepository.findById(mockAccount.getUserId())).thenReturn(mockUser);

        ServiceException thrown =
                assertThrows(ServiceException.class, () -> accountService.update(mockAccount));

        assertTrue(thrown.getAppMessage().contains("Cannot find the user"));
    }

    @Test
    @DisplayName("Test update an account use-case")
    public void testUpdateAccount() {
        Account mockAccount = getMockAccount();
        User mockUser = getMockUser();

        when(accountRepository.update(mockAccount)).thenReturn(mockAccount);
        when(userRepository.findById(mockAccount.getUserId())).thenReturn(mockUser);

        Account actualAccount = accountService.update(mockAccount);

        assertThat(actualAccount, allOf(
                hasProperty(ID, is(mockAccount.getId())),
                hasProperty(USER_ID, is(mockAccount.getUserId())),
                hasProperty(ACCOUNT_BALANCE, is(mockAccount.getAccountBalance())),
                hasProperty(ACCOUNT_TYPE, is(mockAccount.getAccountType())),
                hasProperty(OPEN, is(mockAccount.getOpen()))
        ));

        verify(accountRepository, times(1)).update(mockAccount);
        verify(userRepository, times(1)).findById(mockAccount.getUserId());
    }

    @Test
    @DisplayName("Test get all accounts use-case")
    public void testGetAllAccounts() {
        Account mockAccount1 = getMockAccount();
        Account mockAccount2 = getMockAccount();
        mockAccount2.setId(2);

        List<Account> mockAccounts = new ArrayList<>();
        mockAccounts.add(mockAccount1);
        mockAccounts.add(mockAccount2);

        when(accountRepository.getAll()).thenReturn(mockAccounts);

        List<Account> actualAccounts = accountService.getAll();

        assertThat(actualAccounts.get(0), allOf(
                hasProperty(ID, is(mockAccount1.getId())),
                hasProperty(USER_ID, is(mockAccount1.getUserId())),
                hasProperty(ACCOUNT_BALANCE, is(mockAccount1.getAccountBalance())),
                hasProperty(ACCOUNT_TYPE, is(mockAccount1.getAccountType())),
                hasProperty(OPEN, is(mockAccount1.getOpen()))
        ));

        assertEquals(actualAccounts.size(), mockAccounts.size());

        verify(accountRepository, times(1)).getAll();
    }

    @Test
    @DisplayName("Test get account by it's id use-case")
    public void testFindAccountById() {
        Account mockAccount = getMockAccount();

        when(accountRepository.findById(mockAccount.getId())).thenReturn(mockAccount);

        Account actualAccount = accountService.findById(mockAccount.getId().toString());

        assertThat(actualAccount, allOf(
                hasProperty(ID, is(mockAccount.getId())),
                hasProperty(USER_ID, is(mockAccount.getUserId())),
                hasProperty(ACCOUNT_BALANCE, is(mockAccount.getAccountBalance())),
                hasProperty(ACCOUNT_TYPE, is(mockAccount.getAccountType())),
                hasProperty(OPEN, is(mockAccount.getOpen()))
        ));

        verify(accountRepository, times(1)).findById(mockAccount.getId());
    }

    @Test
    @DisplayName("Test close account use-case")
    public void testCloseAccount() {
        Account mockAccount = getMockAccount();

        when(accountRepository.delete(mockAccount.getId())).thenReturn(true);

        boolean actual = accountService.close(mockAccount.getId().toString());

        assertTrue(actual);

        verify(accountRepository, times(1)).delete(mockAccount.getId());
    }

    @Test
    @DisplayName("Test get account by user id use-case")
    public void testFindAccountByUserId() {
        Account mockAccount1 = getMockAccount();
        Account mockAccount2 = getMockAccount();
        mockAccount2.setId(2);

        List<Account> mockAccounts = new ArrayList<>();
        mockAccounts.add(mockAccount1);
        mockAccounts.add(mockAccount2);

        when(accountRepository.findByUser(mockAccount1.getUserId())).thenReturn(mockAccounts);

        List<Account> actualAccounts = accountService.findByUser(mockAccount1.getUserId().toString());

        assertThat(actualAccounts.get(0), allOf(
                hasProperty(ID, is(mockAccount1.getId())),
                hasProperty(USER_ID, is(mockAccount1.getUserId())),
                hasProperty(ACCOUNT_BALANCE, is(mockAccount1.getAccountBalance())),
                hasProperty(ACCOUNT_TYPE, is(mockAccount1.getAccountType())),
                hasProperty(OPEN, is(mockAccount1.getOpen()))
        ));

        assertEquals(actualAccounts.size(), mockAccounts.size());

        verify(accountRepository, times(1)).findByUser(mockAccount1.getUserId());
    }

    @Test
    @DisplayName("Test get account by user id and account type use-case")
    public void testFindAccountByUserIdAndAccountType() {
        Account mockAccount1 = getMockAccount();
        Account mockAccount2 = getMockAccount();
        mockAccount2.setId(2);

        List<Account> mockAccounts = new ArrayList<>();
        mockAccounts.add(mockAccount1);
        mockAccounts.add(mockAccount2);

        when(accountRepository.findByUserAndAccountType(mockAccount1.getUserId(), mockAccount1.getAccountType()))
                .thenReturn(mockAccounts);

        List<Account> actualAccounts = accountService
                .findByUserAndAccountType(mockAccount1.getUserId().toString(),
                        mockAccount1.getAccountType().toString());

        assertThat(actualAccounts.get(0), allOf(
                hasProperty(ID, is(mockAccount1.getId())),
                hasProperty(USER_ID, is(mockAccount1.getUserId())),
                hasProperty(ACCOUNT_BALANCE, is(mockAccount1.getAccountBalance())),
                hasProperty(ACCOUNT_TYPE, is(mockAccount1.getAccountType())),
                hasProperty(OPEN, is(mockAccount1.getOpen()))
        ));

        assertEquals(actualAccounts.size(), mockAccounts.size());

        verify(accountRepository, times(1))
                .findByUserAndAccountType(mockAccount1.getUserId(), mockAccount1.getAccountType());
    }

    @Test
    @DisplayName("Test get account when invalid account type use-case")
    public void testFindAccountWhenInvalidAccountType() {
        Account mockAccount1 = getMockAccount();
        Account mockAccount2 = getMockAccount();
        mockAccount2.setId(2);

        List<Account> mockAccounts = new ArrayList<>();
        mockAccounts.add(mockAccount1);
        mockAccounts.add(mockAccount2);

        when(accountRepository.findByUserAndAccountType(mockAccount1.getUserId(), 4))
                .thenReturn(mockAccounts);

        ServiceException thrown =
                assertThrows(ServiceException.class, () -> accountService
                        .findByUserAndAccountType(mockAccount1.getUserId().toString(),
                                String.valueOf(4)));

        assertTrue(thrown.getAppMessage().contains("Invalid account type"));
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
                .country("SER")
                .currency("DIN")
                .creationDate(new Timestamp(1))
                .updateDate(new Timestamp(1))
                .build();
    }
}
