package com.kobie.qaautomation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/test-page")
public class TestController {

    @GetMapping
    public String form() {
        return "test-page";
    }


}
