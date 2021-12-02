package me.june.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public HelloSpring helloSpring() {
        return new HelloSpring();
    }

    /**
     * BeanNameUrlHandlerMapping 에 의해 매핑되도록 등록한다.
     * 빈의 이름을 기반으로 Handler 를 찾는다.
     */
    @Bean(name = "/hello")
    public HelloController helloController() {
        return new HelloController();
    }

}
