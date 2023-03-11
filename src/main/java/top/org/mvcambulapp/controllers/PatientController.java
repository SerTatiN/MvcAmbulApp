package top.org.mvcambulapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.org.mvcambulapp.model.dao.patient.IDaoPatient;
import top.org.mvcambulapp.model.dao.person.IDaoPerson;
import top.org.mvcambulapp.model.entity.Patient;
import top.org.mvcambulapp.model.entity.Person;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private IDaoPatient daoPatient;

    @Autowired
    private IDaoPerson daoPerson;

    @GetMapping("/")
    public String listAll(Model model){
        List<Patient> patientList = daoPatient.listAll();
        System.out.println("list " + patientList.size());
        model.addAttribute("patients",patientList);
        System.out.println("patients отправлены");
        return "patient/patient-list";
    }

    @GetMapping("/add/")
    public String getFormAddPatient(Model model){
        Patient patient = new Patient();

        System.out.println("форма  отправлена");
        model.addAttribute("patient", patient);
        return "patient/patient-form";
    }

    @PostMapping("/add/")
    public String addPatient(Patient patient, RedirectAttributes ra){
        System.out.println("форма  получена");
        System.out.println("person.getSurname() "+ patient.getPerson().getSurname());
        System.out.println("person.getBirthdate "+ patient.getPerson().getBirthdate());
        Person addPerson = daoPerson.save(patient.getPerson());

        patient.setPerson(addPerson);
        Patient patientAdd = daoPatient.save(patient);
        ra.addFlashAttribute("goodMsg", "Пациент " + patientAdd + "добавлен");
        return "redirect:/patient/";
    }

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
