package com.ddd.cat.view;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/hello")
    public void hello(Model model) {
        model.addAttribute("appName", "Random Cat");
    }
}
