package com.ddd.cat.view;

import com.ddd.cat.domain.RandomCatService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Base64;

import static com.ddd.cat.view.model.ViewModelAttribute.CAT_PICTURE;

@Controller
public class HomeController {
    private final RandomCatService randomCatService;
    public HomeController(RandomCatService randomCatService) {
        this.randomCatService = randomCatService;
    }

    @GetMapping("/cat")
    public String cat(Model model) {
        String pictureBase64 = Base64.getEncoder().encodeToString(randomCatService.getRandomCatPic());
        model.addAttribute(CAT_PICTURE.attribute(), pictureBase64);
        return "cat";
    }
}
