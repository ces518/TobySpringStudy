package me.june.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * Controller 인터페이스를 처리하는 HandlerAdapter 는 SimpleControllerHandlerAdapter 이다.
 */
public class HelloController implements Controller {

    @Autowired HelloSpring helloSpring;

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        String name = request.getParameter("name");
        String message = helloSpring.sayHello(name);

        HashMap<String, Object> model = new HashMap<>();
        model.put("message", message);

        /**
         * InternalResourceView 는 RequestDispatcher 의 forward/include 를 이용하는 뷰다.
         * 주로 JSP 뷰를 만들때 활용한다.
         */
        InternalResourceView view = new InternalResourceView("/WEB-INF/view/hello.jsp");

        /**
         * RedirectView 는 리다이렉트 처리를 해주는 뷰이다.
         * 컨트롤러에서 이를 직접 만들기보단 redirect: 키워드를 활용해 뷰네임을 반환해주는 것이 좋다.
         */
        RedirectView redirectView = new RedirectView("/wow");

        return new ModelAndView(view, model);
    }
}
