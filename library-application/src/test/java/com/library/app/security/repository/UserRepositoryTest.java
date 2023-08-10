package com.library.app.security.repository;


import com.library.app.repositories.persistence.User;
import com.library.app.repositories.repo.UserRepository;
import com.library.app.repositories.types.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @BeforeEach
    public void setup() {
        repository.deleteAll();
    }

    @Test
    void saveUserTest() {
        //preparation
        User user = getUser();

        //source
        User save = this.repository.save(user);

        //verification
        Optional<User> byId = this.repository.findById(save.getId());
        assertTrue(byId.isPresent());
    }

    @Test
    void findUserByEmailTest() {
        //preparation
        User user = getUser();
        User save = this.repository.save(user);

        //source
        User byEmailAndDeletedIsNull = this.repository.findByEmailAndDeletedIsNull(save.getEmail());

        //verification
        assertNotNull(byEmailAndDeletedIsNull);
    }

    @Test
    void findUserByEmailNegativeTest() {
        //verification
        assertNull(this.repository.findByEmailAndDeletedIsNull("anyFakeEmail"));
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
