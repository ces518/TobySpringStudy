package me.june.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;

import java.util.Properties;

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

    /**
     * SimpleUrlHandlerMapping 매핑정보를 한곳에 모아볼 수 있다.
     */
    @Bean
    public SimpleUrlHandlerMapping simpleUrlHandlerMapping() {
        SimpleUrlHandlerMapping handlerMapping = new SimpleUrlHandlerMapping();
        Properties properties = new Properties();
        properties.put("/hello", "helloController");
        handlerMapping.setMappings(properties);
        return handlerMapping;
    }
}
