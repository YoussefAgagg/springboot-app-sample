package com.github.youssefagagg.springbootappsample.repository;

import com.github.youssefagagg.springbootappsample.domin.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByEmail(String email);
    Optional<User>  findUserByUsername(String username);
    Optional<User>  findUserByActivationKey(String key);

    boolean existsByUsernameIgnoreCase(String username);
    boolean existsByEmailIgnoreCase(String email);
}
