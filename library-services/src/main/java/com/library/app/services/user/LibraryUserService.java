package com.library.app.services.user;

import com.library.app.repositories.persistence.User;
import com.library.app.repositories.repo.UserRepository;
import com.library.app.repositories.types.Role;
import com.library.app.services.admin.model.GrantAdmin;
import com.library.app.services.exception.ExceptionCode;
import com.library.app.services.exception.ServiceException;
import com.library.app.services.registration.model.RegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Supplier;

@Service
@Slf4j
public class LibraryUserService implements UserService {

    private final UserRepository repository;

    private final UserMapper userMapper;

    @Override
    public void save(final User user) {
        log.info("saving user: {}", user.getName());
        this.repository.save(user);
    }

    @Override
    public void saveAll(final List<User> users) {
        this.repository.saveAll(users);
    }

    @Override
    public void createAdmin(RegistrationRequest request) {
        final User entity = this.userMapper.toEntity(request);
        entity.setRole(Role.ADMIN);
        this.repository.save(entity);
    }

    @Override
    @Transactional
    public void grantAdminAccess(final GrantAdmin grantAdmin) {
        final String userId = grantAdmin.userId();
        final Optional<User> byId = this.repository.findById(UUID.fromString(userId));
        final User user = byId.orElseThrow(this.trow(userId));
        user.setRole(Role.ADMIN);
        this.repository.save(user);
    }

    private Supplier<ServiceException> trow(final String userId) {
        return () ->
                new ServiceException(ExceptionCode.UNPROCESSABLE_ENTITY, "User not found, userId: ", userId);
    }

    public LibraryUserService(final UserRepository repository,
                              final UserMapper userMapper) {
        this.repository = repository;
        this.userMapper = userMapper;
    }
}
