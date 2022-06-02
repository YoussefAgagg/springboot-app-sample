package com.github.youssefagagg.springbootappsample.web.controller;

import com.github.youssefagagg.springbootappsample.domin.Department;
import com.github.youssefagagg.springbootappsample.domin.Staff;
import com.github.youssefagagg.springbootappsample.services.DepartmentService;
import com.github.youssefagagg.springbootappsample.services.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/entity")
@RequiredArgsConstructor
@Slf4j
public class StaffController {
    private final StaffService staffService;
    private final DepartmentService departmentService;

    @ModelAttribute(name = "departments" )
    public List<Department> departments(){
        return departmentService.getAllDepartments();
    }

    @GetMapping("/staff")
    public String getStaffs(Model model,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size){
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);
        var staffs=staffService.getAllStaffs(PageRequest.of(currentPage - 1, pageSize));
        int totalPages = staffs.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        model.addAttribute("staffs", staffs);
        return "staff";
    }

    @GetMapping("/createStaff")
    public String create(Model model){
        var departments =departmentService.getAllDepartments();
        model.addAttribute("staff", new Staff());
        return "create-staff";
    }
    @PostMapping("/createStaff")
    public String createStaff(@Valid @ModelAttribute Staff staff, Errors errors, Model model){
        log.debug("new staff: {}", staff);
        if (errors.hasErrors()){
            return "create-staff";
        }
        staffService.save(staff);

        return "redirect:/entity/staff";

    }
    @PostMapping("deleteStaff/{id}")
    public String deleteStaff(@PathVariable Long id){
        log.debug("delete staff {}",staffService.findById(id));
        staffService.delete(id);
        return "redirect:/entity/staff";
    }
}
