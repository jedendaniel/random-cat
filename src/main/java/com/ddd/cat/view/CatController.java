package com.ddd.cat.view;

import com.ddd.cat.domain.RandomCatService;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController()
public class CatController {
    private final RandomCatService randomCatService;
    public CatController(RandomCatService randomCatService) {
        this.randomCatService = randomCatService;
    }

    @GetMapping("/rest-cat")
    public String cat() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("pic", randomCatService.getBaseCatPic());
        return jsonObject.toString();
    }

    @GetMapping("/rest-premium-cat")
    public String premiumCat() {
        return randomCatService.getPremiumCatPic();
    }
}
