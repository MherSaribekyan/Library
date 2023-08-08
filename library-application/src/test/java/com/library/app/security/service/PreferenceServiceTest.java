package com.library.app.security.service;

import com.library.app.repositories.persistence.User;
import com.library.app.repositories.persistence.UserPreference;
import com.library.app.repositories.repo.UserRepository;
import com.library.app.repositories.types.Role;
import com.library.app.services.preferences.PreferenceService;
import com.library.app.services.user.UserLoader;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class PreferenceServiceTest {

    @Autowired
    private PreferenceService service;

    @MockBean
    private UserRepository userRepository;

    @SuppressWarnings("unused")
    @MockBean
    private UserLoader userLoader;

    @Test
    void addUserPreferenceTest() {
        //preparation
        User user = getUser();

        when(this.userRepository.findByEmailAndDeletedIsNull(user.getEmail())).thenReturn(user);

        //source
        service.addUserPreference(user.getEmail(), "fakePreference");

        //verification
        verify(this.userRepository, times(1)).save(any());
        assertNotEquals(0, user.getUserPreferences().size());
    }

    @Test
    void addUserPreferencesTest() {
        //preparation
        User user = getUser();

        when(this.userRepository.findByEmailAndDeletedIsNull(user.getEmail())).thenReturn(user);

        //source
        this.service.addUserPreferences(user.getEmail(), List.of("fakePreference1", "fakePreference2"));

        //verification
        verify(this.userRepository, times(1)).save(any());
        assertEquals(2, user.getUserPreferences().size());
    }

    @Test
    void getUserPreferencesTest() {
        //preparation
        User user = getUser();
        user.setUserPreferences(List.of(new UserPreference(), new UserPreference()));

        when(this.userRepository.findByEmailAndDeletedIsNull(user.getEmail())).thenReturn(user);

        //source
        Set<String> userPreferences = this.service.getUserPreferences(user.getEmail());

        //verification
        assertEquals(1, userPreferences.size());
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
