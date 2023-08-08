package com.library.app.services.user;

import com.library.app.repositories.persistence.User;
import com.library.app.repositories.types.Role;
import com.library.app.services.mapper.EntityMapper;
import com.library.app.services.registration.model.RegistrationRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserMapper implements EntityMapper<RegistrationRequest, User> {

    private final PasswordEncoder passwordEncoder;


    @Override
    public User toEntity(RegistrationRequest request) {
        final User user = new User();
        user.setPhone(request.phone());
        user.setName(request.name());
        user.setRole(Role.USER);
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));
        return user;
    }

    public UserMapper(final PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
