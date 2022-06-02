package com.github.youssefagagg.springbootappsample.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class AccessDeniedController {
    @RequestMapping("/access-denied")
    public String blockAccess(Model model) {
        return "access-denied";
    }


}
