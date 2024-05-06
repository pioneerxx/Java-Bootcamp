package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ProductsRepositoryJdbcImplTest {
    private DataSource dataSource;
    private final List<Product> EXPECTED_FIND_ALL_PRODUCTS = new ArrayList<Product>() {
        {
            add(new Product(0L, "Dyson fan", 49990));
            add(new Product(1L, "Bipki", 80));
            add(new Product(2L, "Canoe", 4500));
            add(new Product(3L, "Playstation 5", 74990));
            add(new Product(4L, "Casket", 10000));
        }
    };
    private final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(2L, "Canoe", 4500);
    private final Product EXPECTED_UPDATED_PRODUCT = new Product(4L, "Casket", 12000);
    private final Product EXPECTED_SAVE_PRODUCT = new Product(4L, "Water", 55);

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
    public void testFindAll() {
        try {
            ProductsRepositoryJdbcImpl productsRepository = new ProductsRepositoryJdbcImpl(dataSource.getConnection());
            assertEquals(productsRepository.findAll(), EXPECTED_FIND_ALL_PRODUCTS);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testFindById() {
        try {
            ProductsRepositoryJdbcImpl productsRepository = new ProductsRepositoryJdbcImpl(dataSource.getConnection());
            assertEquals(productsRepository.findById(2L).get(), EXPECTED_FIND_BY_ID_PRODUCT);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void testUpdate() {
        try {
            ProductsRepositoryJdbcImpl productsRepository = new ProductsRepositoryJdbcImpl(dataSource.getConnection());
            productsRepository.update(new Product(4L, "Casket", 12000));
            assertEquals(productsRepository.findById(4L).get(), EXPECTED_UPDATED_PRODUCT);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Test
    public void saveTest() {
        try {
            ProductsRepositoryJdbcImpl productsRepository = new ProductsRepositoryJdbcImpl(dataSource.getConnection());
            productsRepository.save(new Product(4L, "Water", 55));
            assertEquals(EXPECTED_SAVE_PRODUCT, productsRepository.findById(4L).get());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    @Test
    public void deleteTest() {
        try {
            ProductsRepositoryJdbcImpl productsRepository = new ProductsRepositoryJdbcImpl(dataSource.getConnection());
            productsRepository.delete(1L);
            assertFalse(productsRepository.findById(1L).isPresent());
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
