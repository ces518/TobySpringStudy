package me.june.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

@Controller
@RequestMapping("/return")
public class ReturnController {

    /**
     * ModelAndView 는 가장 대표적인 반환 타입이지만, 잘 사용되지 않는다. 더 편리한 방법을 많이 제공하기 때문이다.
     * (최종적으로 ModelAndView 를 만든다.)
     *
     * @see org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter#getModelAndView
     */
    @RequestMapping("/model-and-view")
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView("hello").addObject("name", "hello");
    }

    /**
     * 메소드 리턴타입이 String 이라면 반환값은 뷰 이름으로 사용된다.
     * 모델 정보는 ModelMap 같은 파라미터를 가져와 사용하는 방법을 활용해야 한다.
     * 주로 사용되는 방식
     */
    @RequestMapping("/string")
    public String string() {
        return "hello";
    }

    /**
     * 메소드 반환타입을 void 로 설정가능
     * RequestToViewNameResolver 를 활용해 자동생성되는 뷰 이름을 사용한다.
     * URL 과 뷰 이름을 일관되게 할 경우 사용할만 하지만 권장하지는 않음 ㅜ
     */
    @RequestMapping("/void")
    public void voidReturn() {

    }

    /**
     * 뷰 이름 결정은 RequestToViewNameResolver 를 사용하고, 뷰에 전달할 모델 객체가 하나뿐이라면 해당 오브젝트를 직접 반환하는 방식
     * 하지만 권장되진 않는다.
     */
    @RequestMapping("/model")
    public User model() {
        return new User();
    }

    /**
     * 뷰 오브젝트를 직접 반환할 수도 있다.
     */
    @RequestMapping("/view")
    public View view() {
        return null;
    }

    /**
     * RequestBody 와 비슷한 방식으로 동작한다.
     * 뷰 결과를 메세지 컨버터를 사용해 응답 본문으로 사용하게 된다.
     */
    @RequestMapping("/response-body")
    @ResponseBody
    public String responseBody() {
        return "<html><body>hello</body></html>";
    }

}
