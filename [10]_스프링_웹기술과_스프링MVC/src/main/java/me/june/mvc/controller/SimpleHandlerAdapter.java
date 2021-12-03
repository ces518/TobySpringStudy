package me.june.mvc.controller;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class SimpleHandlerAdapter implements HandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return (handler instanceof SimpleController);
    }

    @Override
    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Method method = ReflectionUtils.findMethod(handler.getClass(), "control", Map.class, Map.class);
        ViewName viewName = AnnotationUtils.getAnnotation(method, ViewName.class);
        RequiredParams requiredParams = AnnotationUtils.getAnnotation(method, RequiredParams.class);

        HashMap<String, String> params = new HashMap<>();
        for (String param : requiredParams.value()) {
            String value = request.getParameter(param);
            if (value == null) {
                throw new IllegalStateException();
            }
            params.put(param, value);
        }

        HashMap<String, Object> model = new HashMap<>();
        ((SimpleController)handler).control(params, model);

        return new ModelAndView(viewName.value(), model);
    }

    @Override
    public long getLastModified(HttpServletRequest request, Object handler) {
        return -1;
    }
}
