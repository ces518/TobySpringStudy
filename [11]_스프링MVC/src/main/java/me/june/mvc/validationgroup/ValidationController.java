package me.june.mvc.validationgroup;

import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ValidationController {

    /**
     * UserGroup 으로 벨리데이션 수행
     */
    @RequestMapping("/validation")
    public String save(@ModelAttribute("bean") @Validated(User.class) Bean bean) {
        return null;
    }

}
