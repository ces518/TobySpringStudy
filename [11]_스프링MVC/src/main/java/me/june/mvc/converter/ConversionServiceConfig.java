package me.june.mvc.converter;

import java.util.Arrays;
import org.assertj.core.util.Sets;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;

@Configuration
public class ConversionServiceConfig {

    /**
     * GenericConversionService 를 생성하는 팩토리빈을 활용한다.
     * 컨버전 서비스는 일반적으로 GenericConversionService 를 빈으로 등록해 사용한다.
     */
    @Bean
    public ConversionServiceFactoryBean conversionService() {
        ConversionServiceFactoryBean factoryBean = new ConversionServiceFactoryBean();
        factoryBean.setConverters(
            Sets.newHashSet(Arrays.asList(new LevelToStringConverter(), new StringToLevelConverter()))
        );
        return factoryBean;
    }
}
