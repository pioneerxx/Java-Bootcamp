package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsersServiceImplTest {
    private static final User EXPECTED_FIND_BY_ID_LOGIN =  new User(0L, "Ali", "14102", false);
    private static final User EXPECTED_UPDATE_USER = new User(0L, "Ali", "beepkas", false);
    private DataSource dataSource;
    @BeforeEach
    public void initialize() {
        dataSource = new EmbeddedDatabaseBuilder().
                setType(EmbeddedDatabaseType.HSQL).
                addScript("/schema.sql").
                addScript("/data.sql").
                build();
    }

    @AfterEach
    void closeConnection() {
        try (Connection connection = dataSource.getConnection()) {
            Statement statement = connection.createStatement();
            statement.execute("SHUTDOWN");
            statement.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    @Test
    public void findLoginTest() {
        try {
            UsersServiceImpl usersService = new UsersServiceImpl(dataSource.getConnection());
            User user = usersService.findByLogin("Ali");
            assertEquals(EXPECTED_FIND_BY_ID_LOGIN, user);
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
    @Test
    public void updateTest() {
        try {
            UsersServiceImpl usersService = new UsersServiceImpl(dataSource.getConnection());
            usersService.update(new User(0L, "Ali", "beepkas", false));
            assertEquals(EXPECTED_UPDATE_USER, usersService.findByLogin("Ali"));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    @Test
    public void authenticateCorrectTest() {
        try {
            UsersServiceImpl usersService = new UsersServiceImpl(dataSource.getConnection());
            User user = new User(0L, "Ali", "14102", false);
            assertTrue(usersService.authenticate(user.getLogin(), user.getPassword()));
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @Test
    public void authenticateIncorrectLoginTest() {
        try {
            UsersServiceImpl usersService = new UsersServiceImpl(dataSource.getConnection());
            User user = new User(0L, "Alee", "14102", false);
            assertFalse(usersService.authenticate(user.getLogin(), user.getPassword()));
        } catch (SQLException e) {
            System.err.println(e);
        }
    }

    @Test
    public void authenticateIncorrectPasswordTest() {
        try {
            UsersServiceImpl usersService = new UsersServiceImpl(dataSource.getConnection());
            User user = new User(0L, "Ali", "14100", false);
            assertFalse(usersService.authenticate(user.getLogin(), user.getPassword()));
        } catch (SQLException e) {
            System.err.println(e);
        }
    }
}