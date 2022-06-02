package com.github.youssefagagg.springbootappsample.services;

import com.github.youssefagagg.springbootappsample.domin.Staff;
import com.github.youssefagagg.springbootappsample.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class StaffService {
    private final StaffRepository staffRepository;

    public Page<Staff> getAllStaffs(Pageable pageable) {
        return staffRepository.findAll(pageable);
    }

    public Staff findById(Long id) {
       return staffRepository.findById(id).orElse(null);
    }

    public Staff save(Staff staff) {
        return staffRepository.save(staff);
    }

    public void delete(Long id) {
        staffRepository.deleteById(id);
    }
}
