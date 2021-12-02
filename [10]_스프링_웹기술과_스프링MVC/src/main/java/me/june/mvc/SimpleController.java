package me.june.mvc;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 템플릿 메소드 패턴을 적용한 기반 컨트롤러
 * Controller 인터페이스를 구현한 어댑터를 사용한다 SimpleControllerHandlerAdapter
 * 추가적으로 LastModified 인터페이스도 지원한다. 이는 304 Not Modified 응답을 주기 위한 용도이다.
 */
abstract class SimpleController implements Controller {
    private String[] requiredParams;
    private String viewName;

    public void setRequiredParams(String[] requiredParams) {
        this.requiredParams = requiredParams;
    }

    public void setViewName(String viewName) {
        this.viewName = viewName;
    }

    @Override
    public final ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (viewName == null) {
            throw new IllegalStateException();
        }

        HashMap<String, String> params = new HashMap<>();
        for (String param : requiredParams) {
            String value = request.getParameter(param);
            if (value == null) {
                throw new IllegalStateException();
            }
            params.put(param, value);
        }

        HashMap<String, Object> model = new HashMap<>();
        control(params, model);

        return new ModelAndView(viewName, model);
    }

    public abstract void control(Map<String, String> params, Map<String, Object> model);
}
