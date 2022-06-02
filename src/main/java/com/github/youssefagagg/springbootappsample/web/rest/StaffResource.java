package com.github.youssefagagg.springbootappsample.web.rest;



import com.github.youssefagagg.springbootappsample.domin.Staff;
import com.github.youssefagagg.springbootappsample.services.StaffService;
import com.github.youssefagagg.springbootappsample.web.rest.exception.BadRequestAlertException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.HeaderUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Slf4j
public class StaffResource {


    private static final String ENTITY_NAME = "staff";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final StaffService staffService;




    @PostMapping("/staff")
    public ResponseEntity<Staff> createStaff(@Valid @RequestBody Staff staff) throws URISyntaxException {
        log.debug("REST request to save Staff : {}", staff);
        if (staff.getId() != null) {
            throw new BadRequestAlertException("A new staff cannot already have an ID");
        }
        Staff result = staffService.save(staff);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(result);
    }

    @PutMapping("/staff/{id}")
    public ResponseEntity<Staff> updateStaff(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody Staff staff
    ){
        log.debug("REST request to update Staff : {}, {}", id, staff);
        if (staff.getId() == null) {
            throw new BadRequestAlertException("Invalid id");
        }

        if (!staffService.existsById(id)) {
            throw new BadRequestAlertException("id not found");
        }

        Staff result = staffService.save(staff);
        return ResponseEntity
            .ok()
            .body(result);
    }


    @GetMapping("/staff")
    public ResponseEntity<List<Staff>> getAllStaff(Pageable pageable) {
        log.debug("REST request to get Staff ");
        Page<Staff> page = staffService.getAllStaffs(pageable);

        return ResponseEntity.ok().body(page.getContent());
    }

    @GetMapping("/staff/{id}")
    public ResponseEntity<Staff> getStaff(@PathVariable Long id) {
        log.debug("REST request to get Staff : {}", id);
        Staff staff = staffService.findById(id).orElseThrow();
        return ResponseEntity.ok().body(staff);
    }

    @DeleteMapping("/staff/{id}")
    public void deleteStaff(@PathVariable Long id) {
        log.debug("REST request to delete Staff : {}", id);
        staffService.delete(id);
    }
}
