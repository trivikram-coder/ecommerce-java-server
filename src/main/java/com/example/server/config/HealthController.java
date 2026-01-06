package com.example.server.config;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HealthController {

    @GetMapping("/")
    public String home() {
        return "index"; // maps to index.html
    }
}
