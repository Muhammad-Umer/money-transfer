package com.revolut.moneytransfer.repository.impl;

import com.revolut.moneytransfer.configurations.DatasourceConfiguration;
import com.revolut.moneytransfer.exception.RepositoryException;
import com.revolut.moneytransfer.dto.Account;
import com.revolut.moneytransfer.repository.AccountRepository;
import com.revolut.moneytransfer.type.error.AccountErrorType;
import com.revolut.moneytransfer.type.error.GenericErrorType;
import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.revolut.moneytransfer.utils.DataFields.AccountFields.ACCOUNT_BALANCE;
import static com.revolut.moneytransfer.utils.DataFields.AccountFields.ACCOUNT_TYPE;
import static com.revolut.moneytransfer.utils.DataFields.AccountFields.OPEN;
import static com.revolut.moneytransfer.utils.DataFields.AccountFields.USER_ID;
import static com.revolut.moneytransfer.utils.DataFields.CREATION_DATE;
import static com.revolut.moneytransfer.utils.DataFields.ID;
import static com.revolut.moneytransfer.utils.DataFields.UPDATE_DATE;

@Slf4j
public class AccountRepositoryImpl implements AccountRepository {

    private ReadWriteLock accountDatabaseLock = new ReentrantReadWriteLock();

    private static AccountRepositoryImpl accountRepositoryImpl = null;

    public static synchronized AccountRepositoryImpl getInstance() {
        if(accountRepositoryImpl == null ) {
            accountRepositoryImpl = new AccountRepositoryImpl();
        }
        return accountRepositoryImpl;
    }

    @Override
    public Account add(Account account) {
        try (Connection connection = DatasourceConfiguration.getDatasource().getConnection();
             PreparedStatement preparedStatement = connection.
                     prepareStatement("INSERT INTO account VALUES (NULL, ?, ?, ?, TRUE, ?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, account.getUserId());
            preparedStatement.setInt(2, account.getAccountType());
            preparedStatement.setBigDecimal(3, account.getAccountBalance());
            preparedStatement.setTimestamp(4, account.getCreationDate());
            preparedStatement.setTimestamp(5, account.getUpdateDate());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        return findById(id);
                    }
                }
            }

