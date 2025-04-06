package com.ddd.cat.view;

import com.ddd.cat.view.model.ViewUser;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String loginForm(Model model, @ModelAttribute ViewUser viewUser) {
        model.addAttribute("loginError", model.getAttribute("loginError"));
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model, @ModelAttribute ViewUser viewUser) {
        model.addAttribute("loginError", true);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(@ModelAttribute ViewUser viewUser, HttpServletRequest request) {
        SecurityContextHolder.clearContext();
        HttpSession session= request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        for(Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }
        return "login";
    }
    @GetMapping("/registration")
    public String registrationForm(@ModelAttribute ViewUser viewUser) {
        return "registration";
    }
    @PostMapping("/registration")
    public String registration(@ModelAttribute ViewUser viewUser) {
        return "registration";
    }
}
