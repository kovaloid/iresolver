package com;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public final class HelloController {

    @RequestMapping("/")
    public String index() {
        return "Greeting from Maxim via Spring Boot!";
    }

    @RequestMapping("/hello")
    public String helloPage() {
        return "Hello everyone!";
    }
}
