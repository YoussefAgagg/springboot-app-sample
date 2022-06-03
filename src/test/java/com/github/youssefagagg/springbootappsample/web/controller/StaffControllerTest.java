package com.github.youssefagagg.springbootappsample.web.controller;

import com.github.youssefagagg.springbootappsample.domin.Department;
import com.github.youssefagagg.springbootappsample.domin.Staff;
import com.github.youssefagagg.springbootappsample.domin.enumeration.Gender;
import com.github.youssefagagg.springbootappsample.domin.enumeration.StaffPosition;
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

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class StaffControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Test calling /entity/staff endpoint without authentication redirct to login page.")
    public void callingStaffUnauthenticated() throws Exception {
        mvc.perform(get("/entity/staff"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    @DisplayName("Test calling /entity/staff endpoint authenticated returns staff.html .")
    @WithMockUser
    public void callingStaffAuthenticated() throws Exception {
        mvc.perform(get("/entity/staff"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("staffs"))
                .andExpect(view().name("staff"));
    }
    @Test
    @DisplayName("Test calling /entity/creatStaff endpoint authenticated returns create-staff.html .")
    @WithMockUser
    public void callingGetCreateStaffAuthenticated() throws Exception {
        mvc.perform(get("/entity/createStaff"))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("staff"))
                .andExpect(view().name("create-staff"));
    }
    @Test
    @DisplayName("Test calling /entity/creatStaff post method endpoint with authenticated user that has USER_ROLE returns access-denied.html .")
    @WithMockUser
    public void callingPostCreateStaffAuthenticatedWithUserRole() throws Exception {
        var staff=new Staff();
        staff.setDateOfBirth(LocalDate.now());
        var d=new Department();
        d.setId(1l);
        staff.setDepartment(d);
        staff.setName("youssef");
        mvc.perform(post("/entity/createStaff").with(csrf())
                .flashAttr("staff", staff))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/access-denied"));;

    }
    @Test
    @DisplayName("Test calling /entity/creatStaff post method endpoint with authenticated user that has ADMIN_ROLE with invalid data .")
    @WithMockUser(username="admin",authorities ={"ROLE_USER","ROLE_ADMIN"})
    public void callingPostCreateStaffAuthenticatedWithAdminRoleAdnInvalidData() throws Exception {
        var staff=new Staff();
        staff.setDateOfBirth(LocalDate.now());
        var d=new Department();
        d.setId(1l);
        staff.setDepartment(d);
        staff.setName("youssef");
        mvc.perform(post("/entity/createStaff").with(csrf())
                        .flashAttr("staff", staff))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("staff"))
                .andExpect(view().name("create-staff"));

    }
    @Test
    @DisplayName("Test calling /entity/creatStaff post method endpoint with authenticated user that has ADMIN_ROLE with valid data .")
    @WithMockUser(username="admin",authorities ={"ROLE_USER","ROLE_ADMIN"})
    public void callingPostCreateStaffAuthenticatedWithAdminRoleAndValidData() throws Exception {
        var staff=new Staff();
        staff.setDateOfBirth(LocalDate.now());
        var d=new Department();
        d.setId(1l);
        staff.setDepartment(d);
        staff.setEmail("you@gmail.com");
        staff.setPhone("2341");
        staff.setName("youssef");
        staff.setGender(Gender.MALE);
        staff.setPosition(StaffPosition.CEO);
        mvc.perform(post("/entity/createStaff").with(csrf())
                        .flashAttr("staff", staff))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/entity/staff"));

    }
    @Test
    @DisplayName("Test calling /entity/deleteStaff/{id} post method endpoint with authenticated user that has USER_ROLE returns access-denied.html .")
    @WithMockUser
    public void callingPostDeleteStaffAuthenticatedWithUserRole() throws Exception {

        mvc.perform(post("/entity/deleteStaff/1").with(csrf()))
                .andExpect(status().isForbidden())
                .andExpect(forwardedUrl("/access-denied"));;

    }
    @Test
    @DisplayName("Test calling /entity/delete/{id} Staff post method endpoint with authenticated user that has ADMIN_ROLE returns staff.html .")
    @WithMockUser(username="admin",authorities ={"ROLE_USER","ROLE_ADMIN"})
    public void callingPostDeleteStaffAuthenticatedWithAdminRole() throws Exception {

        mvc.perform(post("/entity/deleteStaff/1").with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/entity/staff"));

    }
}