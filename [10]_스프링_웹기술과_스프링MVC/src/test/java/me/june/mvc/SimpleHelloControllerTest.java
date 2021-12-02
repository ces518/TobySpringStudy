package me.june.mvc;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class SimpleHelloControllerTest extends AbstractDispatcherServletTest {

    @Test
    void helloController() throws Exception {
        ModelAndView mav = setRelativeLocations("spring-servlet.xml")
                .setClasses(new Class[]{HelloSpring.class})
                .initRequest("/hello", RequestMethod.GET)
                .addParameter("name", "Spring")
                .runService()
                .getModelAndView();

        assertThat(mav.getViewName()).isEqualTo("/WEB-INF/view/hello.jsp");
        assertThat(mav.getModel().get("message")).isEqualTo("Hello Spring");
    }

}
