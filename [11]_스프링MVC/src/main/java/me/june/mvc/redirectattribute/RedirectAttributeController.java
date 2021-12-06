package me.june.mvc.redirectattribute;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RedirectAttributeController {

    /**
     * Model 객체의 단점은 리다이렉트시 Model 에 담긴 모든 정보가 URI 파라미터로 담기게 된다.
     * 컬렉션 객체를 담고있거나 한다면 URI 는 상당히 지저분해지고 불필요한 정보까지 노출하게 된다.
     * 이를 위해 수동으로 제거하는등 작업이 필요한데, 이를 위해 RedirectAttributes 를 제공해준다.
     * 이를 사용하면 모델 대신 RedirectAttributes 에 담긴 정보를 활용한다.
     */
    @RequestMapping("/redirect-attribute")
    public void redirect(RedirectAttributes ra) {
        ra.addAttribute("status", "status");
        ra.addFlashAttribute("flash", "flash");
    }

}
