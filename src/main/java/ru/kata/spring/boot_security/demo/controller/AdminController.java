package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    @RequestMapping("/login")
    public String formlogin() {
        return ("/login");
    }

    @RequestMapping("/admin")
    public String index() {
        return "admin";
    }
}
