package edu.school21.chat.Messenger;

import java.util.List;
import java.util.Objects;

public class Chatroom {
    private Long id;
    private String name;
    private User owner;
    private List<Message> messageList;

    @Override
    public String toString() {
        return "Chatroom: id:" + id.toString() +
                ", name: " + name +
                ", owner: " + owner.toString().split(" ")[4];
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
