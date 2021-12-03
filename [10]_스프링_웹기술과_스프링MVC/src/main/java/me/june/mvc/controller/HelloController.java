package me.june.mvc.controller;

import java.util.Map;

public class HelloController implements SimpleController {

    /**
     * 인터페이스 기반이기 때문에 필수 프로퍼티등을 정의하기 애매하다.
     * 때문에 해당정보를 애노테이션을 활용해서 가져온다.
     */
    @ViewName("/WEB-INF/view/hello.jsp")
    @RequiredParams({"name"})
    public void control(Map<String, String> params, Map<String, Object> model) {
        model.put("message", "Hello " + params.get("name"));
    }
}
