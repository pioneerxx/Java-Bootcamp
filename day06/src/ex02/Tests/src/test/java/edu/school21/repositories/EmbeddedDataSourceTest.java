package edu.school21.repositories;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EmbeddedDataSourceTest {
    private DataSource dataSource;

    @BeforeEach
    void initialize() {
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
    void verifyConnection() {
        try (Connection connection = dataSource.getConnection()) {
            assertNotNull(connection);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
