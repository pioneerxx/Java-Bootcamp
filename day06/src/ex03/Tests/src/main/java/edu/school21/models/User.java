package edu.school21.models;

import java.util.Objects;

public class User {
    private Long id;
    private String password;
    private String login;
    private boolean authentication;

    public User(Long id, String login, String password, boolean authentication) {
        this.id = id;
        this.password = password;
        this.login = login;
        this.authentication = authentication;
    }

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean getAuthentication() {
        return authentication;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public void setLogin(String login) {
        this.login = login;
    }

    public void setAuthentication(boolean authentication) {
        this.authentication = authentication;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", password='" + password + '\'' +
                ", login='" + login + '\'' +
                ", authentication=" + authentication +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return authentication == user.authentication && Objects.equals(id, user.id) && Objects.equals(password, user.password) && Objects.equals(login, user.login);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, password, login, authentication);
    }
}