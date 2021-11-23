package me.june;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.support.StaticApplicationContext;

public class AppTest {

    @Test
    void registerSingleton() throws Exception {
        StaticApplicationContext applicationContext = new StaticApplicationContext();
        // registerSingleton 메소드로 등록할 경우 GenericBeanDefinition 으로 등록됨
        applicationContext.registerSingleton("hello1", Hello.class);

        Hello hello1 = applicationContext.getBean("hello1", Hello.class);
        assertThat(hello1, is(notNullValue()));
    }

    @Test
    void rootBeanDefinition() throws Exception {
        StaticApplicationContext applicationContext = new StaticApplicationContext();
        // RootBeanDefinition 은 가장 기본적인 구현체
        BeanDefinition helloDef = new RootBeanDefinition(Hello.class);
        helloDef.getPropertyValues().addPropertyValue("name", "Spring");
        applicationContext.registerBeanDefinition("hello2", helloDef);

        Hello hello2 = applicationContext.getBean("hello2", Hello.class);
        assertThat(hello2, is(notNullValue()));
        assertThat(hello2.sayHello(), is("Hello Spring"));

        // BeanDefinition 정보를 가져오는것도 가능함
        assertThat(applicationContext.getBeanFactory().getBeanDefinitionCount(), is(1));
    }
}
