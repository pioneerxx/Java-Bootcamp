package edu.school21.service.repositories;

import edu.school21.service.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final Connection connection;
    public UsersRepositoryJdbcImpl(DataSource dataSource) throws SQLException {
        this.connection = dataSource.getConnection();
    }
    @Override
    public Optional<User> findById(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(String.format("SELECT * FROM service.users WHERE id=%d", id));
            ResultSet set = statement.getResultSet();
            set.next();
            return Optional.of(new User(set.getLong("id"), set.getString("email")));
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }
    @Override
    public List<User> findAll() {
        List<User> userList = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM service.users");
            ResultSet set = statement.executeQuery();
            while (set.next()) {
                userList.add(new User(set.getLong("id"), set.getString("email")));
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return userList.isEmpty() ? null : userList;
    }
    @Override
    public void save(User entity) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    String.format("INSERT INTO service.users(email) VALUES('%s') RETURNING id", entity.getEmail()));
            ResultSet set = statement.executeQuery();
            entity.setId(set.next() ? set.getLong("id") : null);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    @Override
    public void update(User entity) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    String.format("UPDATE service.users SET email ='%s' WHERE id = %d", entity.getEmail(), entity.getId()));
            statement.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    @Override
    public void delete(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    String.format("DELETE FROM service.users WHERE id = %d", id));
            statement.execute();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    @Override
    public Optional<User> findByEmail(String email) {
        try {
            PreparedStatement statement = connection.prepareStatement(
                    String.format("SELECT * FROM service.users WHERE email = '%s'", email));
            ResultSet set = statement.executeQuery();
            if (set.next()) {
                return Optional.of(new User(set.getLong("id"), email));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }
}
