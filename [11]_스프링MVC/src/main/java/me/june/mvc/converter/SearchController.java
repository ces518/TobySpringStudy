package me.june.mvc.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

@Controller
public class SearchController {

    @Autowired
    ConversionService conversionService;

    /**
     * 컨버전 서비스를 사용할때 InitBinder 를 활용한 수동 등록 방법
     */
    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.setConversionService(conversionService);
    }

}
