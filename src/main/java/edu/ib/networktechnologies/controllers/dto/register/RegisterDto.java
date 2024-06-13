package edu.ib.networktechnologies.controllers.dto.register;

import edu.ib.networktechnologies.commonTypes.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import static edu.ib.networktechnologies.commonTypes.UserRole.ROLE_READER;

public class RegisterDto {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    private UserRole role;
    @NotBlank(message = "Email is required")
    @Email
    private String email;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Last name is required")
    private String lastName;

    public RegisterDto(String username, String password, UserRole role, String email, String name, String lastName) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.email = email;
        this.name = name;
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
