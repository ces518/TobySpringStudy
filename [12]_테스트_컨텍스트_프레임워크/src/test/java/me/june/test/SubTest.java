package me.june.test;

import org.springframework.test.context.ContextConfiguration;

/**
 * ContextConfiguration 는 슈퍼클래스 까지 함께 사용한다. 슈퍼 클래스에 정의된 파일 + 하위 클래스의 정의된 파일을 모두 포함함
 * 하위 클래스에서 이를 새롭게 정의하고 싶다면, inheritLocations=false 로 지정해 주어야한다.
 */
@ContextConfiguration(value = "classpath:subContext.xml", inheritLocations = false)
public class SubTest extends SuperTest {

}
