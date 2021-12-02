package me.june.mvc;

import java.util.Map;

public class HelloSimpleController extends SimpleController {

    public HelloSimpleController() {
        setRequiredParams(new String[]{"name"});
        setViewName("/WEB-INF/view/hello.jsp");
    }

    @Override
    public void control(Map<String, String> params, Map<String, Object> model) {
        model.put("message", "Hello" + params.get("name"));
    }
}
