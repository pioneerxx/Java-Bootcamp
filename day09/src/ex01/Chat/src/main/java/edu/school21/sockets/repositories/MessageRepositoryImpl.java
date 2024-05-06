package edu.school21.sockets.repositories;


import edu.school21.sockets.models.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Component("messagesRepository")
@DependsOn("dataSource")
public class MessageRepositoryImpl implements MessageRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public MessageRepositoryImpl(@Qualifier("dataSource")DataSource dataSource) throws SQLException {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<Message> findById(Long id) {
        Message message = jdbcTemplate.query("SELECT * FROM service.messages WHERE id=:id",
                        new MapSqlParameterSource().addValue("id", id),
                        new BeanPropertyRowMapper<>(Message.class))
                .stream().findAny().orElse(null);
        return message == null ? Optional.empty() : Optional.of(message);
    }

    @Override
    public List<Message> findAll() {
        List<Message> messagesList = jdbcTemplate.query("SELECT * FROM service.messages", new BeanPropertyRowMapper<>(Message.class));
        return messagesList.isEmpty() ? null : messagesList;
    }

    @Override
    public void save(Message entity) {
        jdbcTemplate.update("INSERT INTO service.messages(sender, text) VALUES (:sender, :text)",
                new MapSqlParameterSource()
                        .addValue("sender", entity.getSender())
                        .addValue("text", entity.getText()));
        Message message = jdbcTemplate.query("SELECT max(id) FROM service.messages",
                new BeanPropertyRowMapper<>(Message.class)).stream().findAny().get();
        entity.setId(message.getId());
    }
    @Override
    public void update(Message entity) {
        jdbcTemplate.update("UPDATE service.messages SET sender=:sender, text=:text WHERE id=:id",
                new MapSqlParameterSource()
                        .addValue("id", entity.getId())
                        .addValue("sender", entity.getSender())
                        .addValue("text", entity.getText()));
    }
    @Override
    public void delete(Long id) {
        jdbcTemplate.update("DELETE FROM service.messages WHERE id=:id", new MapSqlParameterSource("id", id));
    }
}
