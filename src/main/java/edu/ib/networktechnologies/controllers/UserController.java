package edu.ib.networktechnologies.controllers;

import edu.ib.networktechnologies.controllers.dto.user.GetUserDto;
import edu.ib.networktechnologies.controllers.dto.user.PatchUserDto;
import edu.ib.networktechnologies.controllers.dto.user.PatchUserResponseDto;
import edu.ib.networktechnologies.entities.User;
import edu.ib.networktechnologies.services.AuthService;
import edu.ib.networktechnologies.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("isAuthenticated()")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<GetUserDto> getMe(Principal principal) {
        String username = principal.getName();
        GetUserDto userDto = userService.getUserByUsername(username);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<PatchUserResponseDto> update(@PathVariable long id, @RequestBody PatchUserDto dto) {
        PatchUserResponseDto updatedUser = userService.update(id, dto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<GetUserDto>> getAllUsers() {
        List<GetUserDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }
}