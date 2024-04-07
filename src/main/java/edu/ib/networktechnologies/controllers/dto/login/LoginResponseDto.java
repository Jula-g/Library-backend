package edu.ib.networktechnologies.controllers.dto.login;

public class LoginResponseDto {

    private String token;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public LoginResponseDto(String token) {
        this.token = token;
    }
}
