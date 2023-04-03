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
    private IDaoDoctor daoDoctor;
    @Autowired
    private DbDaoRole daoRole;



    @GetMapping("/patient/")
    public String registration(Model model) {
        model.addAttribute("patient", new Patient());
        System.out.println("форма регистрации отправлена");
        return "registration/registratio-form";
    }

    @PostMapping("/patient/")
    @Transactional
   public String addPatient(Patient patient, Model model,  Authentication auth) {

        User userAdd = daoUser.save(patient.getPerson().getUser());
        if (userAdd != null) {
            userAdd.getRoles().add(daoRole.getRoleByAuthority("ROLE_PATIENT"));

            Person person = patient.getPerson();
            person.setUser(userAdd);
            person = daoPerson.save(person);

            patient.setPerson(person);
            daoPatient.save(patient);

            System.out.println(patient.getPerson());
            System.out.println(patient.getPerson().getUser());
            System.out.println(patient);

//            return "redirect:/";
        }

        return "redirect:/";
    }
//    @GetMapping("/doctor/")
//    public String registrationDoctor(Model model) {
//        model.addAttribute("doctor", new Doctor());
//        System.out.println("форма регистрации отправлена");
//      //  return "registration/registration-doctor-form";
//        return "doctor/doctor-form";
//    }
//    @PostMapping("/doctor/")
//    @Transactional
//    public String addDoctor(Doctor doctor, Model model,  Authentication auth) {
//
//        User docAdd = daoUser.save(doctor.getPerson().getUser());
//        docAdd.getRoles().add(daoRole.getRoleByAuthority("ROLE_DOCTOR"));
//        Person personAdmin = doctor.getPerson();
//        personAdmin.setUser(docAdd);
//        personAdmin = daoPerson.save(personAdmin);
//
//        doctor.setPerson(personAdmin);
//        daoDoctor.save(doctor);
//
//        System.out.println(doctor.getPerson());
//        System.out.println(doctor.getPerson().getUser());
//        System.out.println(doctor);
//
//        return "redirect:/";
//    }

//   @PostMapping("/patient/")
//    public String addPatient(Patient patient, Model model,  Authentication auth) {//@RequestParam String back,
//
////        if (bindingResult.hasErrors()) {
////            return "registration";
////        }
////        if (!patient.getPerson().getUser().getPassword().equals(userForm.getPasswordConfirm())){
////            model.addAttribute("passwordError", "Пароли не совпадают");
////            return "registration";
////        }
//        // User userAdd = daoUser.addUser(patient.getPerson().getUser());
//        User userEx = daoUser.getUserByLogin(patient.getPerson().getUser().getLogin());
//
//        if (userEx == null) {
////            Role role = new Role("ROLE_PATIENT");
////            Role roleAdd = daoRole.save(role);
////            Set<Role> roles = new HashSet<>();
////            roles.add(roleAdd);
//
//            User user = new User(patient.getPerson().getUser().getLogin(), patient.getPerson().getUser().getPassword());
//            System.out.println("user.getRoles() 1 " + user.getRoles());
//            //System.out.println("daoRole.getRoleByAuthority " + daoRole.getRoleByAuthority("ROLE_PATIENT").getId());
//            if (daoRole.getRoleByAuthority("ROLE_PATIENT") == null) {
//                Role role = new Role("ROLE_PATIENT");
//                Role roleAdd = daoRole.save(role);
//                System.out.println("null");
//            }// else {
//                System.out.println("user.getRoles() 2 " + user.getRoles());
//             //   user.getRoles().add(daoRole.getRoleByAuthority("ROLE_PATIENT"));
//
//                Role role = daoRole.getRoleByAuthority("ROLE_PATIENT");
//
//
//                user.setRoles(Collections.singleton(role));
//                System.out.println("user.getRoles()3 set" + user.getRoles());
//
//                User userAdd = daoUser.addUser(user);
//                System.out.println(" userAdd role= "+ userAdd.getRoles());
//
//                Person person = new Person(patient.getPerson().getFirstName(), patient.getPerson().getPatronymic(),
//                        patient.getPerson().getSurname(), patient.getPerson().getBirthdate(), userAdd);
//                Person personAdd = daoPerson.save(person);
//                Patient patientForAdd = new Patient(personAdd);
//                Patient patientAdd = daoPatient.save(patientForAdd);
//
//                System.out.println(" patientAdd role= "+ patientAdd.getPerson().getUser().getRoles());
//                model.addAttribute("goodMsg", "Пациент " + patientAdd + "зарегистрирован");
////                model.addAttribute("back", back);
//
//                model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));
//
//                printRole(auth);
//
//    //        return "redirect:/patient/";
//                return "registration/ok";
////            }
//
//        }
//
//        model.addAttribute("goodMsg", "Пользователь с таким адресом эл. почты уже существует");
//        return "registration/registratio-form";
//    }

    private void printRole(Authentication auth){
        System.out.println("regist " + auth.getAuthorities());
    }

}
