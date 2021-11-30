package me.june.jpa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalEntityManagerFactoryBean;

@Configuration
public class JpaConfig {

    /**
     * JavaSE 기동 방식응 이용한 엔티티매니저 팩토리 생성
     * PersistentProvider 자동 감지 기능을 이용해 프로바이더를 찾는다.
     * META-INF/persistence.xml 에 담긴 정보를 활용함
     * 스프링에 적용하기엔 제약사항이 많다. (DataSource 활용이 불가능함)
     * 스프링이 제공하는 바이트코드 위빙 기법도 사용할 수 없음 스프링에서 제어가 불가능하다.
     * 비추하는 방법..
     */
    @Bean
    public LocalEntityManagerFactoryBean localEntityManager() {
        return new LocalEntityManagerFactoryBean();
    }
}
