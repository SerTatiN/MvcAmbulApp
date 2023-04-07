package top.org.mvcambulapp.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AmbulErrorController implements ErrorController {
    @RequestMapping("/error")
    public String handleError(HttpServletResponse response, Model model) {
        model.addAttribute("errorCode",response.getStatus());
        model.addAttribute("msg", "Ой! Что-то пошло не так...");
        return "layout/error";
    }
}
