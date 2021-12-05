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

    /**
     * 파일확장자 패턴으로 매칭도 가능하다. 하지만 스프링 부트의 경우 기본적으로 false 로 지정되어 있다.
     * 보안상의 이슈..
     *
     * https://stackoverflow.com/questions/9688065/spring-mvc-application-filtering-html-in-url-is-this-a-security-issue
     * https://stackoverflow.com/questions/30610607/how-to-change-spring-request-mapping-to-disallow-url-pattern-with-suffix
     * https://stackoverflow.com/questions/30307678/why-does-requestmapping-spring-annotation-in-controller-capture-more-that-i-wan
     * https://stackoverflow.com/questions/22845672/requestmapping-in-spring-with-weird-patterns
     *
     * https://github.com/spring-projects/spring-framework/issues/23915
     * https://github.com/spring-projects/spring-framework/issues/24179
     *
     */
    @RequestMapping("/main.*")
    public String main() {
        return "main";
    }

    /**
     * value 속성에 명시하며, value 애트리뷰트 명은 생략가능
     */
    @RequestMapping(value = "/hello")
    public String hello() {
        return "hello";
    }

    /**
     * {userId} 와 같은 형태로 패스 파라미터로 선언이 가능한데, 이를 PathVariable 이라고 한다.
     */
    @RequestMapping("/user/{userId}")
    public String userId() {
        return "userId";
    }

    /**
     * 배열로 하나 이상의 URL 패턴 지정도 가능하다.
     */
    @RequestMapping({"/wow", "/fantastic"})
    public String wow() {
        return "wow";
    }
}
