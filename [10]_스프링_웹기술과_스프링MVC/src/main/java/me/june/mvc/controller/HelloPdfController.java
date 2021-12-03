package me.june.mvc.controller;

import me.june.mvc.view.HelloPdfView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

public class HelloPdfController implements Controller {

    @Autowired
    HelloPdfView pdfView;

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HashMap<String, Object> model = new HashMap<>();
        model.put("message", "Hello " + request.getParameter("name"));
        return new ModelAndView(pdfView, model);
    }
}
