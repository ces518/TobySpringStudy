package me.june.mvc.converter;

import me.june.mvc.propertyeditor.Level;
import org.springframework.core.convert.converter.Converter;

public class StringToLevelConverter implements Converter<String, Level> {

    @Override
    public Level convert(String source) {
        return Level.valueOf(Integer.parseInt(source));
    }
}
