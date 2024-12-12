package ru.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HelloController {

    @GetMapping
    public String sayHello() {
        return "redirect:/login";
    }

    @GetMapping("/user")
    public String getUserPage() {
        return "user";
    }

    @GetMapping("/admin")
    public String getAdminPage() {
        return "admin/usersPage";
    }

    @GetMapping("/test")
    public String getTestPage() {
        return "test";
    }

}
