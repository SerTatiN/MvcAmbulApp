package top.org.mvcambulapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.org.mvcambulapp.model.dao.doctor.IDaoDoctor;
import top.org.mvcambulapp.model.dao.listpatientcard.IDaoListPatientCard;
import top.org.mvcambulapp.model.dao.patient.IDaoPatient;
import top.org.mvcambulapp.model.dao.recordtodoctor.IDaoRecord;
import top.org.mvcambulapp.model.dao.user.DbDaoUser;
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
    @Autowired
    private DbDaoUser daoUser;
    @Autowired
    private IDaoRecord daoRecord;

    @GetMapping("/")
    public String listAll(Model model, Authentication auth){
        if (auth.getAuthorities().toString().contains("ROLE_ADMIN")||auth.getAuthorities().toString().contains("ROLE_DOCTOR") ) {
            List<ListPatientCard> patientCards = daoListPatientCard.listAll();
            model.addAttribute("patientCards", patientCards);
            model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));
            model.addAttribute("isDoctor", auth.getAuthorities().toString().contains("ROLE_DOCTOR"));

            System.out.println("patientCards отправлены");
            return "patient-card/patient-card-list";
        }
        return "index";
    }
//  Всю мед карту авторизованного пациента (?надо для Doctor и Patient)
    @GetMapping("/patient")
    public String listAllPatientCard(Model model, Authentication auth){
        System.out.println("patient/: user= " + daoUser.currentUser().getPerson().getFullName());
        if (auth.getAuthorities().toString().contains("ROLE_PATIENT")) {
            List<ListPatientCard> patientCards = daoListPatientCard.getPatientCardByPatientId(daoUser.currentUser().getPerson().getPatient().getId());
            System.out.println("list " + patientCards.size());
            model.addAttribute("patient", daoUser.currentUser().getPerson().getPatient());
            model.addAttribute("patientCards", patientCards);
           // model.addAttribute("isPatient", patientCards);
            System.out.println("patientCards отправлены");
            return "patient-card/patient-card";
        }
        return "index";
    }
//запись в мед карту делает доктор
    @GetMapping("/doctor-accept/{id}")
    public String getFormToAcceptPatient(Model model, @PathVariable("id") Integer recordId, Authentication auth ){
        System.out.println("getFormToAcceptPatient()"+ recordId);

        if (auth.getAuthorities().toString().contains("ROLE_DOCTOR") ) {
            ListPatientCard listCard = new ListPatientCard(daoRecord.getById(recordId).get());

           // System.out.println("форма  отправлена doctor-accept " + listCard.getId());
            model.addAttribute("record", daoRecord.getById(recordId).get());
            model.addAttribute("listCard", listCard);
            model.addAttribute("isDoctor", auth.getAuthorities().toString().contains("ROLE_DOCTOR"));
        }
        return "patient-card/patient-card-form";
    }

    @PostMapping("/doctor-accept/")
    public String addPatientCard(ListPatientCard listCard, RedirectAttributes ra){
        System.out.println("форма на запись получена " +listCard);
        System.out.println("listCard.getRecord() ?"+ listCard.getRecord());
        listCard.getRecord().setAccept(true);
        System.out.println("listCard.getRecord().setAccept(true) ?" + daoRecord.getById(listCard.getRecord().getId()));

        ListPatientCard patientCardAdd = daoListPatientCard.save(listCard);
        ra.addFlashAttribute("goodMsg", "Лист в карту пациента " +
                patientCardAdd.getRecord().getPatient().getPerson().getSurname() + " добавлен");
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
                patientCardUpd.getRecord().getPatient().getPerson().getSurname() + " обновлены");
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