            throw new RepositoryException(AccountErrorType.ACCOUNT_NOT_CREATED);
        } catch (SQLException e) {
            throw new RepositoryException(e, GenericErrorType.SOMETHING_WENT_WRONG);
        } catch (PropertyVetoException e) {
            throw new RepositoryException(e, AccountErrorType.ACCOUNT_NOT_CREATED);
        }
    }

    @Override
    public Account update(Account account) {
        accountDatabaseLock.writeLock().lock();
        try (Connection connection = DatasourceConfiguration.getDatasource().getConnection();
             PreparedStatement preparedStatement = connection.
                     prepareStatement("UPDATE account SET user_id = ?, account_balance = ?, " +
                             "account_type = ?, open = ?, update_date = ? WHERE id = ?")) {

            preparedStatement.setInt(1, account.getUserId());
            preparedStatement.setBigDecimal(2, account.getAccountBalance());
            preparedStatement.setInt(3, account.getAccountType());
            preparedStatement.setBoolean(4, account.getOpen());
            preparedStatement.setTimestamp(5, account.getUpdateDate());
            preparedStatement.setInt(6, account.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                return findById(account.getId());
            }

            throw new RepositoryException(AccountErrorType.ACCOUNT_NOT_UPDATED);
        } catch (SQLException e) {
            throw new RepositoryException(e, GenericErrorType.SOMETHING_WENT_WRONG);
        } catch (PropertyVetoException e) {
            throw new RepositoryException(e, AccountErrorType.ACCOUNT_NOT_UPDATED);
        } finally {
            accountDatabaseLock.writeLock().unlock();
        }
    }

    @Override
    public boolean delete(Integer id) {
        accountDatabaseLock.writeLock().lock();
        try (Connection connection = DatasourceConfiguration.getDatasource().getConnection();
             PreparedStatement preparedStatement = connection.
                     prepareStatement("UPDATE account SET open = ?, update_date = ? WHERE id = ?")) {

            preparedStatement.setBoolean(1, false);
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(3, id);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected == 1;
        } catch (SQLException e) {
            throw new RepositoryException(e, GenericErrorType.SOMETHING_WENT_WRONG);
        } catch (PropertyVetoException e) {
            throw new RepositoryException(e, AccountErrorType.ACCOUNT_NOT_UPDATED);
        } finally {
            accountDatabaseLock.writeLock().unlock();
        }
    }

    @Override
    public Account findById(Integer id) {
        accountDatabaseLock.readLock().lock();
        try (Connection connection = DatasourceConfiguration.getDatasource().getConnection();
             PreparedStatement preparedStatement = connection.
                     prepareStatement("SELECT * FROM account WHERE id = ? AND open = TRUE")) {

            Account account = new Account();
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    account = Account.builder()
                            .id(resultSet.getInt(ID))
                            .userId(resultSet.getInt(USER_ID))
                            .accountBalance(resultSet.getBigDecimal(ACCOUNT_BALANCE))
                            .accountType(resultSet.getInt(ACCOUNT_TYPE))
                            .open(resultSet.getBoolean(OPEN))
                            .creationDate(resultSet.getTimestamp(CREATION_DATE))
                            .updateDate(resultSet.getTimestamp(UPDATE_DATE))
                            .build();
                }
            }
            return account;

        } catch (SQLException e) {
            throw new RepositoryException(e, GenericErrorType.SOMETHING_WENT_WRONG);
        } catch (PropertyVetoException e) {
            throw new RepositoryException(e, AccountErrorType.CANNOT_FIND_ACCOUNT);
        } finally {
            accountDatabaseLock.readLock().unlock();
        }
    }

    @Override
    public List<Account> getAll() {
        try (Connection connection = DatasourceConfiguration.getDatasource().getConnection();
             PreparedStatement preparedStatement = connection.
                     prepareStatement("SELECT * FROM account WHERE open = TRUE")) {

            List<Account> accounts = new ArrayList<>();

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Account account = Account.builder()
                            .id(resultSet.getInt(ID))
                            .userId(resultSet.getInt(USER_ID))
                            .accountBalance(resultSet.getBigDecimal(ACCOUNT_BALANCE))
                            .accountType(resultSet.getInt(ACCOUNT_TYPE))
                            .open(resultSet.getBoolean(OPEN))
                            .creationDate(resultSet.getTimestamp(CREATION_DATE))
                            .updateDate(resultSet.getTimestamp(UPDATE_DATE))
                            .build();

                    accounts.add(account);
                }
            }
            return accounts;

        } catch (SQLException e) {
            throw new RepositoryException(e, GenericErrorType.SOMETHING_WENT_WRONG);
        } catch (PropertyVetoException e) {
            throw new RepositoryException(e, AccountErrorType.CANNOT_FIND_ACCOUNT);
        }
    }

    @Override
    public List<Account> findByUser(Integer userId) {
        try (Connection connection = DatasourceConfiguration.getDatasource().getConnection();
             PreparedStatement preparedStatement = connection.
                     prepareStatement("SELECT * FROM account WHERE user_id = ?")) {

            List<Account> accounts = new ArrayList<>();
            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Account account = Account.builder()
                            .id(resultSet.getInt(ID))
                            .userId(resultSet.getInt(USER_ID))
                            .accountBalance(resultSet.getBigDecimal(ACCOUNT_BALANCE))
                            .accountType(resultSet.getInt(ACCOUNT_TYPE))
                            .creationDate(resultSet.getTimestamp(CREATION_DATE))
                            .updateDate(resultSet.getTimestamp(UPDATE_DATE))
                            .build();

                    accounts.add(account);
                }
            }
            return accounts;

        } catch (SQLException e) {
            throw new RepositoryException(e, GenericErrorType.SOMETHING_WENT_WRONG);
        } catch (PropertyVetoException e) {
            throw new RepositoryException(e, AccountErrorType.CANNOT_FIND_ACCOUNT);
        }
    }

    @Override
    public List<Account> findByUserAndAccountType(Integer userId, Integer accountType) {
        try (Connection connection = DatasourceConfiguration.getDatasource().getConnection();
             PreparedStatement preparedStatement = connection.
                     prepareStatement("SELECT * FROM account WHERE user_id = ? AND account_type = ?")) {

            List<Account> accounts = new ArrayList<>();
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, accountType);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Account account = Account.builder()
                            .id(resultSet.getInt(ID))
                            .userId(resultSet.getInt(USER_ID))
                            .accountBalance(resultSet.getBigDecimal(ACCOUNT_BALANCE))
                            .accountType(resultSet.getInt(ACCOUNT_TYPE))
                            .creationDate(resultSet.getTimestamp(CREATION_DATE))
                            .updateDate(resultSet.getTimestamp(UPDATE_DATE))
                            .build();

                    accounts.add(account);
                }
            }
            return accounts;

        } catch (SQLException e) {
            throw new RepositoryException(e, GenericErrorType.SOMETHING_WENT_WRONG);
        } catch (PropertyVetoException e) {
            throw new RepositoryException(e, AccountErrorType.CANNOT_FIND_ACCOUNT);
        }
    }
}
