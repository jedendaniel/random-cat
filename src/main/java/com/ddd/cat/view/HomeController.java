package com.ddd.cat.view;

import com.ddd.cat.service.RandomCatService;
import com.ddd.cat.view.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Base64;
import java.util.UUID;

@Controller
public class HomeController {
    private final RandomCatService randomCatService;

    public HomeController(RandomCatService randomCatService) {
        this.randomCatService = randomCatService;
    }

    @GetMapping()
    public String index(Model model) {
        model.addAttribute("user", new User("stranger", "pass"));
        return "index";
    }

    @GetMapping("/cat")
    public String cat(Model model) {
        String pictureBase64 = Base64.getEncoder().encodeToString(randomCatService.getRandomCatPic());
        model.addAttribute("catPicture", pictureBase64);
        return "cat";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String loginSubmit(@ModelAttribute User user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("sessionId", UUID.randomUUID());
        System.out.println(user);
        return "index";
    }
}
