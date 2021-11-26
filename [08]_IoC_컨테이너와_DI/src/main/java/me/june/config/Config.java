package me.june.config;

import me.june.Hello;
import me.june.Printer;
import me.june.StringPrinter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${database.username}")
    private String name;

    @Bean
    public Hello hello() {
        return new Hello();
    }

    @Bean
    public Printer printer() {
        return new StringPrinter();
    }
}
