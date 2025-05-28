package com.ddd.cat.view;

import com.ddd.cat.auth.UserRegistrationService;
import com.ddd.cat.view.model.ViewModelAttribute;
import com.ddd.cat.view.model.ViewUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

import static com.ddd.cat.view.model.ViewModelAttribute.LOGIN_ERROR;
import static com.ddd.cat.view.model.ViewModelAttribute.REGISTRATION_NAME_TOO_LONG;
import static com.ddd.cat.view.model.ViewModelAttribute.REGISTRATION_USER_EXISTS;


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
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(HttpServletRequest request, Model model, @ModelAttribute ViewUser viewUser) throws ServletException {
        Optional<ViewModelAttribute> viewModelAttribute = validateRegistrationData(viewUser);
        if (viewModelAttribute.isPresent()) {
            model.addAttribute(viewModelAttribute.get().attribute(), "true");
            return "registration";
        } else {
            userRegistrationService.register(viewUser);
            request.login(viewUser.getUsername(), viewUser.getPassword());
            return "index";
        }
    }

    private Optional<ViewModelAttribute> validateRegistrationData(ViewUser viewUser) {
        if (viewUser.getUsername().length() > 16) {
            return Optional.of(REGISTRATION_NAME_TOO_LONG);
        } else if (userRegistrationService.UserExists(viewUser)) {
            return Optional.of(REGISTRATION_USER_EXISTS);
        }
        return Optional.empty();
    }
}
