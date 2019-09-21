package com.revolut.moneytransfer.repository.impl;

import com.revolut.moneytransfer.configurations.DatasourceConfiguration;
import com.revolut.moneytransfer.dto.Transaction;
import com.revolut.moneytransfer.exception.RepositoryException;
import com.revolut.moneytransfer.repository.TransactionRepository;
import com.revolut.moneytransfer.type.error.GenericErrorType;
import com.revolut.moneytransfer.type.error.TransactionErrorType;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import static com.revolut.moneytransfer.utils.DataFields.CREATION_DATE;
import static com.revolut.moneytransfer.utils.DataFields.ID;
import static com.revolut.moneytransfer.utils.DataFields.TransactionFields.AMOUNT;
import static com.revolut.moneytransfer.utils.DataFields.TransactionFields.FROM_ACCOUNT;
import static com.revolut.moneytransfer.utils.DataFields.TransactionFields.TO_ACCOUNT;
import static com.revolut.moneytransfer.utils.DataFields.UPDATE_DATE;

public class TransactionRepositoryImpl implements TransactionRepository {

    private ReadWriteLock transactionDatabaseLock = new ReentrantReadWriteLock();

    private static TransactionRepositoryImpl transactionRepositoryImpl = null;

    public static synchronized TransactionRepositoryImpl getInstance() {
        if(transactionRepositoryImpl == null ) {
            transactionRepositoryImpl = new TransactionRepositoryImpl();
        }
        return transactionRepositoryImpl;
    }

    @Override
    public Transaction add(Transaction transaction) {
        try (Connection connection = DatasourceConfiguration.getDatasource().getConnection();
             PreparedStatement preparedStatement = connection.
                     prepareStatement("INSERT INTO transaction VALUES (NULL, ?, ?, ?, ?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, transaction.getFromAccount());
            preparedStatement.setInt(2, transaction.getToAccount());
            preparedStatement.setBigDecimal(3, transaction.getAmount());
            preparedStatement.setTimestamp(4, transaction.getCreationDate());
            preparedStatement.setTimestamp(5, transaction.getUpdateDate());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        return findById(id);
                    }
                }
            }

            throw new RepositoryException(TransactionErrorType.TRANSACTION_NOT_CREATED);
        } catch (SQLException e) {
            throw new RepositoryException(e, GenericErrorType.SOMETHING_WENT_WRONG);
        } catch (PropertyVetoException e) {
            throw new RepositoryException(e, TransactionErrorType.TRANSACTION_NOT_CREATED);
        }
    }

    @Override
    public Transaction update(Transaction transaction) {
        transactionDatabaseLock.writeLock().lock();
        try (Connection connection = DatasourceConfiguration.getDatasource().getConnection();
             PreparedStatement preparedStatement = connection.
                     prepareStatement("UPDATE transaction SET from_account = ?, to_account = ?, " +
                             "amount = ?, update_date = ? WHERE id = ?")) {

            preparedStatement.setInt(1, transaction.getFromAccount());
            preparedStatement.setInt(2, transaction.getFromAccount());
            preparedStatement.setBigDecimal(3, transaction.getAmount());
            preparedStatement.setTimestamp(4, transaction.getUpdateDate());
            preparedStatement.setInt(5, transaction.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                return findById(transaction.getId());
            }

            throw new RepositoryException(TransactionErrorType.TRANSACTION_NOT_UPDATED);
        } catch (SQLException e) {
            throw new RepositoryException(e, GenericErrorType.SOMETHING_WENT_WRONG);
        } catch (PropertyVetoException e) {
            throw new RepositoryException(e, TransactionErrorType.TRANSACTION_NOT_UPDATED);
        } finally {
            transactionDatabaseLock.writeLock().unlock();
        }
    }

    /**
     *
     * @param id
     * @return Unsupported operation. Transactions should never be deleted for P&L etc
     */
    @Override
    public boolean delete(Integer id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Transaction findById(Integer id) {
        transactionDatabaseLock.readLock().lock();
        try (Connection connection = DatasourceConfiguration.getDatasource().getConnection();
             PreparedStatement preparedStatement = connection.
                     prepareStatement("SELECT * FROM transaction WHERE id = ?")) {

            Transaction transaction = new Transaction();
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    transaction = Transaction.builder()
                            .id(resultSet.getInt(ID))
                            .fromAccount(resultSet.getInt(FROM_ACCOUNT))
                            .toAccount(resultSet.getInt(TO_ACCOUNT))
                            .amount(resultSet.getBigDecimal(AMOUNT))
                            .creationDate(resultSet.getTimestamp(CREATION_DATE))
                            .updateDate(resultSet.getTimestamp(UPDATE_DATE))
                            .build();
                }
            }
            return transaction;

        } catch (SQLException e) {
            throw new RepositoryException(e, GenericErrorType.SOMETHING_WENT_WRONG);
        } catch (PropertyVetoException e) {
            throw new RepositoryException(e, TransactionErrorType.CANNOT_FIND_TRANSACTION);
        } finally {
            transactionDatabaseLock.readLock().lock();
        }
    }

    /**
     *
     * @return Unsupported operation. No use case to have all transactions listed as data-size would be
     * huge. So this should not be implemented without pagination.
     */
    @Override
    public List<Transaction> getAll() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Transaction> getDebitTransactions(Integer accountId) {
        try (Connection connection = DatasourceConfiguration.getDatasource().getConnection();
             PreparedStatement preparedStatement = connection.
                     prepareStatement("SELECT * FROM transaction WHERE from_account = ?")) {

            List<Transaction> transactions = new ArrayList<>();
            preparedStatement.setInt(1, accountId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Transaction transaction = Transaction.builder()
                            .id(resultSet.getInt(ID))
                            .fromAccount(resultSet.getInt(FROM_ACCOUNT))
                            .toAccount(resultSet.getInt(TO_ACCOUNT))
                            .amount(resultSet.getBigDecimal(AMOUNT))
                            .creationDate(resultSet.getTimestamp(CREATION_DATE))
                            .updateDate(resultSet.getTimestamp(UPDATE_DATE))
                            .build();

                    transactions.add(transaction);
                }
            }
            return transactions;

        } catch (SQLException e) {
            throw new RepositoryException(e, GenericErrorType.SOMETHING_WENT_WRONG);
        } catch (PropertyVetoException e) {
            throw new RepositoryException(e, TransactionErrorType.CANNOT_FIND_TRANSACTION);
        }
    }

    @Override
    public List<Transaction> getCreditTransactions(Integer accountId) {
        try (Connection connection = DatasourceConfiguration.getDatasource().getConnection();
             PreparedStatement preparedStatement = connection.
                     prepareStatement("SELECT * FROM transaction WHERE to_account = ?")) {

            List<Transaction> transactions = new ArrayList<>();
            preparedStatement.setInt(1, accountId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    Transaction transaction = Transaction.builder()
                            .id(resultSet.getInt(ID))
                            .fromAccount(resultSet.getInt(FROM_ACCOUNT))
                            .toAccount(resultSet.getInt(TO_ACCOUNT))
                            .amount(resultSet.getBigDecimal(AMOUNT))
                            .creationDate(resultSet.getTimestamp(CREATION_DATE))
                            .updateDate(resultSet.getTimestamp(UPDATE_DATE))
                            .build();

                    transactions.add(transaction);
                }
            }
            return transactions;

        } catch (SQLException e) {
            throw new RepositoryException(e, GenericErrorType.SOMETHING_WENT_WRONG);
        } catch (PropertyVetoException e) {
            throw new RepositoryException(e, TransactionErrorType.CANNOT_FIND_TRANSACTION);
        }
    }
}
