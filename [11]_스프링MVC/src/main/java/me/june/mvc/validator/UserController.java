package me.june.mvc.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {

    @Autowired
    UserValidator userValidator;

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(userValidator);
    }

    /**
     * 컨트롤러 코드에서 직접 사용
     */
    @RequestMapping("/users/add")
    public void add(@ModelAttribute User user, BindingResult result) {
        userValidator.validate(user, result);
        if (result.hasErrors()) {
            // ...
        }
    }

    /**
     * initBinder 를 통해 벨리데이터를 등록해두고, @Valid, @Validated 를 활용한 검증 방법
     */
    @RequestMapping("/users/add/v2")
    public void addv2(@ModelAttribute @Validated User user, BindingResult result) {
        if (result.hasErrors()) {
            // ...
        }
    }
}
