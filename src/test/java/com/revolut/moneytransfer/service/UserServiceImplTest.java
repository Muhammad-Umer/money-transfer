package com.revolut.moneytransfer.service;

import com.revolut.moneytransfer.dto.User;
import com.revolut.moneytransfer.exception.ServiceException;
import com.revolut.moneytransfer.repository.UserRepository;
import com.revolut.moneytransfer.service.impl.UserServiceImpl;
import com.revolut.moneytransfer.type.application.Currency;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

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
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {
    
    private static final String ID = "id";
    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String EMAIL = "email";
    private static final String COUNTRY = "country";
    private static final String CURRENCY = "currency";

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @Before
    public void setup() {
        userService = new UserServiceImpl(userRepository);
    }

    @Test
    @DisplayName("Test user add use-case")
    public void testAddUser() {
        User mockUser = getMockUser();
        when(userRepository.add(any(User.class))).thenReturn(mockUser);
        User actualUser = userService.add(mockUser);


        assertThat(actualUser, allOf(
                hasProperty(ID, is(mockUser.getId())),
                hasProperty(FIRST_NAME, is(mockUser.getFirstName())),
                hasProperty(LAST_NAME, is(mockUser.getLastName())),
                hasProperty(EMAIL, is(mockUser.getEmail())),
                hasProperty(COUNTRY, is(mockUser.getCountry())),
                hasProperty(CURRENCY, is(mockUser.getCurrency()))
        ));

        assertEquals(actualUser.getCurrency(), Currency.USD.name());

        verify(userRepository, times(1)).add(mockUser);
    }


    @Test
    @DisplayName("Test add Turkey user use-case")
    public void testAddTurkeyUser() {
        User mockUser = getMockUser();
        mockUser.setCountry("TUR");
        when(userRepository.add(any(User.class))).thenReturn(mockUser);
        User actualUser = userService.add(mockUser);


        assertThat(actualUser, allOf(
                hasProperty(ID, is(mockUser.getId())),
                hasProperty(FIRST_NAME, is(mockUser.getFirstName())),
                hasProperty(LAST_NAME, is(mockUser.getLastName())),
                hasProperty(EMAIL, is(mockUser.getEmail())),
                hasProperty(COUNTRY, is(mockUser.getCountry())),
                hasProperty(CURRENCY, is(mockUser.getCurrency()))
        ));

        verify(userRepository, times(1)).add(mockUser);
    }

    @Test
    @DisplayName("Test add user when country is invalid use-case")
    public void testAddUserInvalidCountryException() {
        User mockUser = getMockUser();
        mockUser.setCountry("GER");
        when(userRepository.add(any(User.class))).thenReturn(mockUser);

        ServiceException thrown =
                assertThrows(ServiceException.class, () -> userService.add(mockUser));

        assertTrue(thrown.getAppMessage().contains("The country provided is invalid"));
    }

    @Test
    @DisplayName("Test update user when country is invalid use-case")
    public void testUpdateUserInvalidCountryException() {
        User mockUser = getMockUser();
        mockUser.setCountry("GER");
        when(userRepository.update(any(User.class))).thenReturn(mockUser);

        ServiceException thrown =
                assertThrows(ServiceException.class, () -> userService.update(mockUser));

        assertTrue(thrown.getAppMessage().contains("The country provided is invalid"));
    }

    @Test
    @DisplayName("Test user update use-case")
    public void testUpdateUser() {
        User mockUser = getMockUser();
        when(userRepository.update(any(User.class))).thenReturn(mockUser);
        User actualUser = userService.update(mockUser);

        assertThat(actualUser, allOf(
                hasProperty(ID, is(mockUser.getId())),
                hasProperty(FIRST_NAME, is(mockUser.getFirstName())),
                hasProperty(LAST_NAME, is(mockUser.getLastName())),
                hasProperty(EMAIL, is(mockUser.getEmail())),
                hasProperty(COUNTRY, is(mockUser.getCountry())),
                hasProperty(CURRENCY, is(mockUser.getCurrency()))
        ));

        verify(userRepository, times(1)).update(mockUser);
    }

    @Test
    @DisplayName("Test use-case when user id is invalid")
    public void testUpdateUserException() {
        User mockUser = getMockUser();
        mockUser.setId(null);
        when(userRepository.update(any(User.class))).thenReturn(mockUser);

        ServiceException thrown =
                assertThrows(ServiceException.class, () -> userService.update(mockUser));

        assertTrue(thrown.getAppMessage().contains("Invalid user id"));
    }

    @Test
    @DisplayName("Test delete user use-case")
    public void testDeleteUser() {
        User mockUser = getMockUser();
        when(userRepository.delete(anyInt())).thenReturn(true);

        boolean actual = userService.delete(mockUser.getId().toString());

        assertTrue(actual);
    }

    @Test
    @DisplayName("Test get user by id use-case")
    public void testGetUserById() {
        User mockUser = getMockUser();
        when(userRepository.findById(anyInt())).thenReturn(mockUser);

        User actualUser = userService.findById(mockUser.getId().toString());

        assertThat(actualUser, allOf(
                hasProperty(ID, is(mockUser.getId())),
                hasProperty(FIRST_NAME, is(mockUser.getFirstName())),
                hasProperty(LAST_NAME, is(mockUser.getLastName())),
                hasProperty(EMAIL, is(mockUser.getEmail())),
                hasProperty(COUNTRY, is(mockUser.getCountry())),
                hasProperty(CURRENCY, is(mockUser.getCurrency()))
        ));

        verify(userRepository, times(1)).findById(mockUser.getId());
    }

    @Test
    @DisplayName("Test get all user use-case")
    public void testGetAllUsers() {
        User mockUser1 = getMockUser();
        User mockUser2 = getMockUser();
        mockUser2.setId(2);

        List<User> mockUsers = new ArrayList<>();
        mockUsers.add(mockUser1);
        mockUsers.add(mockUser2);

        when(userRepository.getAll()).thenReturn(mockUsers);

        List<User> actualUsers = userService.getAll();

        assertThat(actualUsers.get(0), allOf(
                hasProperty(ID, is(mockUser1.getId())),
                hasProperty(FIRST_NAME, is(mockUser1.getFirstName())),
                hasProperty(LAST_NAME, is(mockUser1.getLastName())),
                hasProperty(EMAIL, is(mockUser1.getEmail())),
                hasProperty(COUNTRY, is(mockUser1.getCountry())),
                hasProperty(CURRENCY, is(mockUser1.getCurrency()))
        ));

        assertEquals(actualUsers.size(), mockUsers.size());

        verify(userRepository, times(1)).getAll();
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
}
