package com.github.youssefagagg.springbootappsample.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class AccessDeniedController {
    @RequestMapping("/access-denied")
    public String blockAccess(Model model) {
        return "access-denied";
    }


}
