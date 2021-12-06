package me.june.mvc.propertyeditor;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LevelPropertyEditorTest {

    @Test
    void textToLevel() {
        LevelPropertyEditor propertyEditor = new LevelPropertyEditor();
        propertyEditor.setAsText("3");
        assertThat(propertyEditor.getValue()).isEqualTo(Level.GOLD);


    }

    @Test
    void levelToText() {
        LevelPropertyEditor propertyEditor = new LevelPropertyEditor();
        propertyEditor.setValue(Level.BASIC);
        assertThat(propertyEditor.getAsText()).isEqualTo("1");
    }
}