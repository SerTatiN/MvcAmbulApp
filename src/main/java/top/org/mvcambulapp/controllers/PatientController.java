package top.org.mvcambulapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.org.mvcambulapp.model.dao.patient.IDaoPatient;
import top.org.mvcambulapp.model.dao.person.IDaoPerson;
import top.org.mvcambulapp.model.dao.role.DbDaoRole;
import top.org.mvcambulapp.model.dao.user.DbDaoUser;
import top.org.mvcambulapp.model.dao.user.IDaoUser;
import top.org.mvcambulapp.model.entity.Patient;
import top.org.mvcambulapp.model.entity.Person;
import top.org.mvcambulapp.model.entity.Role;
import top.org.mvcambulapp.model.entity.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private IDaoPatient daoPatient;

    @Autowired
    private IDaoPerson daoPerson;
    @Autowired
    private DbDaoUser daoUser;
    @Autowired
    private DbDaoRole daoRole;

    @GetMapping("/")
    public String listAll(Model model, Authentication auth){
        System.out.println("patient/: auth= " + auth.getAuthorities());
        System.out.println("patient/: user= " + daoUser.currentUser().getPerson().getFullName());
        List<Patient> patientList = daoPatient.listAll();
        System.out.println("list " + patientList.size());
        model.addAttribute("patients",patientList);
        model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));
        System.out.println("patients отправлены");
        return "patient/patient-list";
    }

//    @GetMapping("/add/")
//    public String getFormAddPatient(Model model,Authentication auth){
//        if (auth != null) {
//            System.out.println("getFormAddPatient" + auth.getAuthorities());
//            if (auth.getAuthorities().toString().contains("ROLE_ADMIN")) {
//                Patient patient = new Patient();
//                System.out.println("форма  отправлена");
//                model.addAttribute("patient", patient);
//                model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));
//                return "registration/registratio-form";//"patient/patient-form";
//            }
//            //            System.out.println("форма  отправлена");
////            model.addAttribute("patient", patient);
//        }
//        return null;
//    }
//
//    @PostMapping("/add/")
//    public String addPatient(Patient patient, Authentication auth,RedirectAttributes ra){
//        System.out.println("форма  получена");
//        System.out.println("person.getSurname() "+ patient.getPerson().getSurname());
//        System.out.println("person.getBirthdate "+ patient.getPerson().getBirthdate());
//
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
//            System.out.println("daoRole.getRoleByAuthority " + daoRole.getRoleByAuthority("ROLE_PATIENT").getId());
//            if (daoRole.getRoleByAuthority("ROLE_PATIENT") == null) {
//                Role role = new Role("ROLE_PATIENT");
//                Role roleAdd = daoRole.save(role);
//                System.out.println("null");
//            } else {
//                System.out.println("admin: user.getRoles() 2 " + user.getRoles());
//                //   user.getRoles().add(daoRole.getRoleByAuthority("ROLE_PATIENT"));
//
//                Role role = daoRole.getRoleByAuthority("ROLE_PATIENT");
//
//
//                // user.setRoles(user.getRoles().add(daoRole.getRoleByAuthority("ROLE_PATIENT")));
//
//
//                //            System.out.println("daoRole.getRoleByAuthority(\"ROLE_PATIENT\").getId() = " + daoRole.getRoleByAuthority("ROLE_PATIENT").getId());
////                for (Role role : user.getRoles()) {
////                   // if ((daoRole.getRoleByAuthority("ROLE_PATIENT")) != null) {
////                    System.out.println("daoRole.getRoleByAuthority(\"ROLE_PATIENT\").getId() = " + daoRole.getRoleByAuthority("ROLE_PATIENT").getId());
////                        role.setId(daoRole.getRoleByAuthority("ROLE_PATIENT").getId());
////                   // }
//                //               }
//                //         }
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
//                ra.addFlashAttribute("goodMsg", "Пациент " + patient.getPerson().getUser().getLogin() + " " +
//                        patient.getPerson().getUser().getRoles() + " зарегистрирован");
//                System.out.println(" patientAdd role= "+ patientAdd.getPerson().getUser().getRoles());
//                return "registration/ok";
//
//
//        Person addPerson = daoPerson.save(patient.getPerson());
//
//        patient.setPerson(addPerson);
//        Patient patientAdd = daoPatient.save(patient);
//        ra.addFlashAttribute("goodMsg", "Пациент " + patientAdd + "добавлен");
//        return "redirect:/patient/";
//    }

    @GetMapping("/update/{id}")
    public String getFormUpdatePatient(@PathVariable("id") Integer patientId, Model model){
        Optional<Patient> patient = daoPatient.getById(patientId);
        if (patient.isPresent()) {
            System.out.println("форма отправлена");
            model.addAttribute("patient", patient.get());
        }
        return "patient/patient-update";
    }

    @PostMapping("/update/")
    public String updatePerson(Patient patient, RedirectAttributes ra){
        System.out.println("форма получена");
        System.out.println("patient.toString()" + patient.toString());

//        без изменения персональных данных
//        Person updPerson = daoPerson.update(patient.getPerson());
//        patient.setPerson(updPerson);

        Patient patientUpd = daoPatient.update(patient);
        ra.addFlashAttribute("goodMsg", "Данные о пациенте " + patientUpd + " обновлены");
        return "redirect:/patient/";
    }

    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable ("id") Integer patientId, @RequestParam String back, Model model){
        Optional<Patient> patient = daoPatient.getById(patientId);
        if (patient.isPresent()) {
            model.addAttribute("patient", patient.get());
            model.addAttribute("back", back);
        }
        return "patient/patient-detail";
    }
    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable ("id") Integer patientId){
        daoPatient.delete(patientId);
        return "redirect:/patient/";
    }

}
