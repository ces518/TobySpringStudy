package me.june.mvc.propertyeditor;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebBindingInitializer;

/**
 * 특정 컨트롤러가 아닌 컨트롤러 전역에 커스텀 프로퍼티 에디터 설정이 필요한 경우 사용한다.
 * 이를 빈으로 등록후 핸들러 어댑터에 프로퍼티로 DI 해주면 된다.
 */
public class MyWebBindingInitializer implements WebBindingInitializer {

    @Override
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Level.class, new LevelPropertyEditor());
    }
}
