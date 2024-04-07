package edu.ib.networktechnologies.controllers;

import edu.ib.networktechnologies.controllers.dto.user.GetUserDto;
import edu.ib.networktechnologies.controllers.dto.user.PatchUserDto;
import edu.ib.networktechnologies.controllers.dto.user.PatchUserResponseDto;
import edu.ib.networktechnologies.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
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

    @PatchMapping("/{id}")
    public ResponseEntity<PatchUserResponseDto> update(@PathVariable long id, @RequestBody PatchUserDto dto) {
        PatchUserResponseDto updatedUser = userService.update(id, dto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);


    }
}