package com.github.youssefagagg.springbootappsample.repository;

import com.github.youssefagagg.springbootappsample.domin.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StaffRepository extends JpaRepository<Staff,Long> {
    void deleteById(Long id);
}
