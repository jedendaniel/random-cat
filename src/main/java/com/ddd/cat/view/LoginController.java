package com.ddd.cat.view;

import com.ddd.cat.auth.UserRegistrationService;
import com.ddd.cat.view.model.ViewModelAttribute;
import com.ddd.cat.view.model.ViewUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import static com.ddd.cat.view.model.ViewModelAttribute.LOGIN_ERROR;
import static com.ddd.cat.view.model.ViewModelAttribute.REGISTRATION_SUCCESS;


@Controller
public class LoginController {

    private final UserRegistrationService userRegistrationService;

    public LoginController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping("/login")
    public String loginForm(Model model, @ModelAttribute ViewUser viewUser) {
        model.addAttribute(LOGIN_ERROR.attribute(), model.getAttribute(LOGIN_ERROR.attribute()));
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model, @ModelAttribute ViewUser viewUser) {
        model.addAttribute(LOGIN_ERROR.attribute(), true);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, @ModelAttribute ViewUser viewUser) throws ServletException {
        request.logout();
        HttpSession session = request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        for(Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }
        return "login";
    }
    @GetMapping("/registration")
    public String registrationForm(Model model, @ModelAttribute ViewUser viewUser) {
//        model.addAttribute(LOGIN_ERROR.attribute(), model.getAttribute(LOGIN_ERROR.attribute()));
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(HttpServletRequest request, Model model, @ModelAttribute ViewUser viewUser) throws ServletException {
        ViewModelAttribute viewModelAttribute = userRegistrationService.register(viewUser);
        if (viewModelAttribute == REGISTRATION_SUCCESS) {
            request.login(viewUser.getUsername(), viewUser.getPassword());
            return "index";
        } else {
            model.addAttribute(viewModelAttribute.attribute(), true);
            return "registration";
        }
    }
}
