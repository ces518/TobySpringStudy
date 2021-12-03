package me.june.mvc;

import me.june.mvc.controller.HelloController;
import me.june.mvc.controller.SimpleHandlerAdapter;
import org.junit.jupiter.api.Test;

public class HandlerAdapterTest extends AbstractDispatcherServletTest {

    /**
     * 예제가 이상한데 ?... 핸들러 매핑이 찾지못해 404임..
     */
    @Test
    void simpleHandleAdapter() throws Exception {
        setClasses(new Class[]{SimpleHandlerAdapter.class, HelloController.class})
                .initRequest("/hello").addParameter("name", "Spring").runService()
                .assertViewName("/WEB-INF/view/hello.jsp")
                .assertModel("message", "Hello Spring");
    }
}
