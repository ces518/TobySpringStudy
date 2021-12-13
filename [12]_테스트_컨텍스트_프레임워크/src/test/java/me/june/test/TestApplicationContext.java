package me.june.test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
// 설정파일 명 생략시, {테스트 클래스명}-context.xml 이 자동 지정됨
@ContextConfiguration({"classpath:test-applicationContext.xml", "classpath:subContext.xml"})
public class TestApplicationContext {

}
