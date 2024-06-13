package edu.ib.networktechnologies.controllers.dto.login;

public class LoginResponseDto {

    private String token;
    private Long userId;

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public LoginResponseDto(String token, Long userId) {
        this.token = token;
        this.userId = userId;
    }
}
