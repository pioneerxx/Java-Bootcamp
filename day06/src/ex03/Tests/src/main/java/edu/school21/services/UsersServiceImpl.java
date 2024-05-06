package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.models.User;

import java.sql.*;

public class UsersServiceImpl {
    Connection connection;
    public UsersServiceImpl(Connection connection) {
        this.connection = connection;
    }
    public User findByLogin(String login) throws SQLException {
        User user = null;
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tests.users WHERE login = '" + login + "'");
        ResultSet set = statement.executeQuery();
        if (set.next()) {
            user = new User(set.getLong(1), set.getString(2), set.getString(3), set.getBoolean(4));
        }
        return user;
    }

    public boolean authenticate(String login, String password) throws SQLException {
        boolean res = false;
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM tests.users WHERE login = '" + login + "'");
        User user = findByLogin(login);
        if (user != null) {
            if (user.getAuthentication()) {
                throw new AlreadyAuthenticatedException("User " + user.getLogin() + "already authenticated");
            }
            if (user.getPassword().equals(password)) {
                user.setAuthentication(true);
                update(user);
                res = true;
            }
        }
        return res;
    }

    public void update(User user) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("UPDATE tests.users SET login = '" + user.getLogin()
                + "', password = '" + user.getPassword() + "', authentication = " + user.getAuthentication() + " WHERE identifier = " + user.getId());
        statement.execute();
    }
}
