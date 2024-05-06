package edu.school21.sockets.repositories;

import edu.school21.sockets.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;

@Component("usersRepository")
@DependsOn("dataSource")
public class UsersRepositoryImpl implements UsersRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    @Autowired
    public UsersRepositoryImpl(@Qualifier("dataSource") DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    @Override
    public Optional<User> findById(Long id) {
        User user = jdbcTemplate.query("SELECT * FROM service.users WHERE id=:id",
                        new MapSqlParameterSource().addValue("id", id),
                        new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
        return user == null ? Optional.empty() : Optional.of(user);
    }
    @Override
    public List<User> findAll() {
        List<User> userList = jdbcTemplate.query("SELECT * FROM service.users", new BeanPropertyRowMapper<>(User.class));
        return userList.isEmpty() ? null : userList;
    }
    @Override
    public void save(User entity) {
        jdbcTemplate.update("INSERT INTO service.users(email, password) VALUES (:email, :password)",
                new MapSqlParameterSource().addValue("email", entity.getEmail()).addValue("password", entity.getPassword()));
        User user = jdbcTemplate.query("SELECT max(id) FROM service.users", new BeanPropertyRowMapper<>(User.class)).stream().findAny().get();
        entity.setId(user.getId());
    }
    @Override
    public void update(User entity) {
        jdbcTemplate.update("UPDATE service.users SET email=:email, password=:password WHERE id=:id",
                new MapSqlParameterSource()
                        .addValue("id", entity.getId())
                        .addValue("email", entity.getEmail())
                        .addValue("password", entity.getPassword()));
    }
    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM service.users WHERE id=:id", new MapSqlParameterSource("id", id));
    }
    @Override
    public Optional<User> findByEmail(String email) {
        User user = jdbcTemplate.query("SELECT * FROM service.users WHERE email=:email",
                        new MapSqlParameterSource().addValue("email", email),
                        new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
        return user == null ? Optional.empty() : Optional.of(user);
    }
}

