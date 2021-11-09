package config;

import mail.DummyMailSender;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;
import service.UserService;
import service.UserServiceTest.TestUserServiceImpl;

/**
 * JavaConfig 방식을 사용하면, Annotation-Driven 메타 태그 (빈 후처리기 자동 등록) 가 필요없다. 스프링 컨테이너가 자동으로 등록해주기 때문..
 */
@Configuration
public class TestApplicationContext {

    @Bean
    public UserService testUserService() {
        return new TestUserServiceImpl();
    }

    @Bean
    public MailSender mailSender() {
        return new DummyMailSender();
    }
}
