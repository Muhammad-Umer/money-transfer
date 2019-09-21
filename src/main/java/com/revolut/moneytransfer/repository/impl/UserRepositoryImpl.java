package com.revolut.moneytransfer.repository.impl;

import com.revolut.moneytransfer.configurations.DatasourceConfiguration;
import com.revolut.moneytransfer.dto.User;
import com.revolut.moneytransfer.exception.RepositoryException;
import com.revolut.moneytransfer.repository.UserRepository;
import com.revolut.moneytransfer.type.error.AccountErrorType;
import com.revolut.moneytransfer.type.error.GenericErrorType;
import com.revolut.moneytransfer.type.error.UserErrorType;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static com.revolut.moneytransfer.utils.DataFields.CREATION_DATE;
import static com.revolut.moneytransfer.utils.DataFields.ID;
import static com.revolut.moneytransfer.utils.DataFields.UPDATE_DATE;
import static com.revolut.moneytransfer.utils.DataFields.UserFields.COUNTRY;
import static com.revolut.moneytransfer.utils.DataFields.UserFields.CURRENCY;
import static com.revolut.moneytransfer.utils.DataFields.UserFields.EMAIL_ADDRESS;
import static com.revolut.moneytransfer.utils.DataFields.UserFields.FIRST_NAME;
import static com.revolut.moneytransfer.utils.DataFields.UserFields.LAST_NAME;

public class UserRepositoryImpl implements UserRepository {

    private static UserRepositoryImpl userRepositoryImpl = null;

    public static synchronized UserRepositoryImpl getInstance() {
        if(userRepositoryImpl == null ) {
            userRepositoryImpl = new UserRepositoryImpl();
        }
        return userRepositoryImpl;
    }

    @Override
    public User add(User user) {
        try (Connection connection = DatasourceConfiguration.getDatasource().getConnection();
             PreparedStatement preparedStatement = connection.
                     prepareStatement("INSERT INTO user VALUES (NULL, ?, ?, ?, ?, ?, FALSE, ?, ?)",
                             Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getCountry());
            preparedStatement.setString(5, user.getCurrency());
            preparedStatement.setTimestamp(6, user.getCreationDate());
            preparedStatement.setTimestamp(7, user.getUpdateDate());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        return findById(id);
                    }
                }
            }

            throw new RepositoryException(UserErrorType.USER_NOT_CREATED);
        } catch (SQLException e) {
            throw new RepositoryException(e, GenericErrorType.SOMETHING_WENT_WRONG);
        } catch (PropertyVetoException e) {
            throw new RepositoryException(e, UserErrorType.USER_NOT_CREATED);
        }
    }

    @Override
    public User update(User user) {
        try (Connection connection = DatasourceConfiguration.getDatasource().getConnection();
             PreparedStatement preparedStatement = connection.
                     prepareStatement("UPDATE user SET first_name = ?, last_name = ?, " +
                             "email_address = ?, country = ?, currency = ?, update_date = ? WHERE id = ?")) {

            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getCountry());
            preparedStatement.setString(5, user.getCurrency());
            preparedStatement.setTimestamp(6, user.getUpdateDate());
            preparedStatement.setInt(7, user.getId());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected == 1) {
                try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt(1);
                        return findById(id);
                    }
                }
            }

            throw new RepositoryException(UserErrorType.USER_NOT_UPDATED);
        } catch (SQLException e) {
            throw new RepositoryException(e, GenericErrorType.SOMETHING_WENT_WRONG);
        } catch (PropertyVetoException e) {
            throw new RepositoryException(e, UserErrorType.USER_NOT_UPDATED);
        }
    }

    @Override
    public boolean delete(Integer id) {
        try (Connection connection = DatasourceConfiguration.getDatasource().getConnection();
             PreparedStatement preparedStatement = connection.
                     prepareStatement("UPDATE user SET deleted = ?, update_date = ? WHERE id = ?")) {

            preparedStatement.setBoolean(1, true);
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(3, id);

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected == 1;
        } catch (SQLException e) {
            throw new RepositoryException(e, GenericErrorType.SOMETHING_WENT_WRONG);
        } catch (PropertyVetoException e) {
            throw new RepositoryException(e, AccountErrorType.ACCOUNT_NOT_UPDATED);
        }
    }

    @Override
    public User findById(Integer id) {
        try (Connection connection = DatasourceConfiguration.getDatasource().getConnection();
             PreparedStatement preparedStatement = connection.
                     prepareStatement("SELECT * FROM user WHERE id = ? AND deleted = false")) {

            User user = new User();
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    user = User.builder()
                            .id(resultSet.getInt(ID))
                            .firstName(resultSet.getString(FIRST_NAME))
                            .lastName(resultSet.getString(LAST_NAME))
                            .email(resultSet.getString(EMAIL_ADDRESS))
                            .country(resultSet.getString(COUNTRY))
                            .currency(resultSet.getString(CURRENCY))
                            .creationDate(resultSet.getTimestamp(CREATION_DATE))
                            .updateDate(resultSet.getTimestamp(UPDATE_DATE))
                            .build();
                }
            }
            return user;

        } catch (SQLException e) {
            throw new RepositoryException(e, GenericErrorType.SOMETHING_WENT_WRONG);
        } catch (PropertyVetoException e) {
            throw new RepositoryException(e, AccountErrorType.CANNOT_FIND_ACCOUNT);
        }
    }

    @Override
    public List<User> getAll() {

        try (Connection connection = DatasourceConfiguration.getDatasource().getConnection();
             PreparedStatement preparedStatement = connection.
                     prepareStatement("SELECT * FROM user WHERE deleted = false")) {

            List<User> users = new ArrayList<>();

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    User user = User.builder()
                            .id(resultSet.getInt(ID))
                            .firstName(resultSet.getString(FIRST_NAME))
                            .lastName(resultSet.getString(LAST_NAME))
                            .email(resultSet.getString(EMAIL_ADDRESS))
                            .country(resultSet.getString(COUNTRY))
                            .currency(resultSet.getString(CURRENCY))
                            .creationDate(resultSet.getTimestamp(CREATION_DATE))
                            .updateDate(resultSet.getTimestamp(UPDATE_DATE))
                            .build();

                    users.add(user);
                }
            }
            return users;

        } catch (SQLException e) {
            throw new RepositoryException(e, GenericErrorType.SOMETHING_WENT_WRONG);
        } catch (PropertyVetoException e) {
            throw new RepositoryException(e, AccountErrorType.CANNOT_FIND_ACCOUNT);
        }
    }
}
