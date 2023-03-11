package top.org.mvcambulapp.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.org.mvcambulapp.model.dao.doctor.IDaoDoctor;
import top.org.mvcambulapp.model.dao.person.IDaoPerson;
import top.org.mvcambulapp.model.entity.Doctor;
import top.org.mvcambulapp.model.entity.Person;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/doctor")
public class DoctorController {

    @Autowired
    private IDaoDoctor daoDoctor;

    @Autowired
    private IDaoPerson daoPerson;

    @GetMapping("/")
    public String listAll(Model model){
        System.out.println("/ listAll");
        List<Doctor> doctors = daoDoctor.listAll();
        System.out.println("list " + doctors.size());
        model.addAttribute("doctors", doctors);
        System.out.println("model ");
        return "doctor/doctor-list";
    }
    @GetMapping("/add/")
    public String getFormAddDoc(Model model){
        System.out.println("/ add");
      //  Person personDoctor = new Person();
        Doctor doctor = new Doctor();
        //List<Doctor> doctors = daoDoctor.listAll();
        //System.out.println("list " + doctors.size());
       // model.addAttribute("person", personDoctor);
        model.addAttribute("doctor", doctor);
        System.out.println("form  заполнена");
    //    personDoctor.toString();
     //   doctor.getPerson().getSurname();
        return "doctor/doctor-form";
    }

    @PostMapping("/add/")
    public String addNewDoc(Doctor doctor,  RedirectAttributes ra) {
       // Person addPerson = daoPerson.save(person);Person person,
        System.out.println("form получена");
       // System.out.println("person "+ person.toString());
        System.out.println("doctor "+ doctor.getInfo());
        System.out.println("doctor "+ doctor.getPerson().getSurname());
        System.out.println(doctor.getPerson().toString());

        Person addPerson = daoPerson.save(doctor.getPerson());

        doctor.setPerson(addPerson);
        Doctor addDoctor = daoDoctor.save(doctor);
        // 2. отправим сообщение о том что врач добавлен
        ra.addFlashAttribute("goodMsg", "Специалист " + addDoctor+ "добавлен");

        return "redirect:/doctor/"; // 3. перенаправить ответ на вывод всех врачей (redirect)
    }

    @GetMapping("/update/{id}")
    public String getFormUpdateForm(@PathVariable("id") Integer doctorId, Model model){
        Optional<Doctor> doctor = daoDoctor.getById(doctorId);
        if (doctor.isPresent()){
           System.out.println("id person {id} " + doctor.get().getPerson().getId());
           model.addAttribute("doctor",doctor.get());
        }
        System.out.println("форма отправлена");
        return "doctor/doctor-update";
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
    return "redirect:/doctor/";
    }

    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable ("id") Integer doctorId, @RequestParam String back, Model model){
        Optional<Doctor>  doctor = daoDoctor.getById(doctorId);
        if (doctor.isPresent()) {
            model.addAttribute("doctor", doctor.get());
            model.addAttribute("back", back);
        }
        return "doctor/doctor-detail";
    }
    @GetMapping("/delete/{id}")
    public String deleteDoctor(@PathVariable ("id") Integer doctorId){
        daoDoctor.delete(doctorId);
        return "redirect:/doctor/";
    }



}
