package edu.ib.networktechnologies.services;

import edu.ib.networktechnologies.controllers.dto.user.GetUserDto;
import edu.ib.networktechnologies.controllers.dto.user.PatchUserDto;
import edu.ib.networktechnologies.controllers.dto.user.PatchUserResponseDto;
import edu.ib.networktechnologies.entities.Auth;
import edu.ib.networktechnologies.entities.User;
import edu.ib.networktechnologies.exceptions.UserNotFoundException;
import edu.ib.networktechnologies.repositories.AuthRepository;
import edu.ib.networktechnologies.repositories.LoanRepository;
import edu.ib.networktechnologies.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService extends OwnershipService {

    private final UserRepository userRepository;
    private final AuthRepository authRepository;
    private final LoanRepository loanRepository;

    @Autowired
    public UserService(AuthRepository authRepository, UserRepository userRepository, LoanRepository loanRepository) {
        super(authRepository);
        this.userRepository = userRepository;
        this.authRepository = authRepository;
        this.loanRepository = loanRepository;
    }

    public GetUserDto getUserByUsername(String username) {
        Auth auth = authRepository.findByUsername(username).orElseThrow(() -> UserNotFoundException.createWithUsername(username));
        User user = auth.getUser();
        return new GetUserDto(user.getUserId(), user.getName(),  user.getLastName(), user.getEmail());
    }

    public List<GetUserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(this::mapUser).toList();
    }

    private GetUserDto mapUser(User user) {
        return new GetUserDto(user.getUserId(), user.getName(), user.getLastName(), user.getEmail());
    }

    public PatchUserResponseDto update(long id, PatchUserDto dto) {
        User user = userRepository.findById(id).orElseThrow(() -> UserNotFoundException.createWithId(id));

        if(dto.getEmail() != null) user.setEmail(dto.getEmail());
        if(dto.getName() != null) user.setName(dto.getName());
        if(dto.getLastName() != null) user.setLastName(dto.getLastName());

        userRepository.save(user);
        return new PatchUserResponseDto(user.getUserId(), user.getName(), user.getLastName(), user.getEmail());

    }
    @Transactional
    public void delete(long id) {
        if(loanRepository.existsByUserUserId(id)) {
            loanRepository.deleteAllForUser(id);
        }
        authRepository.deleteByUserUserId(id);
        userRepository.deleteById(id);
    }

}
