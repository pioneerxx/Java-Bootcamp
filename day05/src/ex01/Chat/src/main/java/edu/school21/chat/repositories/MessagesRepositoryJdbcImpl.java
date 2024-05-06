package edu.school21.chat.repositories;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
            message = Optional.of(new Message(id, findUserById(resultSet.getLong(2)).get(),
                    findChatroomById(resultSet.getLong(3)).get(), resultSet.getString(4),
                    resultSet.getTimestamp(5).toLocalDateTime()));
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
}
