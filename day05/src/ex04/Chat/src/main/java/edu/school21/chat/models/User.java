package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class User {
    private Long id;
    private String login;
    private String password;
    private List<Chatroom> createdChatroomList;
    private List<Chatroom> usedChatroomList;


    public List<Chatroom> getCreatedChatroomList() {
        return createdChatroomList;
    }
    public List<Chatroom> getUsedChatroomList() {
        return usedChatroomList;
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

    public User(Long id, String login, String password, List<Chatroom> createdChatroomList, List<Chatroom> usedChatroomList) {
        this.id = id;
        this.login = login;
        this.password = password;
        this.createdChatroomList = createdChatroomList;
        this.usedChatroomList = usedChatroomList;
    }

    @Override
    public String toString() {
        String createdRooms = "";
        String usedRooms = "";
        if (createdChatroomList == null) {
            createdRooms = "null";
        } else {
            createdRooms = "[";
            for (int i = 0; i < createdChatroomList.size(); i++) {
                createdRooms +=  createdChatroomList.get(i).getName();
                if (i + 1 == createdChatroomList.size()) {
                    createdRooms += "]";
                } else {
                    createdRooms += "; ";
                }
            }
        }
        if (usedChatroomList == null) {
            usedRooms = "null";
        } else {
            usedRooms = "[";
            for (int i = 0; i < usedChatroomList.size(); i++) {
                usedRooms +=  usedChatroomList.get(i).getName();
                if (i + 1 == usedChatroomList.size()) {
                    usedRooms += "]";
                } else {
                    usedRooms += "; ";
                }
            }
        }
        return "{id=" + id.toString() +
                ", login=\"" + login +
                "\", password=\"" + password +
                "\", createdRooms=" + createdRooms +
                ", rooms=" + usedRooms + '}';
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
