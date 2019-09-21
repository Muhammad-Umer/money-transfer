package com.revolut.moneytransfer.controller;

import com.despegar.http.client.DeleteMethod;
import com.despegar.http.client.GetMethod;
import com.despegar.http.client.HttpClientException;
import com.despegar.http.client.HttpResponse;
import com.despegar.http.client.PatchMethod;
import com.despegar.http.client.PostMethod;
import com.despegar.http.client.PutMethod;
import com.despegar.sparkjava.test.SparkServer;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.revolut.moneytransfer.configurations.DatasourceConfiguration;
import com.revolut.moneytransfer.dto.Account;
import com.revolut.moneytransfer.dto.Transaction;
import com.revolut.moneytransfer.dto.User;
import com.revolut.moneytransfer.repository.AccountRepository;
import com.revolut.moneytransfer.repository.TransactionRepository;
import com.revolut.moneytransfer.repository.UserRepository;
import com.revolut.moneytransfer.repository.impl.AccountRepositoryImpl;
import com.revolut.moneytransfer.repository.impl.TransactionRepositoryImpl;
import com.revolut.moneytransfer.repository.impl.UserRepositoryImpl;
import com.revolut.moneytransfer.type.application.AccountType;
import lombok.extern.slf4j.Slf4j;
import org.junit.ClassRule;
import org.junit.Test;
import spark.servlet.SparkApplication;

import java.beans.PropertyVetoException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@Slf4j
public class ControllerTest {
    private static final String INIT_ERROR = "Server cannot be started: {}";

    private AccountRepository accountRepository = AccountRepositoryImpl.getInstance();
    private UserRepository userRepository = UserRepositoryImpl.getInstance();
    private TransactionRepository transactionRepository = TransactionRepositoryImpl.getInstance();

    public static class ControllerTestApplication implements SparkApplication {
        @Override
        public void init() {
            try {
                DatasourceConfiguration.getDatasource().populate();
            } catch (SQLException | PropertyVetoException e) {
                log.error(INIT_ERROR, e);
            }

            AccountController accountController = new AccountController();
            accountController.registerController();

            UserController userController = new UserController();
            userController.registerController();

            TransactionController transactionController = new TransactionController();
            transactionController.registerController();
        }

        @Override
        public void destroy() {
            log.info("Test application stopped");
        }
    }

    @ClassRule
    public static SparkServer<ControllerTestApplication> testServer =
            new SparkServer<>(ControllerTestApplication.class, 2222);

    @Test
    public void testAddUser() throws HttpClientException {
        User mockUser = getMockUser();
        PutMethod put = testServer.
                put("/user/", new Gson().toJson(mockUser), false);
        HttpResponse httpResponse = testServer.execute(put);
        User response = new Gson().fromJson(new String(httpResponse.body()), User.class);

        assertEquals(response.getId(), Integer.valueOf(7));

        assertNotNull(testServer.getApplication());
    }

    @Test
    public void testUpdateUser() throws HttpClientException {
        User mockUser = getMockUser();
        PatchMethod patch = testServer.
                patch("/user/", new Gson().toJson(mockUser), false);
        HttpResponse httpResponse = testServer.execute(patch);
        User response = new Gson().fromJson(new String(httpResponse.body()), User.class);

        assertEquals(response.getFirstName(), mockUser.getFirstName());

        assertNotNull(testServer.getApplication());
    }

    @Test
    public void testDeleteUser() throws HttpClientException {
        DeleteMethod delete = testServer.
                delete("/user/id/1", false);
        HttpResponse httpResponse = testServer.execute(delete);
        boolean response = new Gson().fromJson(new String(httpResponse.body()), Boolean.class);

        assertTrue(response);

        assertNotNull(testServer.getApplication());
    }

    @Test
    public void testGetAllUsers() throws HttpClientException {
        List<User> userList = userRepository.getAll();
        GetMethod get = testServer.get("/user/all", false);
        HttpResponse httpResponse = testServer.execute(get);
        List<User> response = new Gson().fromJson(new String(httpResponse.body()),
                new TypeToken<List<User>>() {}.getType());

        if (!response.isEmpty()) {
            assertThat(response.get(0).getId(), is(userList.get(0).getId()));
            assertThat(response.get(0).getFirstName(), is(userList.get(0).getFirstName()));
            assertThat(response.get(0).getLastName(), is(userList.get(0).getLastName()));
            assertThat(response.get(0).getEmail(), is(userList.get(0).getEmail()));
            assertThat(response.get(0).getCountry(), is(userList.get(0).getCountry()));
            assertThat(response.get(0).getCurrency(), is(userList.get(0).getCurrency()));
        }

        assertNotNull(testServer.getApplication());
    }

    @Test
    public void testGetUserById() throws HttpClientException {
        User user = userRepository.findById(1);
        GetMethod get = testServer.get("/user/id/1", false);
        HttpResponse httpResponse = testServer.execute(get);
        User response = new Gson().fromJson(new String(httpResponse.body()), User.class);

        assertThat(response.getId(), is(response.getId()));
        assertThat(response.getFirstName(), is(user.getFirstName()));
        assertThat(response.getLastName(), is(user.getLastName()));
        assertThat(response.getEmail(), is(user.getEmail()));
        assertThat(response.getCountry(), is(user.getCountry()));
        assertThat(response.getCurrency(), is(user.getCurrency()));

        assertNotNull(testServer.getApplication());
    }

