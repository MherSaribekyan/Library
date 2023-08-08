package com.library.app.services.registration;

import com.library.app.repositories.persistence.User;
import com.library.app.services.registration.model.RegistrationRequest;
import com.library.app.services.user.UserMapper;
import com.library.app.services.user.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LibraryRegistrationService implements RegistrationService {

    private final UserMapper userMapper;

    private final UserService userService;

    @Override
    public void register(final RegistrationRequest request) {
        final User entity = this.userMapper.toEntity(request);
        this.userService.save(entity);
    }


    public LibraryRegistrationService(final UserMapper userMapper,
                                      final UserService userService) {
        this.userMapper = userMapper;
        this.userService = userService;
    }
}
