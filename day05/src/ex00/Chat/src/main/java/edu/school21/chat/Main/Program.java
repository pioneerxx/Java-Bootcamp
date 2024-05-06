package edu.school21.chat.Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Program {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/Chat", "postgres", "root")) {
            PreparedStatement userStatement = connection.prepareStatement("SELECT chat.users.id, login, password, name FROM chat.users " +
                    "JOIN chat.chatrooms ON owner = chat.users.id");
            ResultSet resultSet = userStatement.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getString("id") + " " + resultSet.getString("login") + " " + resultSet.getString("name"));
            }
        } catch (java.sql.SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}