package com.github.youssefagagg.springbootappsample.web.controller;

import com.github.youssefagagg.springbootappsample.domin.User;
import com.github.youssefagagg.springbootappsample.exception.AccountActivationException;
import com.github.youssefagagg.springbootappsample.services.MailService;
import com.github.youssefagagg.springbootappsample.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/account")
public class AccountController {

    private final UserService userService;
    private final MailService mailService;

    @GetMapping("/register")
    public String register(Model model){
        model.addAttribute("user", new User());

        return "sign-up";
    }
    @PostMapping("/register")
    public String registerNewAccount(@Valid @ModelAttribute User user, Errors errors, Model model){

        if (errors.hasErrors()){
            return "sign-up";
        }
        if (userService.existsByUsername(user.getUsername()) || userService.existsByEmail(user.getEmail())) {

            log.warn("username or email is already exist.");
            model.addAttribute("error", "username or email is already exist.");
            return "sign-up";
        }else{
            userService.CreateUser(user);
            System.out.println(user);
            mailService.sendActivationEmail(user);
        }
        return "redirect:/login";

    }
    @GetMapping("/activate")
    public String activateAccount(@RequestParam(value = "key") String key) {
        Optional<User> user = userService.activateRegistration(key);
        if (!user.isPresent()) {
            throw new AccountActivationException("No user was found for this activation key");
        }
        return "your account has bean activated";
    }
}
