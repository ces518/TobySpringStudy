package me.june.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 * SessionAttributes 는 컨트롤러 메소드가 생성하는 모델 중 지정한 이름과 동일한 것이 있다면, 세션에 담아준다.
 * 또 하나는 @ModelAttribute 가 짖어된 파라미터가 있다면, 이를 세션에서 가져온다.
 * 이름에서 알 수 있듯이, 하나 이상의 모델을 세션에 저장할 수 있고 이를 활용할 수 있다.
 * 이는 클래스의 모든 메소드에 적용된다.
 */
@Controller
@SessionAttributes("user")
public class SessionAttributesController {


    @RequestMapping(value = "/user/edit", method = RequestMethod.GET)
    public String editForm(@RequestParam int id, Model model) {
        model.addAttribute("user", new User());
        return "user/edit";
    }

    @RequestMapping(value = "/user/edit", method = RequestMethod.POST)
    public String submit(@ModelAttribute User user, SessionStatus sessionStatus) {
        // ... 등록 처리
        sessionStatus.setComplete(); // 세션에서 제거
        return "";
    }
}
