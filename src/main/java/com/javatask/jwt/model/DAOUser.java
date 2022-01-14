package com.javatask.jwt.model;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.springframework.boot.jackson.JsonComponent;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class DAOUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long userId;
    @Column
    private String username;
    @Column
    private String password;

    @OneToMany(fetch = FetchType.EAGER,mappedBy="user",cascade = CascadeType.ALL)
    @Column(nullable = true)
    @JsonManagedReference
    private Set<DAOMessage> messages;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long id) {
        this.userId = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Set<DAOMessage> getMessages() {
        return messages;
    }
}
