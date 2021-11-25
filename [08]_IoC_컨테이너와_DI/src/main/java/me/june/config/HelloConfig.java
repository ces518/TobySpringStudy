package me.june.config;

import me.june.Hello;
import me.june.Printer;
import me.june.StringPrinter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HelloConfig {

    /**
     * DI 를 위해 printer() 를 반복적으로 호출하더라도, 싱글톤이 적용된다.
     * -> Proxy 가 적용됨..!!
     */
    @Bean
    public Hello hello() {
        Hello hello = new Hello();
        hello.setName("Spring");
        hello.setPrinter(printer());
        return hello;
    }

    @Bean
    public Hello hello2() {
        Hello hello = new Hello();
        hello.setName("Spring2");
        hello.setPrinter(printer());
        return hello;
    }

    @Bean
    public Printer printer() {
        return new StringPrinter();
    }
}
