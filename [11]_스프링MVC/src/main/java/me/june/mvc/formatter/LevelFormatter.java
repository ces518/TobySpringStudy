package me.june.mvc.formatter;

import java.text.ParseException;
import java.util.Locale;
import me.june.mvc.propertyeditor.Level;
import org.springframework.format.Formatter;

/**
 * Formatter 는 웹에 특화된 버전이다.
 * Locale 정보 등을 참조해서 지역화 i18n 등에 활용할 수도 있다.
 */
public class LevelFormatter implements Formatter<Level> {

    @Override
    public Level parse(String text, Locale locale) throws ParseException {
        return Level.valueOf(Integer.parseInt(text));
    }

    @Override
    public String print(Level level, Locale locale) {
        return String.valueOf(level.intValue());
    }
}
