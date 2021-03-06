package me.june.mvc;

import static org.assertj.core.api.Assertions.assertThat;

import javax.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

public class SessionAttributesTest extends AbstractDispatcherServletTest {

    @Test
    void sessionAttr() throws Exception {
        setClasses(new Class[]{UserController.class});

        // GET 요청
        initRequest("/user/edit").addParameter("id", "1");
        runService();

        HttpSession session = request.getSession();
        assertThat(session.getAttribute("user")).isEqualTo(getModelAndView().getModel().get("user"));

        // POST 요청
        initRequest("/user/edit", "POST").addParameter("id", "1").addParameter("name", "Spring2");
        request.setSession(session);
        runService();

        assertThat(((User)getModelAndView().getModel().get("user")).getEmail()).isEqualTo("mail@spring.com");
        assertThat(session.getAttribute("user")).isNull();

    }

    @Controller
    @SessionAttributes("user")
    static class UserController {
        @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
        public User form(@RequestParam int id) {
            return new User(1, "Spring", "mail@spring.com");
        }

        @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
        public void submit(@ModelAttribute User user, SessionStatus sessionStatus) {
            sessionStatus.setComplete();
        }
    }


    static class User {
        int id;
        String name;
        String email;

        public User(int id, String name, String email) {
            this.id = id;
            this.name = name;
            this.email = email;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }

}
