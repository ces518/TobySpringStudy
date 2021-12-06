package me.june.mvc.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @EnableWebMvc -> Spring 3.1 에 추가됨
 * 커스터 마이징 하기위해서 WebMvcConfigurer 인터페이스를 구현.. (JDK 8)
 * WebMvcConfigurerAdapter (JDK7_Deprecated)
 */
@Configuration
@EnableWebMvc
public class AppConfig implements WebMvcConfigurer {

}
