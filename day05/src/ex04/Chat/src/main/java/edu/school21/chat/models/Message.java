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

    public void setText(String text) {
        this.text = text;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public Message(Long id, User user, Chatroom chatroom, String text, LocalDateTime dateTime) {
        this.id = id;
        this.author = user;
        this.room = chatroom;
        this.text = text;
        this.dateTime = dateTime;
    }

    public Message(Message message) {
        this.id = message.id;
        this.author = message.author;
        this.room = message.room;
        this.text = message.text;
        this.dateTime = message.dateTime;
    }

    public void setAuthor(User user) {
        this.author = author;
    }

    public void setRoom(Chatroom room) {
        this.room = room;
    }

    @Override
    public String toString() {
        String date = dateTime == null ? "null" : "" + dateTime.getDayOfMonth() + '/' + dateTime.getMonth().getValue() + '/' + dateTime.getYear() +
                ' ' + dateTime.getHour() + ':' + dateTime.getMinute();
        return "Message: {\nid=" + id.toString() +
                ",\nauthor=" + author.toString() +
                ",\nroom=" + room.toString() +
                ",\ntext=\"" + text +
                "\",\ndateTime=" + date + "}";
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
