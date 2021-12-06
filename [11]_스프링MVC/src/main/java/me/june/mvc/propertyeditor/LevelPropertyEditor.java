package me.june.mvc.propertyeditor;

import java.beans.PropertyEditorSupport;

/**
 * PropertyEditor 인터페이스를 직접 구현하는것 보단 Support 클래스를 사용해 필요한 부분만 Override 하는 방식으로 구현
 */
public class LevelPropertyEditor extends PropertyEditorSupport {

    @Override
    public String getAsText() {
        return String.valueOf(((Level) this.getValue()).intValue());
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        setValue(Level.valueOf(Integer.parseInt(text.trim())));
    }
}
