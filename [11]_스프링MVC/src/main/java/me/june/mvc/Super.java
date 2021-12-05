package me.june.mvc;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
public class Super {

    @RequestMapping("/list")
    public String list() {
        return "";
    }
}
