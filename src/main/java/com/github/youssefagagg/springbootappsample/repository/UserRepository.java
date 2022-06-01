package com.github.youssefagagg.springbootappsample.repository;

import com.github.youssefagagg.springbootappsample.domin.USER;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<USER, Long> {
    Optional<USER> findUserByEmail(String email);
    Optional<USER>  findUserByUsername(String username);
}
