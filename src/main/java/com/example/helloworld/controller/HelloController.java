package com.example.helloworld.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/")
public class HelloController {
    @GetMapping("/greet/{stub}")
    private String greet(@PathVariable String stub) {
        return String.format("Hello, %s", stub);
    }
}
