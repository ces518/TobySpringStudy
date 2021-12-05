package me.june.mvc;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user/*")
public class UserController {

    @RequestMapping
    public String add() {
        return "";
    }

    @RequestMapping
    public String edit() {
        return "";
    }
}
