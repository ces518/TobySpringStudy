package me.june.test;

import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration
/**
 * DirtiesContext 가 붙은 테스트는, 수행 이후 테스트 컨텍스트를 강제로 제거한다.
 * 내부 상태가 변경되거나 한 경우 공유해선 안되므로 이를 사용하는 것
 * 메소드/타입 레벨에 모두 적용이 가능하다.
 * 타입 레벨에 적용할 경우 해당 클래스의 모든 메소드 테스트가 끝난 후 제거된다.
 * classMode = ClassMode.AFTER_EACH_TEST_METHOD 로 지정한다면, 매 테스트 마다 컨텍스트를 제거하게 된다.
 */
@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class DirtyContextTest {

}
