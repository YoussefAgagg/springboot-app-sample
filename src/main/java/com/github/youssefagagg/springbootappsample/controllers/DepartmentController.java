package com.github.youssefagagg.springbootappsample.controllers;

import com.github.youssefagagg.springbootappsample.domin.Department;
import com.github.youssefagagg.springbootappsample.domin.Department;
import com.github.youssefagagg.springbootappsample.services.DepartmentService;
import com.github.youssefagagg.springbootappsample.services.DepartmentService;
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
public class DepartmentController {
    private final DepartmentService departmentService;



    @GetMapping("/department")
    public String getDepartments(Model model){

        model.addAttribute("departments", departmentService.getAllDepartments());
        return "department";
    }

    @GetMapping("/createDepartment")
    public String create(Model model){
        model.addAttribute("department", new Department());
        return "create-department";
    }
    @PostMapping("/createDepartment")
    public String createDepartment(@Valid @ModelAttribute Department department, Errors errors, Model model){
        log.debug("new department: {}", department);
        if (errors.hasErrors()){
            return "create-department";
        }
        departmentService.save(department);

        return "redirect:/entity/department";

    }
    @PostMapping("deleteDepartment/{id}")
    public String deleteDepartment(@PathVariable Long id){
        log.debug("delete department {}",departmentService.findById(id));
        departmentService.delete(id);
        return "redirect:/entity/department";
    }
}
