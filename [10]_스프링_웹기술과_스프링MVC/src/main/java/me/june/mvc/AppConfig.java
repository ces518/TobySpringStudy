package me.june.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public HelloSpring helloSpring() {
        return new HelloSpring();
    }
}
