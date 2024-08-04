package com.ddd.cat.view;

import com.ddd.cat.service.RandomCatService;
import com.ddd.cat.view.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Base64;

@Controller
public class HomeController {
    private final RandomCatService randomCatService;
    public HomeController(RandomCatService randomCatService) {
        this.randomCatService = randomCatService;
    }

    @GetMapping({"/", "/index", "/home"})
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
}