    @Test
    public void testGetAllAccounts() throws HttpClientException {
        List<Account> accountList = accountRepository.getAll();
        GetMethod get = testServer.get("/account/all", false);
        HttpResponse httpResponse = testServer.execute(get);
        List<Account> response = new Gson().fromJson(new String(httpResponse.body()),
                new TypeToken<List<Account>>() {}.getType());

        if (!response.isEmpty()) {
            assertThat(response.get(0).getId(), is(accountList.get(0).getId()));
            assertThat(response.get(0).getUserId(), is(accountList.get(0).getUserId()));
            assertThat(response.get(0).getAccountBalance(), is(accountList.get(0).getAccountBalance()));
            assertThat(response.get(0).getAccountType(), is(accountList.get(0).getAccountType()));
            assertThat(response.get(0).getOpen(), is(accountList.get(0).getOpen()));
        }

        assertNotNull(testServer.getApplication());
    }

    @Test
    public void testGetAccountById() throws HttpClientException {
        Account account = accountRepository.findById(1);
        GetMethod get = testServer.get("/account/id/1", false);
        HttpResponse httpResponse = testServer.execute(get);
        Account response = new Gson().fromJson(new String(httpResponse.body()), Account.class);

        assertThat(response.getId(), is(account.getId()));
        assertThat(response.getUserId(), is(account.getUserId()));
        assertThat(response.getAccountBalance(), is(account.getAccountBalance()));
        assertThat(response.getAccountType(), is(account.getAccountType()));
        assertThat(response.getOpen(), is(account.getOpen()));

        assertNotNull(testServer.getApplication());
    }

    @Test
    public void testGetAccountByUserId() throws HttpClientException {
        Account mockAccount = getMockAccount();
        List<Account> accountList = accountRepository.findByUser(mockAccount.getUserId());
        GetMethod get = testServer.get("/account/user/" + mockAccount.getUserId(), false);
        HttpResponse httpResponse = testServer.execute(get);
        List<Account> response = new Gson().fromJson(new String(httpResponse.body()),
                new TypeToken<List<Account>>() {}.getType());

        if (!response.isEmpty()) {
            assertThat(response.get(0).getId(), is(accountList.get(0).getId()));
            assertThat(response.get(0).getUserId(), is(accountList.get(0).getUserId()));
            assertThat(response.get(0).getAccountBalance(), is(accountList.get(0).getAccountBalance()));
            assertThat(response.get(0).getAccountType(), is(accountList.get(0).getAccountType()));
            assertThat(response.get(0).getOpen(), is(accountList.get(0).getOpen()));
        }

        assertNotNull(testServer.getApplication());
    }

    @Test
    public void testGetAccountByUserIdAndAccountType() throws HttpClientException {
        Account mockAccount = getMockAccount();
        String path = "/account/user/" + mockAccount.getUserId() + "/accountType/" + mockAccount.getAccountType();

        List<Account> accountList = accountRepository.
                findByUserAndAccountType(mockAccount.getUserId(), mockAccount.getAccountType());
        GetMethod get = testServer.get(path, false);
        HttpResponse httpResponse = testServer.execute(get);
        List<Account> response = new Gson().fromJson(new String(httpResponse.body()),
                new TypeToken<List<Account>>() {}.getType());

        if (!response.isEmpty()) {
            assertThat(response.get(0).getId(), is(accountList.get(0).getId()));
            assertThat(response.get(0).getUserId(), is(accountList.get(0).getUserId()));
            assertThat(response.get(0).getAccountBalance(), is(accountList.get(0).getAccountBalance()));
            assertThat(response.get(0).getAccountType(), is(accountList.get(0).getAccountType()));
            assertThat(response.get(0).getOpen(), is(accountList.get(0).getOpen()));
        }

        assertNotNull(testServer.getApplication());
    }

    @Test
    public void testAddAccount() throws HttpClientException {
        Account mockAccount = getMockAccount();
        PutMethod put = testServer.
                put("/account/", new Gson().toJson(mockAccount), false);
        HttpResponse httpResponse = testServer.execute(put);
        Account response = new Gson().fromJson(new String(httpResponse.body()), Account.class);

        assertEquals(response.getId(), Integer.valueOf(7));

        assertNotNull(testServer.getApplication());
    }

    @Test
    public void testUpdateAccount() throws HttpClientException {
        Account mockAccount = getMockAccount();
        PatchMethod patch = testServer.
                patch("/account/", new Gson().toJson(mockAccount), false);
        HttpResponse httpResponse = testServer.execute(patch);
        Account response = new Gson().fromJson(new String(httpResponse.body()), Account.class);

        assertEquals(response.getId(), mockAccount.getId());
        assertEquals(response.getAccountBalance(), mockAccount.getAccountBalance());

        assertNotNull(testServer.getApplication());
    }

