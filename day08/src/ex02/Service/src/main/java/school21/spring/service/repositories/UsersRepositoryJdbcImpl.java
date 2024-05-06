package school21.spring.service.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import school21.spring.service.models.User;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component("usersRepositoryJdbcImpl")
@DependsOn("dataSource")
public class UsersRepositoryJdbcImpl implements UsersRepository {
    private final Connection connection;
    @Autowired
    public UsersRepositoryJdbcImpl(@Qualifier("dataSource") DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }
    @Override
    public Optional<User> findById(Long id) {
        try {
            PreparedStatement statement = connection.prepareStatement(String.format("SELECT * FROM service.users WHERE id=%d", id));
            ResultSet set = statement.getResultSet();
            set.next();
            return Optional.of(new User(set.getLong("id"), set.getString("email"), set.getString("password")));
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
                userList.add(new User(set.getLong("id"), set.getString("email"), set.getString("password")));
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
                    String.format("INSERT INTO service.users(email, password) VALUES('%s', '%s')", entity.getEmail(), entity.getPassword()));
            statement.execute();
            statement = connection.prepareStatement("SELECT max(id) FROM service.users");
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
                    String.format("UPDATE service.users SET email ='%s', password = '%s' WHERE id = %d", entity.getEmail(), entity.getPassword(), entity.getId()));
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
                return Optional.of(new User(set.getLong("id"), email, set.getString("password")));
            } else {
                return Optional.empty();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return Optional.empty();
        }
    }
}
