package me.june;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import org.junit.jupiter.api.Test;
import org.springframework.context.support.StaticApplicationContext;

public class AppTest {

    @Test
    void registerSingleton() throws Exception {
        StaticApplicationContext applicationContext = new StaticApplicationContext();
        applicationContext.registerSingleton("hello1", Hello.class);

        Hello hello1 = applicationContext.getBean("hello1", Hello.class);
        assertThat(hello1, is(notNullValue()));
    }
}
