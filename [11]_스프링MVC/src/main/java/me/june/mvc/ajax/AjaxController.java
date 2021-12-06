package me.june.mvc.ajax;

import me.june.mvc.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @RestController 를 사용하면 모든 메소드에 @ResponseBody 가 적용된것과 동일하다.
 */
@Controller
@RequestMapping("/ajax")
public class AjaxController {

    @RequestMapping(value = "checkloginId", method = RequestMethod.GET)
    @ResponseBody
    public Result checkLogin(@PathVariable String loginId) {
        // 로그인 체크로직 수행...
        return new Result();
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public User register(@RequestBody User user) {
        // 검증 및 등록
        return user;
    }
}
