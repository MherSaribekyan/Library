package com.library.app.services.preferences;

import com.library.app.repositories.persistence.User;
import com.library.app.repositories.persistence.UserPreference;
import com.library.app.repositories.repo.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LibraryPreferenceService implements PreferenceService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addUserPreference(final String principalName, final String preference) {
        final User user = this.userRepository.findByEmailAndDeletedIsNull(principalName);
        final UserPreference userPreference = new UserPreference();
        userPreference.setGenre(preference);
        userPreference.setOld(LocalDate.now());
        user.addPreference(userPreference);
        this.userRepository.save(user);
    }

    @Override
    @Transactional
    public void addUserPreferences(final String principalName, final List<String> preferences) {
        final User user = this.userRepository.findByEmailAndDeletedIsNull(principalName);
        preferences.forEach(preference -> {
            final UserPreference userPreference = new UserPreference();
            userPreference.setGenre(preference);
            userPreference.setOld(LocalDate.now());
            user.addPreference(userPreference);
        });
        this.userRepository.save(user);
    }

    @Override
    public Set<String> getUserPreferences(final String principalName) {
        final User user = this.userRepository.findByEmailAndDeletedIsNull(principalName);
        return user.getUserPreferences()
                .stream().map(UserPreference::getGenre)
                .collect(Collectors.toSet());
    }

    public LibraryPreferenceService(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
