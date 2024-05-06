package edu.school21.chat.app;

import edu.school21.chat.Exception.NotSavedSubEntityException;
import edu.school21.chat.models.User;
import edu.school21.chat.repositories.UsersRepository;
import edu.school21.chat.repositories.UsersRepositoryJdbcImpl;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            UsersRepository usersRepository = new UsersRepositoryJdbcImpl(DBConnection.getConnection());
            List<User> userList = usersRepository.findAll(0, 5);
            for (User user : userList) {
                System.out.println(user);
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        } catch (NotSavedSubEntityException e) {
            System.err.println(e.getMessage());
        }
    }
}