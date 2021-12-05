package me.june.mvc;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.List;

/**
 * Generic 을 활용한 컨트롤러..
 * 반복적인 CRUD 로직에 대한 처리가 가능함
 */
public abstract class GenericController<T, K, S> {

    S service;

    @RequestMapping("/add")
    public void add(T entity) {
        // ..
    }

    @RequestMapping("/update")
    public void update(T entity) {
        // ..
    }

    @RequestMapping("/view")
    public T view(K id) {
        // ..
        return null;
    }

    @RequestMapping("/delete")
    public void delete(K id) {
        // ..
    }

    @RequestMapping("/list")
    public List<T> list() {
        // ...
        return Collections.emptyList();
    }
}
