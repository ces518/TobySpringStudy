package me.june.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan(
    basePackages = "me.june",
    excludeFilters = {
        @Filter(Configuration.class), // @Configuration 클래스를 필터 대상에서 제외
        @Filter(type = FilterType.ASSIGNABLE_TYPE, value = AppConfig.class) // FilterType.ASSIGNABLE_TYPE 지정해 특정 타입을 대상으로 할 수 있다.
                                                                            // AppConfig 클래스를 제외
    }
)
@Import(DataConfig.class) // 다른 설정 정보를 포함시킬때 사용
@ImportResource("parentContext.xml") // 특정 xml 설정을 포함시킬때 사용
public class AppConfig {

}
