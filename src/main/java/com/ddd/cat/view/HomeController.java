package com.ddd.cat.view;

import com.ddd.cat.view.model.CatPicDTO;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import static com.ddd.cat.view.model.ViewModelAttribute.CAT_PICTURE;
import static com.ddd.cat.view.model.ViewModelAttribute.CAT_PREMIUM_PICTURE;

@Controller
public class HomeController {
    private final CatPicDTO baseCatPicture;
    private final CatPicDTO premiumCatPicture;

    public HomeController(CatPicDTO baseCatPicture, CatPicDTO premiumCatPicture) {
        this.baseCatPicture = baseCatPicture;
        this.premiumCatPicture = premiumCatPicture;
    }

    @GetMapping("/")
    public String cat(Model model) {
        if (baseCatPicture.getCatPic() == null) {
            return "index";
        } else {
            model.addAttribute(CAT_PICTURE.attribute(), baseCatPicture.getCatPic());
            return "cat";
        }
    }

    @GetMapping("/premium-signed")
    public String premiumCat(Model model) {
        if (premiumCatPicture.getCatPic() == null) {
            return "premium-signed";
        } else {
            model.addAttribute(CAT_PREMIUM_PICTURE.attribute(), premiumCatPicture.getCatPic());
            return "premium-cat";
        }
    }
}
