package edu.school21.chat.app;

import edu.school21.chat.Exception.NotSavedSubEntityException;
import edu.school21.chat.models.Chatroom;
import edu.school21.chat.models.Message;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.MessagesRepository;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        try {
            User creator = new User(4L, "Dias", "09072001", new ArrayList(), new ArrayList());
            User author = creator;
            Chatroom room = new Chatroom(2L, "Делаем 3двьюер", creator, new ArrayList());
            Message message = new Message(null, author, room, "Ну какой 3двьюер ало", LocalDateTime.now());
            MessagesRepository messagesRepository = new MessagesRepositoryJdbcImpl(DBConnection.getConnection());
            messagesRepository.save(message);
            System.out.println(message.getId());

        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (NotSavedSubEntityException e) {
            System.err.println(e.getMessage());
        }
    }
}