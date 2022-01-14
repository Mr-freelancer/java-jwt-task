package com.javatask.jwt.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;

@Entity
@Table(name = "messages")
public class DAOMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private DAOUser user;

    @Column(name = "message_text")
    private String messageText;

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    public void setUser(DAOUser user) {
        this.user = user;
    }

    public long getId() {
        return id;
    }

    public DAOUser getUser() {
        return user;
    }

    public String getMessageText() {
        return messageText;
    }


}
