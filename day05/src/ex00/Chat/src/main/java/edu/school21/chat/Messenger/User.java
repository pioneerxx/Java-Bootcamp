package edu.school21.chat.Messenger;

import java.util.List;
import java.util.Objects;

public class User {
    private Long id;
    private String login;
    private String password;
    private List<Chatroom> createdChatroomList;
    private List<Chatroom> usedChatroomList;

    public User(Long id, String login, String password, List<Chatroom> createdChatroomList, List<Chatroom> usedChatroomList) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdChatroomList = createdChatroomList;
        this.usedChatroomList = usedChatroomList;
    }

    @Override
    public String toString() {
        return "User: id:" + id.toString() +
                ", login: " + login +
                ", password: " + password;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        boolean res = true;
        User u;
        if (o == null) {
            res = false;
        } else if (o.getClass() != getClass()) {
            res = false;
        } else {
            u = (User) o;
            if (u.id != id || u.login != login || u.password != password) {
                res = false;
            }
        }
        return res;
    }
}
