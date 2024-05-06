package edu.school21.service.models;

public class User {
    private Long id;
    private String email;
    public User(Long id, String email) {
        this.id = id;
        this.email = email;
    }
    public User() {}

    public Long getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "{" +
                "id=" + id +
                ", email='" + email + '\'' +
                '}';
    }
}
