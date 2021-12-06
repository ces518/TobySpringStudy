package me.june.mvc.propertyeditor;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.Charset;
import org.junit.jupiter.api.Test;
import org.springframework.beans.propertyeditors.CharsetEditor;

class PropertyEditorControllerTest {

    @Test
    void charsetEditor() {
        CharsetEditor charsetEditor = new CharsetEditor();
        charsetEditor.setAsText("UTF-8");
        assertThat(charsetEditor.getValue()).isInstanceOf(Charset.class);
        assertThat(charsetEditor.getValue()).isEqualTo(Charset.forName("UTF-8"));
    }

}