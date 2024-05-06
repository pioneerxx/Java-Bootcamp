package edu.school21.chat.models;

import java.time.LocalDateTime;
import java.util.Objects;

public class Message {
    private Long id;
    private User author;
    private Chatroom room;
    private String text;
    private LocalDateTime dateTime;

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public Chatroom getRoom() {
        return room;
    }
    public String getText() {
        return text;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Message(Long id, User user, Chatroom chatroom, String text, LocalDateTime dateTime) {
        this.id = id;
        this.author = user;
        this.room = chatroom;
        this.text = text;
        this.dateTime = dateTime;
    }
    @Override
    public String toString() {
        return "Message: {\nid=" + id.toString() +
                ",\nauthor=" + author.toString() +
                ",\nroom=" + room.toString() +
                ",\ntext=\"" + text +
                "\",\ndateTime=" + dateTime.getDayOfMonth() + '/' + dateTime.getMonth().getValue() + '/' + dateTime.getYear() +
                ' ' + dateTime.getHour() + ':' + dateTime.getMinute() + "}";
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
