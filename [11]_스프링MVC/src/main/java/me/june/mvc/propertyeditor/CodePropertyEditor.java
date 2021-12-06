package me.june.mvc.propertyeditor;

import java.beans.PropertyEditorSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * 또 다른 방법은 프로토타입 프로퍼티 에디터의 활용이다.
 * 프로퍼티 에디터로 바인딩 해줄때 실제 완전한 오브젝트를 만들어주기 때문에 문제가 발생할 여지도 없다.
 */
@Component
@Scope("prototype")
public class CodePropertyEditor extends PropertyEditorSupport {

    @Autowired
    CodeService service;

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(service.getCode(Integer.parseInt(text)));
    }

    @Override
    public String getAsText() {
        return String.valueOf(((Code)getValue()).getId());
    }

    @Service
    static class CodeService {
        public Code getCode(int id) {
            return null;
        }
    }
}
