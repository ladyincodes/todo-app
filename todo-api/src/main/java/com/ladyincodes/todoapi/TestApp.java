package com.ladyincodes.todoapi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestApp {

    @GetMapping ("/")
    public String home() {
        return "Todo API is running ✅";
    }

    @GetMapping("/api/protected")
    public String securedEndpoint() {
        return "You are authenticated! 🎉";
    }
}
