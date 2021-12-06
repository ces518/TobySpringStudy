package me.june.mvc.propertyeditor;

import java.beans.PropertyEditorSupport;

public class MinMaxPropertyEditor extends PropertyEditorSupport {

    int min;
    int max;

    public MinMaxPropertyEditor(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public String getAsText() {
        return String.valueOf(getValue());
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        int value = Integer.parseInt(text);
        if (value < min) {
            value = min;
        } else if (value > max) {
            value = max;
        }
        setValue(value);
    }
}