    @Test
    public void testCloseAccount() throws HttpClientException {
        Account mockAccount = getMockAccount();
        DeleteMethod delete = testServer.
                delete("/account/id/" + mockAccount.getId(), false);
        HttpResponse httpResponse = testServer.execute(delete);
        boolean response = new Gson().fromJson(new String(httpResponse.body()), Boolean.class);

        assertTrue(response);

        assertNotNull(testServer.getApplication());
    }

    @Test
    public void testDeposit() throws HttpClientException {
        Account mockAccount = getMockAccount();
        mockAccount.setUserId(2);
        mockAccount.setId(2);
        String path = "/account/deposit/" + mockAccount.getId() + "/amount/" + BigDecimal.TEN;
        PostMethod post = testServer.
                post(path, "",false);
        HttpResponse httpResponse = testServer.execute(post);
        Transaction response = new Gson().fromJson(new String(httpResponse.body()), Transaction.class);

        assertEquals(response.getAmount(), BigDecimal.TEN);

        assertNotNull(testServer.getApplication());
    }

    @Test
    public void testWithdraw() throws HttpClientException {
        Account mockAccount = getMockAccount();
        String path = "/account/withdraw/" + mockAccount.getId() + "/amount/" + BigDecimal.TEN;
        PostMethod post = testServer.
                post(path, "",false);
        HttpResponse httpResponse = testServer.execute(post);
        Transaction response = new Gson().fromJson(new String(httpResponse.body()), Transaction.class);

        assertEquals(response.getAmount(), BigDecimal.TEN.multiply(new BigDecimal(-1)));

        assertNotNull(testServer.getApplication());
    }

    @Test
    public void testTransfer() throws HttpClientException {
        String path = "/account/transfer/sender/2/recipient/3/amount/10";
        PostMethod post = testServer.
                post(path, "",false);
        HttpResponse httpResponse = testServer.execute(post);
        Transaction response = new Gson().fromJson(new String(httpResponse.body()), Transaction.class);

        assertEquals(response.getAmount(), BigDecimal.TEN);

        assertNotNull(testServer.getApplication());
    }

    @Test
    public void testAddTransaction() throws HttpClientException {
        Transaction mockTransaction = getMockTransaction();
        mockTransaction.setFromAccount(3);
        mockTransaction.setToAccount(4);
        PutMethod put = testServer.
                put("/transaction/", new Gson().toJson(mockTransaction), false);
        HttpResponse httpResponse = testServer.execute(put);
        Transaction response = new Gson().fromJson(new String(httpResponse.body()), Transaction.class);

        assertEquals(response.getId(), Integer.valueOf(7));

        assertNotNull(testServer.getApplication());
    }

    @Test
    public void testGetTransactionById() throws HttpClientException {
        Transaction transaction = transactionRepository.findById(1);
        GetMethod get = testServer.get("/transaction/id/1", false);
        HttpResponse httpResponse = testServer.execute(get);
        Transaction response = new Gson().fromJson(new String(httpResponse.body()), Transaction.class);

        assertThat(response.getId(), is(transaction.getId()));
        assertThat(response.getFromAccount(), is(transaction.getFromAccount()));
        assertThat(response.getToAccount(), is(transaction.getToAccount()));
        assertThat(response.getAmount(), is(transaction.getAmount()));

        assertNotNull(testServer.getApplication());
    }

    @Test
    public void testGetDebitTransactionById() throws HttpClientException {
        List<Transaction> transactionList = transactionRepository.getDebitTransactions(1);
        GetMethod get = testServer.get("/transaction/debit/account/1", false);
        HttpResponse httpResponse = testServer.execute(get);
        List<Transaction> response = new Gson().fromJson(new String(httpResponse.body()),
                new TypeToken<List<Transaction>>() {}.getType());

        if (!response.isEmpty()) {
            assertThat(response.get(0).getId(), is(transactionList.get(0).getId()));
            assertThat(response.get(0).getFromAccount(), is(transactionList.get(0).getFromAccount()));
            assertThat(response.get(0).getToAccount(), is(transactionList.get(0).getToAccount()));
            assertThat(response.get(0).getAmount(), is(transactionList.get(0).getAmount()));
        }

        assertNotNull(testServer.getApplication());
    }

    @Test
    public void testGetCreditTransactionById() throws HttpClientException {
        List<Transaction> transactionList = transactionRepository.getDebitTransactions(1);
        GetMethod get = testServer.get("/transaction/credit/account/2", false);
        HttpResponse httpResponse = testServer.execute(get);
        List<Transaction> response = new Gson().fromJson(new String(httpResponse.body()),
                new TypeToken<List<Transaction>>() {}.getType());

        if (!response.isEmpty()) {
            assertThat(response.get(0).getId(), is(transactionList.get(0).getId()));
            assertThat(response.get(0).getFromAccount(), is(transactionList.get(0).getFromAccount()));
            assertThat(response.get(0).getToAccount(), is(transactionList.get(0).getToAccount()));
            assertThat(response.get(0).getAmount(), is(transactionList.get(0).getAmount()));
        }

        assertNotNull(testServer.getApplication());
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
