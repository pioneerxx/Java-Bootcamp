package edu.school21.chat.models;

import java.util.List;
import java.util.Objects;

public class Chatroom {
    private Long id;
    private String name;
    private User owner;
    private List<Message> messageList;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public User getUser() {
        return owner;
    }

    public Chatroom(Long id, String name, User owner, List<Message> messageList) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.messageList = messageList;
    }
    @Override
    public String toString() {
        return "{id=" + id.toString() +
                ", name=" + name +
                ", owner=" + owner.toString() + '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        boolean res = true;
        Chatroom ch;
        if (o == null) {
            res = false;
        } else if (o.getClass() != getClass()) {
            res = false;
        } else {
            ch = (Chatroom) o;
            if (ch.id != id || ch.name != name || !ch.owner.equals(owner)) {
                res = false;
            }
        }
        return res;
    }
}
