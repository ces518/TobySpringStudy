package me.june.mvc.propertyeditor;

import java.beans.PropertyEditorSupport;

/**
 * 완전하지 않은 오브젝트 (ID 만 가짐) 를 모조 오브젝트 Fake Object 라고 한다.
 * 이런 오브젝트를 만들어 주는 프로퍼티 에디터를 모조 오브젝트 프로퍼티 에디터 라고한다.
 */
public class FakeCodePropertyEditor extends PropertyEditorSupport {

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        Code code = new FakeCode();
        code.setId(Integer.parseInt(text));
        setValue(code);
    }

    @Override
    public String getAsText() {
        return String.valueOf(((Code)getValue()).getId());
    }
}
