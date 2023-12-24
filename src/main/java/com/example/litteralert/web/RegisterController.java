package com.example.litteralert.web;

import com.example.litteralert.model.exceptions.InvalidArgumentsException;
import com.example.litteralert.model.exceptions.PasswordsDoNotMatchException;
import com.example.litteralert.model.exceptions.UsernameAlreadyExistsException;
import com.example.litteralert.service.AuthService;
import com.example.litteralert.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/register")
public class RegisterController {
    private final AuthService authService;
    private final UserService userService;

    public RegisterController(AuthService authService, UserService userService) {
        this.authService = authService;
        this.userService = userService;
    }

    @GetMapping
    public String getRegisterPage(@RequestParam(required = false) String error, Model model) {
        if(error != null && !error.isEmpty()) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        return "register";
    }

    @PostMapping
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String repeatedPassword,
                           @RequestParam String name,
                           @RequestParam String phoneNumber,
                           @RequestParam String surname) {
        try{
            this.userService.register(username, password, repeatedPassword, name, surname,phoneNumber);
            return "redirect:/reports";
        } catch (InvalidArgumentsException | PasswordsDoNotMatchException | UsernameAlreadyExistsException exception) {
            return "redirect:/reports?registerError=" + exception.getMessage();
        }
    }
}
