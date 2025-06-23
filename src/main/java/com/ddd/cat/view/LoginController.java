package com.ddd.cat.view;

import com.ddd.cat.auth.UserRegistrationService;
import com.ddd.cat.view.model.UserDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

import static com.ddd.cat.view.model.ViewModelAttribute.LOGIN_ERROR;


@Controller
public class LoginController {

    private final UserRegistrationService userRegistrationService;

    public LoginController(UserRegistrationService userRegistrationService) {
        this.userRegistrationService = userRegistrationService;
    }

    @GetMapping("/login")
    public String loginForm(Model model, @ModelAttribute UserDTO userDTO) {
        model.addAttribute(LOGIN_ERROR.attribute(), model.getAttribute(LOGIN_ERROR.attribute()));
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model, @ModelAttribute UserDTO userDTO) {
        model.addAttribute(LOGIN_ERROR.attribute(), true);
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, @ModelAttribute UserDTO userDTO) throws ServletException {
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
    public String registrationForm(Model model, @ModelAttribute UserDTO userDTO) {
        model.addAttribute("validationErrorsMap", Map.of());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(HttpServletRequest request, Model model, @ModelAttribute UserDTO userDTO) throws ServletException {
            Map<String, String> validationErrors = userRegistrationService.register(userDTO);
        if (validationErrors.isEmpty()) {
            request.login(userDTO.getUsername(), userDTO.getPassword());
            return "index";
        } else {
                model.addAttribute("validationErrorsMap", validationErrors);
            return "registration";
        }
    }
}
