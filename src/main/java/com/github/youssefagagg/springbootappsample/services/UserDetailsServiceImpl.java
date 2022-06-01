package com.github.youssefagagg.springbootappsample.services;

import com.github.youssefagagg.springbootappsample.domin.USER;
import com.github.youssefagagg.springbootappsample.exception.UserNotActivatedException;
import com.github.youssefagagg.springbootappsample.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException {
        log.debug("Authenticating {}", usernameOrEmail);
        // Ensure that usernameOrEmail is not empty or null.
        if (!(Objects.isNull(usernameOrEmail)||usernameOrEmail.isBlank())) {
            var storedUser =
                    EmailValidator.getInstance().isValid(usernameOrEmail)
                            ? userRepository.findUserByEmail(usernameOrEmail)
                            : userRepository.findUserByUsername(usernameOrEmail);

           return storedUser.map(user -> createSpringSecurityUser( user))
                    .orElseThrow(() -> new UsernameNotFoundException("User with email " + usernameOrEmail + " was not found in the database"));

        }
        return null;
    }
    private org.springframework.security.core.userdetails.User createSpringSecurityUser( USER user) {
        if (!user.isActivated()) {
            throw new UserNotActivatedException("User " + user.getUsername() + " was not activated");
        }
        List<GrantedAuthority> grantedAuthorities = user
                .getAuthorities()
                .stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
