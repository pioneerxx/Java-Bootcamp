package edu.school21.chat.app;

import edu.school21.chat.Exception.NotSavedSubEntityException;
import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.sql.SQLException;
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        try {
            MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(DBConnection.getConnection());
            Optional<Message> messageOptional = messagesRepository.findById(1L);
            if (messageOptional.isPresent()) {
                Message message = messageOptional.get();
                message.setText("Bye");
                message.setDateTime(null);
                messagesRepository.update(message);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (NotSavedSubEntityException e) {
            System.err.println(e.getMessage());
        }
    }
}