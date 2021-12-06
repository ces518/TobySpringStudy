package me.june.mvc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;
import org.springframework.web.bind.support.SessionStatus;

/**
 * 기본적으로 사용가능한 파라미터의 종류는 다음과 같다.
 * - HttpServletRequest/Response
 * - HttpSession
 * - WebRequest/NativeWebRequest
 * - Locale
 * - InputStream/Reader
 * - OutputStream/Writer
 */
@Controller
@RequestMapping("/parameter")
public class ParameterController {

    /**
     * 패스 파라미터의 경우 @PathVariable 를 이용해 참조해 올 수 있다.
     * 만약 패스파라미터의 타입과 메소드 인자의 타입이 일치하지 않는다면 400 에러 응답을 내려주게 된다.
     */
    @RequestMapping("/{id}")
    public String pathVariable(@PathVariable String id) {
        return id;
    }

    /**
     * 요청 파라미터를 메소드 파라미터로 받는 애노테이션
     * 하나 이상의 파라미터에 적용이 가능하며, 기본형 타입의 경우 생략이 가능하다.
     * required 옵션을 false 로 지정하지 않으면 해당 파라미터는 반드시 존재해야한다.
     * 모든 요청 파라미터를 받고 싶다면 Map<String, String> 타입으로 선언하면 된다.
     */
    @RequestMapping("/request-param")
    public String requestParam(
            @RequestParam int id,
            @RequestParam(required = false) String name,
            @RequestParam Map<String, String> params // 모든 요청 파라미터를 받는 방법
    ) {
        return "";
    }

    /**
     * auth 라는 이름의 쿠키 값을 메소드 파라미터 auth 에 넣어준다.
     * RequestParam 과 마찬가지로 지정한 쿠키 값이 반드시 존재해야 한다.
     * required=false 지정시 예외가 발생하지 않는다.
     */
    @RequestMapping("/cookie-value")
    public String cookieValue(@CookieValue(value = "auth", required = false, defaultValue = "NONE") String auth) {
        return auth;
    }

    /**
     * 별도 애노테이션이 적용되어 있지 않다면, Map, Model, ModelMap 타입 파라미터는 모두 모델정보를 담을 때 사용할 오브젝트가 전달된다.
     */
    @RequestMapping("/model")
    public String model(ModelMap modelMap) {
        modelMap.addAttribute("user", "user");
        return "user";
    }

    /**
     * 쿼리파라미터나, 폼데이터를 하나의 오브젝트로 받는 방법 = ModelAttribute
     * 커멘드 패턴의 커멘드를 따 커멘트 오브젝트라고도 한다.
     * 생략 가능
     * ModelAttribute 는 기본적으로 컨트롤러에서 뷰로 전달할 모델 객체에 자동으로 담아주는데, 기본적으로 파라미터 타입명을 활용한다.
     */
    @RequestMapping("/search")
    public String search(@ModelAttribute("search") UserSearch userSearch) {
        return "";
    }

    /**
     * @RequestParam 의 경우 선언한 타입과 일치하지 않으면 TypeMisMatchException 이 발생하고, 이를 400 에러로 스프링이 변환해준다.
     * @ModelAttribute 의 경우 해당 타입에 바로 바인딩하는 것이 아닌, "검증 과정" 이 추가되어 있다.
     * 때문에 타입이 일치하지 않아도 작업은 계속 진행된다. 타입 변환중 발생한 예외가 BindingException 타입의 오브젝트에 담겨 전달된다.
     * @RequestParam 과 @ModelAttribute 의 가장 큰 차이는, "별도의 검증과정이 있다는 점" 이다.
     * Errors 또는 BindingResult 타입 파라미터를 같이 사용하게 되면 검증 과정중 발생한 예외를 컨트롤러에서 핸들링 할 수 있다.
     * 주의할 점은 @ModelAttribute 를 적용한 파라미터 바로 뒤에 위치해야 한다.
     */
    @RequestMapping("/binding")
    public String bindingResult(@ModelAttribute UserSearch userSearch, BindingResult result) {
        return "";
    }

    /**
     * 모델을 세션에 저장해 두었다가 다음 페이지에서 재활용 가능하게 해주는 기능이 있다.
     * 이를 사용하다가 더 이상 사용할 일이 없을때 SessionStatus 를 활용해 제거한다.
     */
    @RequestMapping("/session-status")
    public String sessionStatus(SessionStatus status) {
        status.isComplete();
        status.setComplete();
        return "";
    }

    /**
     * @RequestBody 는 요청 본문을 바인딩할 때 사용한다.
     * 이 애노테이션이 적용되었다면, HttpMessageConverter 를 사용한다.
     * 미디어 타입과 파라미터 타입을 확인하고, MessageConverter 중에 이를 선출해 처리한다.
     * JSON/XML/STRING 등등 다양한 컨버터들이 존재한다.
     */
    @RequestMapping("/request-body")
    public String requestBody(@RequestBody UserRequest request) {
        return "";
    }

    /**
     * @Value 는 빈의 값 주입 뿐 아니라 컨트롤러 메소드 파라미터로도 사용이 가능하다.
     * 사용방법은 동일하다.
     */
    @RequestMapping("/value")
    public String value(@Value("#{systemProperties['os.name']}") String osName) {
        return "";
    }
}
