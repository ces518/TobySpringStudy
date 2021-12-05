package me.june.mvc;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/user")
public interface Intf {

    @RequestMapping("/list")
    String list();
}
