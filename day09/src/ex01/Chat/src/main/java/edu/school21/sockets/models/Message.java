package edu.school21.sockets.models;

public class Message {
    private Long id;
    private Long sender;
    private String text;

    public Long getId() {
        return id;
    }

    public Message() {}

    public Message(Long sender, String text) {
        this.sender = sender;
        this.text = text;
    }

    public Long getSender() {
        return sender;
    }

    public void setSender(Long sender) {
        this.sender = sender;
    }

    public String getText() {
        return text;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

}
