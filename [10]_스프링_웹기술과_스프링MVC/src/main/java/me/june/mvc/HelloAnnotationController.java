package me.june.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * AnnotationMethodHandlerAdapter 를 사용하는 컨트롤러
 * 가장 많이 사용하고 유연한 방식
 * 메소드 단위로 핸들러를 지정할 수 있다.
 */
@Controller
public class HelloAnnotationController {

    @RequestMapping("/hello")
    public String hello(@RequestParam("name") String name, ModelMap map) {
        map.put("message", "Hello " + name);
        return "/WEB-INF/view/hello.jsp";
    }

}
