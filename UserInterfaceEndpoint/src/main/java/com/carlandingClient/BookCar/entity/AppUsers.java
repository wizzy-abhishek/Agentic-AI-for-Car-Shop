package com.carlandingClient.BookCar.entity;

import jakarta.persistence.*;

@Entity
public class AppUsers {

    @Id
    @Column(updatable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(updatable = false)
    @Enumerated(EnumType.STRING)
    private UserRole userRole;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public AppUsers() {
    }

    public AppUsers(String fullName, String email, String password, UserRole userRole) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.userRole = userRole;
    }
}
