package com.github.youssefagagg.springbootappsample.web.rest;



import com.github.youssefagagg.springbootappsample.domin.Department;
import com.github.youssefagagg.springbootappsample.services.DepartmentService;
import com.github.youssefagagg.springbootappsample.web.rest.exception.BadRequestAlertException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.net.URISyntaxException;
import java.util.List;



@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class DepartmentResource {

    private final DepartmentService departmentService;

    @PostMapping("/department")
    public ResponseEntity<Department> createDepartment(@Valid @RequestBody Department department) throws URISyntaxException {
        log.debug("REST request to save Department : {}", department);
        if (department.getId() != null) {
            throw new BadRequestAlertException("A new department cannot already have an ID");
        }
        Department result = departmentService.save(department);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);
    }

    @PutMapping("/department/{id}")
    public ResponseEntity<Department> updateDepartment(
            @PathVariable(value = "id", required = false) final Long id,
            @Valid @RequestBody Department department
    ){
        log.debug("REST request to update Department : {}, {}", id, department);
        if (department.getId() == null) {
            throw new BadRequestAlertException("Invalid id");
        }

        if (!departmentService.existsById(id)) {
            throw new BadRequestAlertException("id not found");
        }

        Department result = departmentService.save(department);
        return ResponseEntity
                .ok()
                .body(result);
    }


    @GetMapping("/department")
    public ResponseEntity<List<Department>> getAllDepartment(Pageable pageable) {
        log.debug("REST request to get Department ");
        var departments = departmentService.getAllDepartments();

        return ResponseEntity.ok().body(departments);
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<Department> getDepartment(@PathVariable Long id) {
        log.debug("REST request to get Department : {}", id);
        Department department = departmentService.findById(id).orElseThrow();
        return ResponseEntity.ok().body(department);
    }

    @DeleteMapping("/department/{id}")
    public void deleteDepartment(@PathVariable Long id) {
        log.debug("REST request to delete Department : {}", id);
        departmentService.delete(id);
    }
}
