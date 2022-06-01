package com.github.youssefagagg.springbootappsample.services;

import com.github.youssefagagg.springbootappsample.domin.Authority;
import com.github.youssefagagg.springbootappsample.domin.User;
import com.github.youssefagagg.springbootappsample.repository.AuthorityRepository;
import com.github.youssefagagg.springbootappsample.repository.UserRepository;
import com.github.youssefagagg.springbootappsample.security.AuthoritiesConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.*;

@Service
@Transactional
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;
    public boolean existsByUsername(String username) {
        Objects.requireNonNull(username);

        return userRepository.existsByUsernameIgnoreCase(username);
    }
    public boolean existsByEmail( String email) {
        Objects.requireNonNull(email);
        return userRepository.existsByEmailIgnoreCase(email);
    }
    public User CreateUser(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setActivated(false);
        user.setActivationKey(UUID.randomUUID().toString());
        Set<Authority> authorities=new HashSet<>();
        authorityRepository.findById(AuthoritiesConstants.USER).ifPresent(authorities::add);
        user.setAuthorities(authorities);
        userRepository.save(user);
        log.debug("Created new User: {}", user);
        return user;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findUserByActivationKey(key)
                .map(user -> {
                    user.setActivated(true);
                    user.setActivationKey(null);
                    log.debug("activate user: {}",user);
                    return user;
                });
    }
}
