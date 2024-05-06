package edu.school21.repositories;

import edu.school21.models.Product;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private Connection connection;

    public ProductsRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> productList = new ArrayList<>();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tests.products");
        ResultSet set = statement.executeQuery();
        while (set.next()) {
            productList.add(new Product(set.getLong("identifier"),
                    set.getString("name"), set.getInt("price")));
        }
        return productList;
    }

    @Override
    public Optional<Product> findById(Long id) throws SQLException {
        Optional<Product> product = Optional.empty();
        PreparedStatement statement = connection.prepareStatement("SELECT * " +
                "FROM tests.products WHERE identifier = " + id);
        ResultSet set = statement.executeQuery();
        if (set.next()) {
            product = Optional.of(new Product(set.getLong("identifier"),
                    set.getString("name"), set.getInt("price")));
        }
        return product;
    }

    @Override
    public void update(Product product) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE tests.products SET name = '"
                + product.getName() + "', price = " + product.getPrice() + " WHERE identifier = " + product.getIdentifier());
        statement.executeUpdate();
    }

    @Override
    public void save(Product product) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("INSERT INTO tests.products(name, price) " +
                "VALUES ('" + product.getName() + "', " + product.getPrice() + ");");
        statement.execute();
        statement = connection.prepareStatement("CALL IDENTITY()");
        ResultSet set = statement.executeQuery();
        set.next();
        product.setIdentifier(set.getLong(1));
    }

    @Override
    public void delete(Long id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM tests.products WHERE identifier = " + id);
        statement.executeQuery();
    }
}
