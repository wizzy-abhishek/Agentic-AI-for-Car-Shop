package com.carlandingClient.BookCar.dto;

import com.carlandingClient.BookCar.entity.UserRole;

public class SignUpDTO {

    private String fullName;

    private String Email ;

    private String password ;

    private String role ;

    public SignUpDTO(String fullName, String email, String password, String role) {
        this.fullName = fullName;
        Email = email;
        this.password = password;
        this.role = role;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmail() {
        return Email;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        if(role.equals("ADMIN")){
            return UserRole.OWNER;
        }else{
            return UserRole.CUSTOMER;
        }
    }
}
