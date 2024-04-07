package edu.ib.networktechnologies.services;

import edu.ib.networktechnologies.controllers.dto.user.GetUserDto;
import edu.ib.networktechnologies.controllers.dto.user.PatchUserDto;
import edu.ib.networktechnologies.controllers.dto.user.PatchUserResponseDto;
import edu.ib.networktechnologies.entities.Auth;
import edu.ib.networktechnologies.entities.User;
import edu.ib.networktechnologies.exceptions.UserNotFoundException;
import edu.ib.networktechnologies.repositories.AuthRepository;
import edu.ib.networktechnologies.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;


@Service
public class UserService extends OwnershipService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;

    @Autowired
    public UserService(AuthRepository authRepository, UserRepository userRepository) {
        super(authRepository);
        this.userRepository = userRepository;
        this.authRepository = authRepository;
    }

    public GetUserDto getUserByUsername(String username) {
        Auth auth = authRepository.findByUsername(username).orElseThrow(() -> UserNotFoundException.createWithUsername(username));
        User user = auth.getUser();
        return new GetUserDto(user.getUserId(), user.getName(), user.getLastName(), user.getEmail());
    }

    @PreAuthorize("hasRole('ADMIN') or this.isOwner(authentication.name, #id)")
    public PatchUserResponseDto update(long id, PatchUserDto dto) {
        User user = userRepository.findById(id).orElseThrow(() -> UserNotFoundException.createWithId(id));

        dto.getEmail().ifPresent(user::setEmail);
        dto.getName().ifPresent(user::setName);
        dto.getLastName().ifPresent(user::setLastName);

        userRepository.save(user);
        return new PatchUserResponseDto(user.getUserId(), user.getName(), user.getLastName(), user.getEmail());

    }
}
