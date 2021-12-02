package me.june.mvc;

import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.SimpleServletHandlerAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class ServletControllerTest extends AbstractDispatcherServletTest {

    /**
     * SimpleServletHandlerAdapter 은 Servlet 클래스 코드를 그대로 유지하면서 스프링 빈으로 등록되어 컨트롤러로 활용된다.
     * 레거시를 스프링으로 점진적으로 포팅할 때 유용하다.
     * 단 이경우 init() / destroy() 같은 생명주기 메소드가 호출되지 않음을 유의해야 한다.
     */
    @Test
    void helloServletController() throws Exception {
        setClasses(new Class[]{SimpleServletHandlerAdapter.class, HelloServlet.class})
                .initRequest("/hello").addParameter("name", "Spring");
        assertThat(runService().getContentAsString()).isEqualTo("Hello Spring");
    }

    @Component("/hello")
    static class HelloServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String name = req.getParameter("name");
            resp.getWriter().print("Hello " + name);
        }
    }

}
