package me.june.mvc.propertyeditor;

import java.nio.charset.Charset;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PropertyEditorController {

    /**
     * ?charset=UTF-8 로 들어온다면, Charset.UTF-8 타입으로 받게된다.
     */
    @RequestMapping("/property-editor")
    public void hello(@RequestParam Charset charset, Model model) {

    }

}
