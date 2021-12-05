package me.june.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * DefaultAnnotationHandlerMapping (3.1 부터 Deprecated) -> RequestMappingHandlerMapping 으로 대체됨
 * @RequestMapping 애노테이션을 매핑 정보로 활용한다.
 * 클래스레벨 + 메소드 레벨의 정보를 취합해서 매핑정보로 활용할 수 있다.
 */
@Controller
@RequestMapping("/api/v1") // 타입 레벨에 정의, 타입 레벨에 정의 되었다면 이는 메소드레벨에 정의된 매핑 정보의 "공통 정보" 로써 사용된다.
public class SimpleController {

    // 메소드 레벨에 정의
    @RequestMapping("/users")
    public String users() {
        return "users";
    }
}
