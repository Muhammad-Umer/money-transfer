package com.revolut.moneytransfer.service.impl;

import com.google.common.base.Enums;
import com.neovisionaries.i18n.CountryCode;
import com.revolut.moneytransfer.dto.User;
import com.revolut.moneytransfer.exception.ServiceException;
import com.revolut.moneytransfer.repository.UserRepository;
import com.revolut.moneytransfer.service.UserService;
import com.revolut.moneytransfer.type.application.Currency;
import com.revolut.moneytransfer.type.error.UserErrorType;

import javax.inject.Inject;
import java.sql.Timestamp;
import java.util.List;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Inject
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User add(User user) {
        CountryCode countryCode = CountryCode.getByAlpha3Code(user.getCountry());
        if (countryCode == null) {
            throw new ServiceException(UserErrorType.INVALID_USER_ID);
        }

        Currency currency =
                Enums.getIfPresent(Currency.class, countryCode.getCurrency().getCurrencyCode())
                        .or(Currency.USD);

        user.setCurrency(currency.name());
        return userRepository.add(user);
    }

    @Override
    public User update(User user) {
        if (user.getId() == null) {
            throw new ServiceException(UserErrorType.INVALID_USER_ID);
        }

        CountryCode countryCode = CountryCode.getByAlpha3Code(user.getCountry());
        if (countryCode == null) {
            throw new ServiceException(UserErrorType.INVALID_USER_ID);
        }

        Currency currency =
                Enums.getIfPresent(Currency.class, countryCode.getCurrency().getCurrencyCode())
                        .or(Currency.USD);

        user.setCurrency(currency.name());
        user.setUpdateDate(new Timestamp(System.currentTimeMillis()));

        return userRepository.update(user);
    }

    @Override
    public boolean delete(String userId) {
        return userRepository.delete(Integer.valueOf(userId));
    }

    @Override
    public User findById(String userId) {
        return userRepository.findById(Integer.valueOf(userId));
    }

    @Override
    public List<User> getAll() {
        return userRepository.getAll();
    }
}
