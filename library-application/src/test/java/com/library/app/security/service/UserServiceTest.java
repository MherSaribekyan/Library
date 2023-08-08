package com.library.app.security.service;

import com.library.app.repositories.persistence.User;
import com.library.app.repositories.repo.UserRepository;
import com.library.app.repositories.types.Role;
import com.library.app.services.admin.model.GrantAdmin;
import com.library.app.services.registration.model.RegistrationRequest;
import com.library.app.services.user.LibraryUserService;
import com.library.app.services.user.UserLoader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private LibraryUserService service;

    @MockBean
    private UserRepository repository;

    @SuppressWarnings("unused")
    @MockBean
    private UserLoader userLoader;

    @Test
    void saveUserTest() {
        //preparation
        User user = getUser();

        //source
        this.service.save(user);

        //verification
        verify(this.repository, times(1)).save(eq(user));
    }

    @Test
    void saveAllTest() {
        //preparation
        User user = getUser();
        List<User> userList = List.of(user);

        //source
        this.service.saveAll(userList);

        //verification
        verify(this.repository, times(1)).saveAll(eq(userList));
    }

    @Test
    void createAdminTest() {
        //preparation
        RegistrationRequest request = new RegistrationRequest("fakeNAme", "fakePhone", "fakeEmail", "fakePassword");

        //source
        this.service.createAdmin(request);

        //verification
        verify(this.repository, times(1)).save(any());
    }

    @Test
    void grantAdminTest() {
        //preparation
        UUID userId = UUID.randomUUID();
        User user = getUser();

        when(this.repository.findById(userId)).thenReturn(Optional.of(user));

        //source
        this.service.grantAdminAccess(new GrantAdmin(userId.toString()));

        //verification
        verify(this.repository, times(1)).save(any());
    }

    private User getUser() {
        User user = new User();
        user.setName("fakeName");
        user.setRole(Role.USER);
        user.setPassword("fakePassword");
        user.setEmail("fakeEmail");
        user.setPhone("fakePhone");
        return user;
    }
}
