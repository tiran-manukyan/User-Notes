package com.simple.spring.app.dto;

public class JwtTokenDto {
    String token;

    public JwtTokenDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
