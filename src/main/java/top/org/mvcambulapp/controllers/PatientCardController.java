package top.org.mvcambulapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.org.mvcambulapp.model.dao.doctor.IDaoDoctor;
import top.org.mvcambulapp.model.dao.listpatientcard.IDaoListPatientCard;
import top.org.mvcambulapp.model.dao.patient.IDaoPatient;
import top.org.mvcambulapp.model.entity.ListPatientCard;
import top.org.mvcambulapp.model.entity.Patient;
import top.org.mvcambulapp.model.entity.Person;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/patient-card")
public class PatientCardController {
    @Autowired
    private IDaoListPatientCard daoListPatientCard;

    @Autowired
    private IDaoPatient daoPatient;

    @Autowired
    private IDaoDoctor daoDoctor;

    @GetMapping("/")
    public String listAll(Model model){
        List<ListPatientCard> patientCards = daoListPatientCard.listAll();
        System.out.println("list " + patientCards.size());
        model.addAttribute("patientCards", patientCards);
        System.out.println("patientCards отправлены");
        return "patient-card/patient-card-list";
    }

    @GetMapping("/add/")
    public String getFormAddPatientCard(Model model){
        ListPatientCard listCard = new ListPatientCard();

        System.out.println("форма  отправлена");
        model.addAttribute("listCard", listCard);
        return "patient-card/patient-card-form";
    }

    @PostMapping("/add/")
    public String addPatientCard(ListPatientCard listCard, RedirectAttributes ra){
        System.out.println("форма на запись получена");
        System.out.println(listCard.toString());
     //  System.out.println(" "+ patient.getPerson().getBirthdate());
     //   Person addPerson = daoPerson.save(patient.getPerson());

     //   patient.setPerson(addPerson);
        ListPatientCard patientCardAdd = daoListPatientCard.save(listCard);
        ra.addFlashAttribute("goodMsg", "Лист в карту пациента " +
                patientCardAdd.getPatient().getPerson().getSurname() + "добавлен");
        return "redirect:/patient-card/";
    }

    @GetMapping("/update/{id}")
    public String getFormUpdatePatient(@PathVariable("id") Integer patientCardId, Model model){
        Optional<ListPatientCard> patientCard = daoListPatientCard.getById(patientCardId);
        if (patientCard.isPresent()) {
            System.out.println("форма отправлена");
            model.addAttribute("patientCard", patientCard.get());
        }
        return "patient-card/patient-card-update";
    }

    @PostMapping("/update/")
    public String updatePerson(ListPatientCard patientCard, RedirectAttributes ra){
        System.out.println("форма получена");
        System.out.println("patient.toString()" + patientCard.toString());

//        без изменения персональных данных
//        Person updPerson = daoPerson.update(patient.getPerson());
//        patient.setPerson(updPerson);

        ListPatientCard patientCardUpd = daoListPatientCard.update(patientCard);
        ra.addFlashAttribute("goodMsg", "Данные в карте пациента " +
                patientCardUpd.getPatient().getPerson().getSurname() + " обновлены");
        return "redirect:/patient-card/";
    }

    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable ("id") Integer patientCardId, @RequestParam String back, Model model){
        Optional<ListPatientCard> patientCard = daoListPatientCard.getById(patientCardId);
        if (patientCard.isPresent()) {
            model.addAttribute("patientCard", patientCard.get());
            model.addAttribute("back", back);
        }
        return "patient-card/patient-card-detail";
    }
    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable ("id") Integer patientCardId){
        daoListPatientCard.delete(patientCardId);
        return "redirect:/patient-card/";
    }


}
