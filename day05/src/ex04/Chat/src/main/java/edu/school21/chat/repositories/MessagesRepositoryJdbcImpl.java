package edu.school21.chat.repositories;
import edu.school21.chat.Exception.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

public class MessagesRepositoryJdbcImpl implements MessagesRepository {
    private Connection connection;
    @Override
    public Optional<Message> findById(Long id) {
        Optional<Message> message = Optional.empty();
        try {
            PreparedStatement userStatement = connection.prepareStatement("SELECT * FROM chat.messages " +
                    "WHERE chat.messages.id = " + id.toString());
            ResultSet resultSet = userStatement.executeQuery();
            resultSet.next();
            LocalDateTime dateTime = resultSet.getTimestamp(5) == null ? null : resultSet.getTimestamp(5).toLocalDateTime();
            message = Optional.of(new Message(id, findUserById(resultSet.getLong(2)).get(),
                    findChatroomById(resultSet.getLong(3)).get(), resultSet.getString(4),
                    dateTime));
        } catch (SQLException e) {
            System.out.println(e);
        }
        return message;
    }

    private Optional<User> findUserById(Long id) {
        Optional<User> user = Optional.empty();
        try {
            PreparedStatement userStatement = connection.prepareStatement("SELECT * FROM chat.users " +
                    "WHERE chat.users.id = " + id.toString());
            ResultSet resultSet = userStatement.executeQuery();
            resultSet.next();
            user = Optional.of(new User(id, resultSet.getString(2), resultSet.getString(3),
                    new ArrayList<>(), new ArrayList<>()));
        } catch (SQLException e) {

        }
        return user;
    }

    private Optional<Chatroom> findChatroomById(Long id) {
        Optional<Chatroom> chatroom = Optional.empty();
        try {
            PreparedStatement userStatement = connection.prepareStatement("SELECT * FROM chat.chatrooms " +
                    "WHERE chat.chatrooms.id = " + id.toString());
            ResultSet resultSet = userStatement.executeQuery();
            resultSet.next();
            chatroom = Optional.of(new Chatroom(id, resultSet.getString(2),
                    findUserById(resultSet.getLong(3)).get(), new ArrayList<>()));
        } catch (SQLException e) {

        }
        return chatroom;
    }

    public MessagesRepositoryJdbcImpl(DataSource dataSource) throws SQLException {
        connection = dataSource.getConnection();
    }

    public void save(Message message) throws NotSavedSubEntityException, SQLException {
        if (message.getAuthor().getId() == null) {
            throw new NotSavedSubEntityException("Author can't be null");
        }
        if (message.getRoom().getId() == null) {
            throw new NotSavedSubEntityException("Chatroom can't be null");
        }
        if (!findUserById(message.getAuthor().getId()).isPresent()) {
            throw new NotSavedSubEntityException("Author not found in database");
        }
        if(!findChatroomById(message.getRoom().getId()).isPresent()) {
            throw new NotSavedSubEntityException("Chatroom not found in database");
        }
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO chat.messages(author, room, text, date) VALUES (" +
                message.getAuthor().getId() + ", " + message.getRoom().getId().toString() + ", '" + message.getText() + "', " + "'" +
                Timestamp.valueOf(message.getDateTime()) + "') RETURNING id;");
        ResultSet set = preparedStatement.executeQuery();
        set.next();
        message.setId(set.getLong(1));
    }

    @Override
    public void update(Message message) throws SQLException, NotSavedSubEntityException {
        if (message.getRoom().getId() == null) {
            throw new NotSavedSubEntityException("Chatroom can't be null");
        }
        if (!findUserById(message.getAuthor().getId()).isPresent()) {
            throw new NotSavedSubEntityException("Author not found in database");
        }
        if(!findChatroomById(message.getRoom().getId()).isPresent()) {
            throw new NotSavedSubEntityException("Chatroom not found in database");
        }
        String date = message.getDateTime() == null ? "null" : "'" + Timestamp.valueOf(message.getDateTime()) + "'";
        String text = message.getText() == null ? "null" : "'" + message.getText() + "'";
        PreparedStatement statement = connection.prepareStatement("UPDATE chat.messages SET author = " +
                message.getAuthor().getId() + ", room = " + message.getRoom().getId() + ", text = " + text +
                ", date = " + date + " WHERE id = " + message.getId() + ";");
        statement.executeUpdate();
    }
}
