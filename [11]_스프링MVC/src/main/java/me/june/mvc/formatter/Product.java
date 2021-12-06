package me.june.mvc.formatter;

import java.math.BigDecimal;
import java.util.Calendar;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

/**
 * 스프링부트의 경우 FormattingConversionService 로 WebConversionService 가 기본적으로 등록된다.
 */
public class Product {

    /**
     * 특정 숫자를 일정한 포맷에 맞게 변환해주는 포매터
     * NumberFormatAnnotationFormatterFactory 가 등록되어있어야한다.
     */
    @NumberFormat(pattern = "$###,##0.00")
    BigDecimal price;

    /**
     * 현재 지역정보에 따른 풀 포맷
     */
//    @DateTimeFormat(style = "F-")
    /**
     * 커스텀 포맷
     */
    @DateTimeFormat(pattern = "yyyy/MM/dd")
    Calendar birthDay;
}
