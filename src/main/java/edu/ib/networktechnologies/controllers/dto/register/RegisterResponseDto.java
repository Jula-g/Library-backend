package edu.ib.networktechnologies.controllers.dto.register;

import edu.ib.networktechnologies.commonTypes.UserRole;

public class RegisterResponseDto {

    private long userId;
    private String username;
    private String token;
    private UserRole role;

    public RegisterResponseDto(long userId, String username, String token, String role) {
        this.userId = userId;
        this.username = username;
        this.token = token;
        this.role = UserRole.valueOf(role);
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
