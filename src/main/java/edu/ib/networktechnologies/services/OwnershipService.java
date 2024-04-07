package edu.ib.networktechnologies.services;

import edu.ib.networktechnologies.entities.Auth;
import edu.ib.networktechnologies.exceptions.UserNotFoundException;
import edu.ib.networktechnologies.repositories.AuthRepository;

public abstract class OwnershipService {

    private final AuthRepository authRepository;

    public OwnershipService(AuthRepository authRepository) {
        this.authRepository = authRepository;
    }

    public boolean isOwner(String username, Long userId) {
        if(username == null || userId == null)
            return false;

        Auth auth = authRepository.findByUsername(username).orElseThrow(() -> UserNotFoundException.createWithUsername(username));
        return userId == auth.getUser().getUserId();
    }
}
