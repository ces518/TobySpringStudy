package me.june.mvc;

import me.june.mvc.serlvet.SimpleGetServlet;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;

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
}
