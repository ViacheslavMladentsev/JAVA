package edu.school21.services;

import edu.school21.exception.AlreadyAuthenticatedException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;

class UsersServiceImplTest {

    private static final User USER_MOCK_CORRECT = new User(1L, "login", "password", false);
    private static final User USER_MOCK_INCORRECT_LOGIN = new User(1L, "login", "password", false);
    private static final User USER_MOCK_INCORRECT_PASSWORD = new User(1L, "login", "password", false);
    private static final User USER_MOCK_ALREADY_AUTHENTICATED = new User(1L, "login", "password", true);

    private static final String CORRECT_LOGIN = "login";
    private static final String INCORRECT_LOGIN = "incorrect_login";
    private static final String CORRECT_PASSWORD = "password";
    private static final String INCORRECT_PASSWORD = "incorrect_password";

    private UsersRepository mockUserRepository;

    @BeforeEach
    void init() {
        mockUserRepository = Mockito.mock(UsersRepository.class);
    }

    @Test
    void authenticateCorrect() {
        Mockito.doReturn(USER_MOCK_CORRECT).when(mockUserRepository).findByLogin(USER_MOCK_CORRECT.getLogin());
        UsersServiceImpl usersService = new UsersServiceImpl(mockUserRepository);
        assertTrue(usersService.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD));
        assertTrue(USER_MOCK_CORRECT.isAuthentication());
    }

    @Test
    void authenticateIncorrectLogin() {
        Mockito.doReturn(USER_MOCK_INCORRECT_LOGIN).when(mockUserRepository).findByLogin(USER_MOCK_INCORRECT_LOGIN.getLogin());
        UsersServiceImpl usersService = new UsersServiceImpl(mockUserRepository);
        assertThrows(NullPointerException.class, () -> usersService.authenticate(INCORRECT_LOGIN, CORRECT_PASSWORD));
    }

    @Test
    void authenticateIncorrectPassword() {
        Mockito.doReturn(USER_MOCK_INCORRECT_PASSWORD).when(mockUserRepository).findByLogin(USER_MOCK_CORRECT.getLogin());
        UsersServiceImpl usersService = new UsersServiceImpl(mockUserRepository);
        assertFalse(usersService.authenticate(CORRECT_LOGIN, INCORRECT_PASSWORD));
    }

    @Test
    void authenticateAlreadyAuthenticated() {
        Mockito.doReturn(USER_MOCK_ALREADY_AUTHENTICATED).when(mockUserRepository).findByLogin(USER_MOCK_ALREADY_AUTHENTICATED.getLogin());
        UsersServiceImpl usersService = new UsersServiceImpl(mockUserRepository);
        assertThrows(AlreadyAuthenticatedException.class,
                () -> usersService.authenticate(CORRECT_LOGIN, CORRECT_PASSWORD));
    }

}
