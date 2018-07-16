package com.jresolver.editor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public final class HelloController {

    @RequestMapping("/hello1")
    public String index() {
        return "Greeting from Maxim via Spring Boot!";
    }

    @RequestMapping("/hello")
    public String helloPage() {
        return "Hello everyone!";
    }
}
