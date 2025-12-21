package com.ddd.cat.controller;

import com.ddd.cat.domain.RandomCatService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.node.ObjectNode;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("cat")
public class CatController {
    private final RandomCatService randomCatService;
    private final ObjectMapper objectMapper;
    public CatController(RandomCatService randomCatService, ObjectMapper objectMapper) {
        this.randomCatService = randomCatService;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public String cat() {
        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("pic", randomCatService.getBaseCatPic());
        return objectMapper.writeValueAsString(rootNode);
    }

    @GetMapping("/premium")
    public String premiumCat() {
        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("pic", randomCatService.getPremiumCatPic());
        return objectMapper.writeValueAsString(rootNode);
    }
}
