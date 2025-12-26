package com.ddd.cat.controller;

import com.ddd.cat.controller.model.Cat;
import com.ddd.cat.domain.RandomCatService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/api/v1/cat")
public class CatController {
    private final RandomCatService randomCatService;

    public CatController(RandomCatService randomCatService) {
        this.randomCatService = randomCatService;
    }

    @GetMapping
    public ResponseEntity<Cat> cat() {
        return new ResponseEntity<>(new Cat(randomCatService.getBaseCatPic()), HttpStatus.OK);
    }

    @GetMapping("/premium")
    public ResponseEntity<Cat> premiumCat() {
        return new ResponseEntity<>(new Cat(randomCatService.getPremiumCatPic()), HttpStatus.OK);
    }
}
