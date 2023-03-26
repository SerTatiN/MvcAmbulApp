package top.org.mvcambulapp.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping("/")
    public String index(Model model, Authentication auth){
        if (auth!=null) {
            System.out.println("***** " + auth);
            model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));
            model.addAttribute("isAuth", auth.getAuthorities());
            model.addAttribute("isDoctor", auth.getAuthorities().toString().contains("ROLE_DOCTOR"));
            model.addAttribute("isPatient", auth.getAuthorities().toString().contains("ROLE_PATIENT"));
        }
        return "index";

    }
}
