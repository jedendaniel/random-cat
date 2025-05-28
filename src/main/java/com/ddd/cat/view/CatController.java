package com.ddd.cat.view;

import com.ddd.cat.domain.RandomCatService;
import com.ddd.cat.view.model.ViewCatPic;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Base64;

import static com.ddd.cat.view.model.ViewModelAttribute.CAT_PICTURE;
import static com.ddd.cat.view.model.ViewModelAttribute.CAT_PREMIUM_PICTURE;

@Controller
public class CatController {
    private final RandomCatService randomCatService;
    private final ViewCatPic baseCatPicture;
    private final ViewCatPic premiumCatPicture;
    public CatController(RandomCatService randomCatService, ViewCatPic baseCatPicture, ViewCatPic premiumCatPicture) {
        this.randomCatService = randomCatService;
        this.baseCatPicture = baseCatPicture;
        this.premiumCatPicture = premiumCatPicture;
    }

    @GetMapping("/cat")
    public String cat(Model model) {
        if (baseCatPicture.getCatPic() == null) {
            String pictureBase64 = Base64.getEncoder().encodeToString(randomCatService.getBaseCatPic());
            model.addAttribute(CAT_PICTURE.attribute(), pictureBase64);
            baseCatPicture.setCatPic(pictureBase64);
        } else {
            model.addAttribute(CAT_PICTURE.attribute(), baseCatPicture.getCatPic());
        }
        return "cat";
    }

    @GetMapping("/premium-cat")
    public String premiumCat(Model model) {
        if (premiumCatPicture.getCatPic() == null) {
            String pictureBase64 = Base64.getEncoder().encodeToString(randomCatService.getPremiumCatPic());
            model.addAttribute(CAT_PREMIUM_PICTURE.attribute(), pictureBase64);
            premiumCatPicture.setCatPic(pictureBase64);
        } else {
            model.addAttribute(CAT_PREMIUM_PICTURE.attribute(), premiumCatPicture.getCatPic());
        }
        return "premium-cat";
    }
}
