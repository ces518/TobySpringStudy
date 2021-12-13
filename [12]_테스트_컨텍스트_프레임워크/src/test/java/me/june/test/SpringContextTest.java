package me.june.test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * Spring 3.1 버전 부터 자바 설정을 활용한 테스트 컨텍스트 설정도 가능해졌다.
 * 아무런 값을 지정하지 않은 경우에는, 이전과 동일하게 -context.xml 파일을 먼저 찾는다.
 * 디폴트 설정 클래스를 찾는 방법은 테스트 클래스의 내부에 정의된 스태틱 멤버 클래스 중 @Configuration 이 붙은 것들을 모두 디폴트 설정 정보로 활용한다.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = AppConfig.class)
@ActiveProfiles("dev") // 테스트용 활성 프로파일 지정을 지원한다.
public class SpringContextTest {

    @Configuration
    static class TestConfig {

    }
}
