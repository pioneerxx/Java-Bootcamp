package edu.school21.chat.Messenger;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private Long id;
    private User author;
    private Chatroom room;
    private String text;
    private LocalDateTime dateTime;

    @Override
    public String toString() {
        return "Message: id:" + id.toString() +
                ", author: " + author.toString().split(" ")[4] +
                ", chatroom: " + room.toString().split(" ")[4] +
                ", text: " + text +
                ", time: " + dateTime.toString();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        boolean res = true;
        Message mes;
        if (o == null) {
            res = false;
        } else if (o.getClass() != getClass()) {
            res = false;
        } else {
            mes = (Message) o;
            if (mes.id != id || mes.author != author || mes.text != text || mes.dateTime != dateTime) {
                res = false;
            }
        }
        return res;
    }
}
