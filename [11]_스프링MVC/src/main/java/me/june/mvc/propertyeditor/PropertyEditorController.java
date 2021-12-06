package me.june.mvc.propertyeditor;

import java.nio.charset.Charset;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/property-editor")
public class PropertyEditorController {

    ObjectProvider<CodePropertyEditor> codePropertyEditor;

    /**
     * HandlerAdapter 는, @RequestParam, @ModelAttribute 와 같은 변수 바인딩 전 WebDataBinder 를 먼저 생성한다.
     * WebDataBinder 의 적용대상은 @RequestParam, @CookieValue, @RequestHeader, @PathVariable,
     * @ModelAttribute 이 적용된 파라미터이다. 커스텀 프로퍼티 에디터를 사용하려면 WebDataBinder 에 등록을 해주어야 한다. 이때 스프링이 제공하는
     * @InitBinder 라는 초기화 메소드를 사용해야 한다.
     *
     * @see org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter#getDataBinderFactory
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(Level.class, new LevelPropertyEditor());
        // 특정 프로퍼티 명에만 프로퍼티 에디터 적용도 가능하다.
        // 이런 경우 @ModelAttribute 로 받는 커맨드 오브젝트에만 가능하니 주의해야 한다.
        webDataBinder.registerCustomEditor(int.class, "age", new MinMaxPropertyEditor(0, 200));
        webDataBinder.registerCustomEditor(Code.class, new FakeCodePropertyEditor());

        // 바인딩 허용 필드 / 바인딩 금지 필드 설정 및 가져오기가 가능하다.
        // 그 외 요청 파라미터는 모두 막아준다.
        webDataBinder.setAllowedFields("name", "email", "tel");
        webDataBinder.getAllowedFields();
        webDataBinder.getDisallowedFields();

        // 필수 파라미터 체크시 활용이 가능하다.
        // HTTP 요청에 필수파라미터가 제외되었다면 바인딩 에러로 처리가 가능하다.
        webDataBinder.setRequiredFields("name");
        webDataBinder.getRequiredFields();

        // 필드마커 사용시 활용이 가능하다.
        // HTTP Form 특성상 체크박스는 체크를 풀었을때 해당 필드를 전송하지 않는다.
        // 때문에 SessionAttributes 등을 활용했을때 문제의 소지가 될 수 있다.
        // 이런경우 필드마커를 활용하는데, 이는 히든 필드의 활용이다.
        // 필드마커를 통해 해당필드가 있음을 알 수 있기 때문에 처리가 가능하다.
        // 이는 히든필드 앞에 붙는 접두어를 지정한다.
        // 기본값은 _ 언더바 이다.
        webDataBinder.setFieldMarkerPrefix("_");
        webDataBinder.getFieldMarkerPrefix();

        // 히든 필드를 활용해 체크박스에 대한 디폴드값 설정시 사용한다.
        // 기본 값은 ! 느낌표이다.
        webDataBinder.setFieldDefaultPrefix("!");
        webDataBinder.getFieldDefaultPrefix();
    }

    /**
     * ?charset=UTF-8 로 들어온다면, Charset.UTF-8 타입으로 받게된다.
     */
    @RequestMapping("/hello")
    public void hello(@RequestParam Charset charset, Model model) {

    }

    @RequestMapping("/custom")
    public void level(@RequestParam Level level) {

    }

    @RequestMapping("/add")
    public void add(@ModelAttribute Member member) {

    }

}
