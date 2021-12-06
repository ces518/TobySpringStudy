package me.june.mvc.propertyeditor;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.web.bind.WebDataBinder;

public class WebDataBinderTest {

    @Test
    void customPropertyEditorTest() {
        WebDataBinder dataBinder = new WebDataBinder(null);
        dataBinder.registerCustomEditor(Level.class, new LevelPropertyEditor());
        assertThat(dataBinder.convertIfNecessary("1", Level.class)).isEqualTo(Level.BASIC);
    }
}
