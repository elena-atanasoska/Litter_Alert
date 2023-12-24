package com.example.litteralert.web;

import com.example.litteralert.model.User;
import com.example.litteralert.model.exceptions.InvalidUserCredentialsException;
import com.example.litteralert.service.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/login")
public class LoginController {
    private final AuthService authService;

    public LoginController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String getLoginPage() {
        return "mapa";
    }

    @PostMapping
    public String login(HttpServletRequest request, Model model) {
        User user = null;
        try{
            user = this.authService.login(request.getParameter("username"),
                    request.getParameter("password"));
            request.getSession().setAttribute("user", user);
            return "redirect:/reports";
        }
        catch (InvalidUserCredentialsException exception) {
            model.addAttribute("hasLoginError", true);
            model.addAttribute("loginError", exception.getMessage());
            return "mapa";
        }
    }
}
