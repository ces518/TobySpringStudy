package me.june.mvc.uricomponentbuilder;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Controller
public class UriComponentController {

    /**
     * 코드에서 URI 생성 또는 가공할 경우 UriComponentsBuilder 사용을 권장한다.
     * Spring 3.1 부터 핸들러메소드 인자로 UriComponentsBuilder 를 받을 수 있다.
     * 이는 현재 URI 기준으로 생성된 빌더를 얻어올 수 있다.
     */
    @RequestMapping("/uricomponent")
    public void uri(UriComponentsBuilder builder) {
        UriComponents uc = UriComponentsBuilder.fromUriString(
            "http://www.myshop.com/useres/{user}/order/{order}"
        ).build();
        String uriString = uc.expand(1, 1).encode().toUriString();


        UriComponentsBuilder.newInstance()
            .scheme("http")
            .host("www.myshop.com")
            .path("/users/{user}/order/{order}")
            .build();

    }

}
