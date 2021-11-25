package me.june.config;

import me.june.bean.AnnotatedHello;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Configuration 애노테이션과 @Bean 이 적용되면 스프링 컨테이너가 인식하는 빈 메타정보 + 빈 오브젝트 팩토리가 된다.
 */
@Configuration
public class AnnotatedHelloConfig {

    /**
     * @Bean 애노테이션이 적용된 메소드의 이름이 빈의 이름이 된다.
     */
    @Bean
    public AnnotatedHello annotatedHello() {
        return new AnnotatedHello();
    }

}
