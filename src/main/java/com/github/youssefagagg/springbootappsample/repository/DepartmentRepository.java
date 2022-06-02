package com.github.youssefagagg.springbootappsample.repository;

import com.github.youssefagagg.springbootappsample.domin.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
