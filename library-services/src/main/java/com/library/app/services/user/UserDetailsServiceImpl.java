package com.library.app.services.user;

import com.library.app.repositories.persistence.User;
import com.library.app.repositories.repo.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {
        final User user = userRepository.findByEmailAndDeletedIsNull(email);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), getAuthorities(user.getRole().name()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(final String userRole) {
        return Collections.singletonList(new SimpleGrantedAuthority(userRole));
    }

    public UserDetailsServiceImpl(final UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
