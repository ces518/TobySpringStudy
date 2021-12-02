package me.june.mvc;

import me.june.mvc.serlvet.ConfigurableDispatcherServlet;
import me.june.mvc.serlvet.SimpleGetServlet;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.mock.web.MockServletConfig;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import java.io.IOException;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class MockServletTest {

    @Test
    void test() throws ServletException, IOException {
        // given
        /**
         * 서블릿에 전달할 HttpServletRequest 타입의 요청정보를 구성하게 해준다.
         * 세션 정보가필요하다면, MockHttpSession 을 사용하면 된다.
         *
         * 드물게 ServletConfig/ServletContext 를 별도로 만들어 테스트해야 하는 경우가 있다.
         * MockServletContext 를 생성해 스프링 루트 컨텍스트를 넣어주는 등 활용이 가능하다.
         */
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/hello");
        request.addParameter("name", "Spring");

        MockHttpSession session = new MockHttpSession();
        session.putValue("cart", "cart");
        request.setSession(session);

        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        SimpleGetServlet servlet = new SimpleGetServlet();
        servlet.service(request, response);

        // then
        assertThat(response.getContentAsString()).isEqualTo("<html><body>Hello Spring</body></html>");
    }

    /**
     * ConfigurableDispatchServlet 디스패처 서블릿을 테스트환경에서 보다 쉽게 테스트가능하게 지원해주는 확장 클래스이다.
     * XML 설정 파일위치나 빈 클래스 를 직접 등록할 수도 있기 때문에 테스트마다 독립적인 설정 / 빈 클래스사용이 가능하다.
     * 유용한 기능중 하나는 ModelAndView 를 저장해 두었다가 테스트에서 참조할 수도 있다.
     */
    @Test
    void dispatcherServlet() throws ServletException, IOException {
        // given
        ConfigurableDispatcherServlet servlet = new ConfigurableDispatcherServlet();
        servlet.setRelativeLocations(getClass(), "spring-servlet.xml"); // 설정 파일 추가
        servlet.setClasses(HelloSpring.class); // 빈 클래스 추가
        servlet.init(new MockServletConfig("spring"));

        // MockRequest/Response 생성
        MockHttpServletRequest request = new MockHttpServletRequest("GET", "/hello");
        request.addParameter("name", "Spring");

        MockHttpServletResponse response = new MockHttpServletResponse();

        // when
        // HelloController 로 요청을 보냄
        servlet.service(request, response);

        // then
        ModelAndView mav = servlet.getModelAndView();
        assertThat(mav.getViewName()).isEqualTo("/WEB-INF/view/hello.jsp");
        assertThat(mav.getModel().get("message")).isEqualTo("Hello Spring");
    }
}
