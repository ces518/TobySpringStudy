package me.june.test;

import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

/**
 * 테스트 클래스에서 사용가능한 DI 애노테이션은, @Autowired, @Resource, @Qualifier, @Inject, @Named, @Provider, @Required 등을 지원한다.
 * JPA 를 사용한다면 @PersistenceContext, @PersistenceUnit 도 가능하다.
 */
@ContextConfiguration
public class DITest {

    @Autowired
    Bean bean;

    @Resource
    Bean myBean;


    static class Bean {

    }
}
