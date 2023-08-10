package com.library.app.security.service;

import com.library.app.repositories.persistence.User;
import com.library.app.repositories.types.Role;
import com.library.app.services.registration.RegistrationService;
import com.library.app.services.registration.model.RegistrationRequest;
import com.library.app.services.user.UserMapper;
import com.library.app.services.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class RegistrationServiceTest {

    @Autowired
    private RegistrationService service;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Test
    void testRegister() {
        //preparation
        User entity = getUser();
        RegistrationRequest request = new RegistrationRequest("fakeNAme", "fakePhone", "fakeEmail", "fakePassword");

        when(userMapper.toEntity(request)).thenReturn(entity);

        //source
        this.service.register(request);

        //verification
        verify(this.userService, times(1)).save(entity);
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
