package com.github.youssefagagg.springbootappsample.repository;

import com.github.youssefagagg.springbootappsample.domin.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<Authority,String> {

}
