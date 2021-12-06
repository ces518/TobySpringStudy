package me.june.mvc.converter;

import me.june.mvc.propertyeditor.Level;
import org.springframework.core.convert.converter.Converter;

/**
 * 프로퍼티 에디터와의 차이는 stateless 하기때문에 싱글톤 빈으로 등록해서 사용이 가능하다.
 * 컨버터의 경우 단방향 변환만 지원하기 때문에 반대의 경우도 구현해서 사용하면, 프로퍼티 에디터와 동일한 기능을 한다.
 */
public class LevelToStringConverter implements Converter<Level, String> {

    @Override
    public String convert(Level source) {
        return String.valueOf(source.intValue());
    }
}
