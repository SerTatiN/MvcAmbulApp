package top.org.mvcambulapp.controllers;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.org.mvcambulapp.model.dao.doctor.IDaoDoctor;
import top.org.mvcambulapp.model.dao.patient.IDaoPatient;
import top.org.mvcambulapp.model.dao.person.IDaoPerson;
import top.org.mvcambulapp.model.dao.role.DbDaoRole;
import top.org.mvcambulapp.model.dao.role.IDaoRole;
import top.org.mvcambulapp.model.dao.user.IDaoUser;
import top.org.mvcambulapp.model.entity.*;

import java.time.LocalDate;
import java.util.*;

@Controller
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    private IDaoUser daoUser;
    @Autowired
    private IDaoPerson daoPerson;
    @Autowired
    private IDaoPatient daoPatient;
    @Autowired
    private DbDaoRole daoRole;


    @GetMapping("/patient")
    public String registration(Model model) {
        model.addAttribute("patient", new Patient());
        System.out.println("форма регистрации отправлена");
        return "registration/registratio-form";
    }

    @PostMapping("/patient")
    @Transactional
   public String addPatient(Patient patient, Model model,  Authentication auth) {

        User userEx = daoUser.getUserByLogin(patient.getPerson().getUser().getLogin());
        if (userEx != null) {
            System.out.println("Пациент с такой электронной почтой существует");
            model.addAttribute("goodMsg", "Указанная электронная почта занята");
        } else {
            User userAdd = daoUser.save(patient.getPerson().getUser());
            userAdd.getRoles().add(daoRole.getRoleByAuthority("ROLE_PATIENT"));

            Person person = patient.getPerson();
            person.setUser(userAdd);
            person = daoPerson.save(person);

            patient.setPerson(person);
            Patient patientAdd = daoPatient.save(patient);
            model.addAttribute("goodMsg", "Вы успешно зарегистрировались");
        }
        return "registration/ok";
    }


}
