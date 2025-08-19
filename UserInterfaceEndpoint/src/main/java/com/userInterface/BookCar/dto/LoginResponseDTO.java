package com.userInterface.BookCar.dto;

public class LoginResponseDTO {

    private final String jwtToken;

    public LoginResponseDTO(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
