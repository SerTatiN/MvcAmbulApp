package top.org.mvcambulapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.org.mvcambulapp.model.dao.patient.IDaoPatient;
import top.org.mvcambulapp.model.dao.person.IDaoPerson;
import top.org.mvcambulapp.model.dao.role.DbDaoRole;
import top.org.mvcambulapp.model.dao.role.IDaoRole;
import top.org.mvcambulapp.model.dao.user.IDaoUser;
import top.org.mvcambulapp.model.entity.Patient;
import top.org.mvcambulapp.model.entity.Person;
import top.org.mvcambulapp.model.entity.Role;
import top.org.mvcambulapp.model.entity.User;

import java.util.HashSet;
import java.util.Set;

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



    @GetMapping("/")
    public String registration(Model model) {
        model.addAttribute("patient", new Patient());
        System.out.println("форма регистрации отправлена");
        return "registration/registratio-form";
    }

    @PostMapping("/")
    public String addPatient(Patient patient, Model model) {

//        if (bindingResult.hasErrors()) {
//            return "registration";
//        }
//        if (!patient.getPerson().getUser().getPassword().equals(userForm.getPasswordConfirm())){
//            model.addAttribute("passwordError", "Пароли не совпадают");
//            return "registration";
//        }
        // User userAdd = daoUser.addUser(patient.getPerson().getUser());
        User userEx = daoUser.getUserByLogin(patient.getPerson().getUser().getLogin());

        if (userEx == null) {
//            Role role = new Role("ROLE_PATIENT");
//            Role roleAdd = daoRole.save(role);
//            Set<Role> roles = new HashSet<>();
//            roles.add(roleAdd);

            User user = new User(patient.getPerson().getUser().getLogin(), patient.getPerson().getUser().getPassword());
            System.out.println("user.getRoles() 1 " + user.getRoles());
            System.out.println("daoRole.getRoleByAuthority " + daoRole.getRoleByAuthority("ROLE_PATIENT").getId());
            if (daoRole.getRoleByAuthority("ROLE_PATIENT") == null) {
                Role role = new Role("ROLE_PATIENT");
                Role roleAdd = daoRole.save(role);
                System.out.println("null");
            } else {
                System.out.println("user.getRoles() 2 " + user.getRoles());
                user.getRoles().add(daoRole.getRoleByAuthority("ROLE_PATIENT"));

                //            System.out.println("daoRole.getRoleByAuthority(\"ROLE_PATIENT\").getId() = " + daoRole.getRoleByAuthority("ROLE_PATIENT").getId());
//                for (Role role : user.getRoles()) {
//                   // if ((daoRole.getRoleByAuthority("ROLE_PATIENT")) != null) {
//                    System.out.println("daoRole.getRoleByAuthority(\"ROLE_PATIENT\").getId() = " + daoRole.getRoleByAuthority("ROLE_PATIENT").getId());
//                        role.setId(daoRole.getRoleByAuthority("ROLE_PATIENT").getId());
//                   // }
                //               }
                //         }
                System.out.println("user.getRoles()3" + user.getRoles());

                User userAdd = daoUser.addUser(user);
                System.out.println(" userAdd role= "+ userAdd.getRoles());

                Person person = new Person(patient.getPerson().getFirstName(), patient.getPerson().getPatronymic(),
                        patient.getPerson().getSurname(), patient.getPerson().getBirthdate(), userAdd);
                Person personAdd = daoPerson.save(person);
                Patient patientForAdd = new Patient(personAdd);
                Patient patientAdd = daoPatient.save(patientForAdd);
                model.addAttribute("goodMsg", "Пациент " + patient.getPerson().getUser().getLogin() + " " +
                        patient.getPerson().getUser().getRoles() + " зарегистрирован");
                System.out.println(" patientAdd role= "+ patientAdd.getPerson().getUser().getRoles());
                return "registration/ok";
            }

        }
        model.addAttribute("usernameError", "Пользователь с таким именем уже существует");
        return "/";
    }
}
