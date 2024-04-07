package edu.ib.networktechnologies.controllers.dto.register;

import edu.ib.networktechnologies.commonTypes.UserRole;

public class RegisterResponseDto {

    private long userId;

    private String username;

    private String role;

    private String email;

    public RegisterResponseDto() {
    }

    public RegisterResponseDto(long userId, String username, String role, String email) {
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.email = email;
    }

    public RegisterResponseDto(String username, UserRole role, String email) {
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
