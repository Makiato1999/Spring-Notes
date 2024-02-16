package com.easybytes.easyschool.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    // routing
    @RequestMapping("/home")
    public String displayHomePage() {
        return "home.html";
    }
}
