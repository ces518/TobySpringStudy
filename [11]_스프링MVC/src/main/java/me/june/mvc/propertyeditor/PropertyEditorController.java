package me.june.mvc.propertyeditor;

import java.nio.charset.Charset;
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
