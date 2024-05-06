package edu.school21.chat.app;

import edu.school21.chat.models.Message;
import edu.school21.chat.repositories.MessagesRepositoryJdbcImpl;

import java.sql.SQLException;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.println("Enter a message ID");
            System.out.print("-> ");
            int inputMessageId = scanner.nextInt();
            MessagesRepositoryJdbcImpl repositoryJdbc = new MessagesRepositoryJdbcImpl(DBConnection.getConnection());
            Optional<Message> message = repositoryJdbc.findById(Long.valueOf(inputMessageId));
            if (message.isPresent()) {
                System.out.println(message.get());
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}