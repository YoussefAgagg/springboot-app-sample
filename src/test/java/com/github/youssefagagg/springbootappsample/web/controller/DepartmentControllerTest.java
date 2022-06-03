package com.github.youssefagagg.springbootappsample.web.controller;

import com.github.youssefagagg.springbootappsample.domin.Department;
import com.github.youssefagagg.springbootappsample.domin.Department;
import com.github.youssefagagg.springbootappsample.domin.enumeration.Gender;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DepartmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Test calling /entity/department endpoint without authentication redirct to login page.")
    public void callingDepartmentUnauthenticated() throws Exception {
        mvc.perform(get("/entity/department"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @DisplayName("Test calling /entity/department endpoint authenticated returns department.html .")
    @WithMockUser
    public void callingDepartmentAuthenticated() throws Exception {
        mvc.perform(get("/entity/department"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("departments"))
                .andExpect(view().name("department"));
    }
    @Test
    @DisplayName("Test calling /entity/creatDepartment endpoint authenticated returns create-department.html .")
    @WithMockUser
    public void callingGetCreateDepartmentAuthenticated() throws Exception {
        mvc.perform(get("/entity/createDepartment"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("department"))
                .andExpect(view().name("create-department"));
    }
    @Test
    @DisplayName("Test calling /entity/creatDepartment post method endpoint with authenticated user that has USER_ROLE returns access-denied.html .")
    @WithMockUser
    public void callingPostCreateDepartmentAuthenticatedWithUserRole() throws Exception {
        var department=new Department();

        mvc.perform(post("/entity/createDepartment").with(csrf())
                        .flashAttr("department", department))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/access-denied"));;

    }
    @Test
    @DisplayName("Test calling /entity/creatDepartment post method endpoint with authenticated user that has ADMIN_ROLE with invalid data .")
    @WithMockUser(username="admin",authorities ={"ROLE_USER","ROLE_ADMIN"})
    public void callingPostCreateDepartmentAuthenticatedWithAdminRoleAdnInvalidData() throws Exception {
        var department=new Department();

        mvc.perform(post("/entity/createDepartment").with(csrf())
                        .flashAttr("department", department))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("department"))
                .andExpect(view().name("create-department"));

    }
    @Test
    @DisplayName("Test calling /entity/creatDepartment post method endpoint with authenticated user that has ADMIN_ROLE with valid data .")
    @WithMockUser(username="admin",authorities ={"ROLE_USER","ROLE_ADMIN"})
    public void callingPostCreateDepartmentAuthenticatedWithAdminRoleAndValidData() throws Exception {
        var department=new Department();
        department.setName("youssef");
        department.setCode("123e");

        mvc.perform(post("/entity/createDepartment").with(csrf())
                        .flashAttr("department", department))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/entity/department"));

    }
    @Test
    @DisplayName("Test calling /entity/deleteDepartment/{id} post method endpoint with authenticated user that has USER_ROLE returns access-denied.html .")
    @WithMockUser
    public void callingPostDeleteDepartmentAuthenticatedWithUserRole() throws Exception {

        mvc.perform(post("/entity/deleteDepartment/1").with(csrf()))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/access-denied"));;

    }

}