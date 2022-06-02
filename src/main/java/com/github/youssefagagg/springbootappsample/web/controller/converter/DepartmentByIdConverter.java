package com.github.youssefagagg.springbootappsample.web.controller.converter;

import com.github.youssefagagg.springbootappsample.domin.Department;
import com.github.youssefagagg.springbootappsample.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DepartmentByIdConverter implements Converter<Long, Department> {
    private final DepartmentService departmentService;
    @Override
    public Department convert(Long source) {

        return departmentService.findById(source).orElse(null);
    }
}
