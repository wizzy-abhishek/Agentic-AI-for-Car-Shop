package com.carlandingClient.BookCar.dto;

public class LoginResponseDTO {

    private String jwtToken;

    public LoginResponseDTO(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public String getJwtToken() {
        return jwtToken;
    }
}
