package me.june.mvc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.handler.SimpleUrlHandlerMapping;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
        handlerMapping.setInterceptors(handlerInterceptor()); //
        return handlerMapping;
    }

    /**
     * 핸들러 매핑의 역할은 단순 컨트롤러만 찾는게 아니라, 핸들러 실행 체인을 돌려준다.
     * 실행체인은 핸들러 인터셉터와 컨트롤러의 목록 구성이다.
     * HandlerInterceptor 는 컨트롤러 요청 전후처리가 가능하게 한다.
     * preHandle / postHandle / afterCompletion 이 있다.
     * 필터와 유사한 구조로 동작한다.
     */
    @Bean
    public HandlerInterceptor handlerInterceptor() {
        return new HandlerInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                return HandlerInterceptor.super.preHandle(request, response, handler);
            }
        };
    }

    /**
     * 주로 JSP 를 뷰로 사용할때 사용한다.
     * 기본적으로 등록되어 있다.
     */
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/view");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }
}
