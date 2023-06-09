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
import top.org.mvcambulapp.model.dao.role.IDaoRole;
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
    private IDaoRole daoRole;

    @GetMapping("/list")
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

    @GetMapping("/add/")
    public String getFormAddPatient(Model model,Authentication auth){
        if (auth != null) {
            System.out.println("getFormAddPatient " + auth.getAuthorities());
            if (auth.getAuthorities().toString().contains("ROLE_ADMIN")) {
                Patient patient = new Patient();
                System.out.println("форма  отправлена");

                model.addAttribute("patient", patient);
                model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));

                return "patient/patient-form";  //"registration/registratio-form";
            }
            System.out.println("getFormAddPatient, форма не отправлена" +auth.getAuthorities() );
//            model.addAttribute("patient", patient);
        }
        return "index";
    }

    @PostMapping("/add/")
    public String addPatient(Patient patient, RedirectAttributes ra){
        System.out.println("форма  получена");
        System.out.println("person.getSurname() "+ patient.getPerson().getSurname());
        System.out.println("person.getBirthdate "+ patient.getPerson().getBirthdate());

        User userEx = daoUser.getUserByLogin(patient.getPerson().getUser().getLogin());
        if (userEx != null) {
            System.out.println("Пациент с такой электронной почтой существует");
            ra.addFlashAttribute("goodMsg", "Пациент " + patient.getPerson().getFullName() +
                    " " + "с такой электронной почтой существует");
        }
        else {
            User userAdd = daoUser.save(patient.getPerson().getUser());
            userAdd.getRoles().add(daoRole.getRoleByAuthority("ROLE_PATIENT"));

            Person person = patient.getPerson();
            person.setUser(userAdd);
            person = daoPerson.save(person);

            patient.setPerson(person);
            Patient patientAdd = daoPatient.save(patient);

            ra.addFlashAttribute("goodMsg", "Пациент " + patient.getPerson().getFullName() +
                    " " + "добавлен");
            System.out.println(" patientAdd role= "+ patientAdd.getPerson().getUser().getRoles());
//            return "redirect:/list";
        }
        return "redirect:/patient/list";
    }

    @GetMapping("/update/{id}")
    public String getFormUpdatePatient(@PathVariable("id") Integer patientId, Model model, Authentication auth){
        Optional<Patient> patient = daoPatient.getById(patientId);
        if (patient.isPresent()) {
            System.out.println("форма отправлена");
            model.addAttribute("patient", patient.get());
            model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));

        }
        return "patient/patient-update";
    }

    @PostMapping("/update/")
    public String updatePerson(Patient patient, RedirectAttributes ra){
        System.out.println("форма получена");
        System.out.println("patient " + patient);
        System.out.println("patient.getPerson() " + patient.getPerson());

        Optional<Patient> patientUpd = daoPatient.getById(patient.getId());
        if (patientUpd.isPresent()) {
            System.out.println("1" +patientUpd.get().getPerson().getUser());
            patient.getPerson().setUser(patientUpd.get().getPerson().getUser());
            System.out.println("2" + patient.getPerson().getUser());

            Person personU = daoPerson.update(patient.getPerson());

            patientUpd.get().setPerson(personU);

            patient = daoPatient.update(patientUpd.get());


            ra.addFlashAttribute("goodMsg", "Пациент " + patient.getPerson().getFullName() +
                    " " + "обновлен");
            //  System.out.println(" patientAdd role= " + patient.getPerson().getUser().getRoles());
            return "redirect:/patient/list";
        }

//        Patient patientUpd = daoPatient.update(patient);
//        ra.addFlashAttribute("goodMsg", "Данные о пациенте " + patientUpd + " обновлены");
        return "/";
    }

    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable ("id") Integer patientId, @RequestParam String back, Model model,
                            Authentication auth){
        Optional<Patient> patient = daoPatient.getById(patientId);
        if (patient.isPresent()) {
            model.addAttribute("patient", patient.get());
            model.addAttribute("back", back);
            model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));
        }
        return "patient/patient-detail";
    }
    @GetMapping("/delete/{id}") // нельзя удалять пациентов
    public String deleteDoctor(@PathVariable ("id") Integer patientId){
        daoPatient.delete(patientId);

        return "redirect:/patient/list";
    }

}
