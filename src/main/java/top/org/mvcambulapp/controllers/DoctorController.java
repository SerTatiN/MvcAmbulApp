package top.org.mvcambulapp.controllers;


import com.fasterxml.jackson.core.JsonToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.org.mvcambulapp.model.dao.doctor.IDaoDoctor;
import top.org.mvcambulapp.model.dao.person.IDaoPerson;
import top.org.mvcambulapp.model.dao.role.DbDaoRole;
import top.org.mvcambulapp.model.dao.user.DbDaoUser;
import top.org.mvcambulapp.model.entity.Doctor;
import top.org.mvcambulapp.model.entity.Patient;
import top.org.mvcambulapp.model.entity.Person;
import top.org.mvcambulapp.model.entity.User;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private IDaoDoctor daoDoctor;

    @Autowired
    private IDaoPerson daoPerson;
    @Autowired
    private DbDaoUser daoUser;
    @Autowired
    private DbDaoRole daoRole;

    @GetMapping("/list")
    public String listAll(Model model,Authentication auth ){

        System.out.println("/ listAll");

        List<Doctor> doctors = daoDoctor.listAll();
        System.out.println("list " + doctors.size());
        model.addAttribute("doctors", doctors);
        if (auth !=null) {
            model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));
            model.addAttribute("isPatient", auth.getAuthorities().toString().contains("ROLE_PATIENT"));
        }
        System.out.println("model ");
        return "doctor/doctor-list";
    }
    @GetMapping("/add/")
    public String getFormAddDoc(Model model ,Authentication auth ){
        if (auth.getAuthorities().toString().contains("ROLE_ADMIN")) {
            System.out.println("/ addDoctor");
            Doctor doctor = new Doctor();
            model.addAttribute("doctor", doctor);
            model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));
            System.out.println("form  заполнена");

            return "doctor/doctor-form";
        }
        return "doctor/doctor-list"; //????

    }

    @PostMapping("/add/")
    public String addDoctor(Doctor doctor,  RedirectAttributes ra) {
       // Person addPerson = daoPerson.save(person);Person person,
        System.out.println("form получена");
       // System.out.println("person "+ person.toString());
//        System.out.println("doctor "+ doctor.getInfo());
//        System.out.println("doctor "+ doctor.getPerson().getSurname());
        System.out.println(doctor.getPerson().toString());
        System.out.println("doctor-user "+ doctor.getPerson().getUser());

        doctor.getPerson().getUser().getRoles().add(daoRole.getRoleByAuthority("ROLE_DOCTOR"));

        System.out.println("doctor-user "+ doctor.getPerson().getUser());

        User userAdd = daoUser.save(doctor.getPerson().getUser());
//        userAdd.getRoles().add(daoRole.getRoleByAuthority("ROLE_DOCTOR"));

        Person person = doctor.getPerson();
        person.setUser(userAdd);
        Person addPerson = daoPerson.save(person);

        doctor.setPerson(addPerson);
        Doctor addDoctor = daoDoctor.save(doctor);
        // 2. отправим сообщение о том что врач добавлен
        ra.addFlashAttribute("goodMsg", "Специалист " + addDoctor+ "добавлен");

        return "redirect:/doctor/list"; // 3. перенаправить ответ на вывод всех врачей (redirect)
    }

    @GetMapping("/update/{id}")
    public String getFormUpdateForm(@PathVariable("id") Integer doctorId, Model model, Authentication auth){
        if (auth.getAuthorities().toString().contains("ROLE_ADMIN")) {
            Optional<Doctor> doctor = daoDoctor.getById(doctorId);
            if (doctor.isPresent()) {
                System.out.println("id person {id} " + doctor.get().getPerson().getId());
                model.addAttribute("doctor", doctor.get());
                model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));
            }
            System.out.println("форма отправлена");
            return "doctor/doctor-update";
        }
        return "doctor/doctor-list";
    }

    @PostMapping("/update/")
    public String updateDoctor(Doctor doctor, RedirectAttributes ra){
        System.out.println("форма получена");
        System.out.println("id person " + doctor.getPerson().getId());

//        без изменения персональных данных
//        Person updPerson = daoPerson.update(doctor.getPerson());
//        doctor.setPerson(updPerson);

        Doctor updDoctor = daoDoctor.update(doctor);
        ra.addFlashAttribute("goodMsg", "Данные о специалисте " + updDoctor+ " обновлены");
    return "redirect:/doctor/list";
    }

    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable ("id") Integer doctorId, @RequestParam String back, Model model,Authentication auth){
        Optional<Doctor>  doctor = daoDoctor.getById(doctorId);
        if (doctor.isPresent()) {
            model.addAttribute("doctor", doctor.get());
//            model.addAttribute("auth",auth.getAuthorities().toString());

        }
        model.addAttribute("back", back);
        return "doctor/doctor-detail";
    }
    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable ("id") Integer doctorId,Model model){
        Optional<Doctor>  doctor = daoDoctor.getById(doctorId);
        if (doctor.isPresent()) {
            System.out.println("delete/{id}-" + doctor.get().getPerson().getUser().getRoles());

            daoUser.getUserById(doctor.get().getPerson().getUser().getId()).get().getRoles().remove(daoRole.getRoleByAuthority("ROLE_DOCTOR"));

            System.out.println("delete/{id}+" + doctor.get().getPerson().getUser().getRoles());

            daoDoctor.delete(doctorId);
            model.addAttribute("goodMsg", "Специалист был удален");

        }
        return "redirect:/doctor/list";
    }

//    @GetMapping("/accept/{id}")
//    public String getFormToAcceptPatient(@PathVariable ("id") Integer patientId,Model model){
//        System.out.println("getFormToAcceptPatient" + patientId);
//        PA
//        return "doctor/doctor-form-accept";
//    }




}
