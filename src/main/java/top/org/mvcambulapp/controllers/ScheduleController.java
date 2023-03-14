package top.org.mvcambulapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.org.mvcambulapp.model.dao.doctor.IDaoDoctor;
import top.org.mvcambulapp.model.dao.schedule.IDaoSchedule;
import top.org.mvcambulapp.model.entity.Doctor;
import top.org.mvcambulapp.model.entity.Person;
import top.org.mvcambulapp.model.entity.Schedule;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private IDaoSchedule daoSchedule;
    @Autowired
    private IDaoDoctor daoDoctor;

    @GetMapping("/")
    public String listAll(Model model, Authentication auth){
        System.out.println("schedule/listAll " + auth.getAuthorities().toString());
        List<Schedule> list = daoSchedule.listAll();
        System.out.println("list " + list.size());
        System.out.println("1 " + list.get(0).toString());
        model.addAttribute("schedules", list);
        model.addAttribute("isAdmin", auth);
        return "schedule/schedule-list";
    }

    @GetMapping("/add/")
    public String getFormAddSchedule(Model model) {
        System.out.println("/ add");
        //  Person personDoctor = new Person();
        Schedule schedule = new Schedule();
        List<Doctor> listDoc = daoDoctor.listAll();
        //List<Doctor> doctors = daoDoctor.listAll();
        //System.out.println("list " + doctors.size());
        // model.addAttribute("person", personDoctor);
        model.addAttribute("schedule", schedule);
        model.addAttribute("doctors", listDoc); // для вставки в <optional>
        System.out.println("form  заполнена");
        //    personDoctor.toString();
        //   doctor.getPerson().getSurname();
        return "schedule/schedule-form";
    }
    @PostMapping("/add/")
    public String addSchedule(Schedule schedule, RedirectAttributes ra){
        System.out.println("form  получена");
        System.out.println("расписание " + schedule.getId());
        System.out.println("getDate() " + schedule.getDate());
        System.out.println("getStartTime() " + schedule.getStartTime());
        System.out.println("getEndTime() " + schedule.getEndTime());
        Schedule scheduleAdd = daoSchedule.save(schedule);
        ra.addFlashAttribute("goodMsg", "Расписание специалисту " +
                scheduleAdd.getDoctor().getPerson().getSurname() + " added");

        return "redirect:/schedule/";
    }

    @GetMapping("/update/{id}")
    public String getFormUpdateSchedule(@PathVariable("id") Integer scheduleId, Model model){
        Optional<Schedule> schedule = daoSchedule.getById(scheduleId);
        List<Doctor> listDoc = daoDoctor.listAll();
        if (schedule.isPresent()) {
            System.out.println("форма schedule отправлена");
            model.addAttribute("schedule", schedule);
            model.addAttribute("doctors", listDoc);
        }
        return "schedule/schedule-update";
    }

    @PostMapping("/update/")
    public String updateSchedule(Schedule schedule, RedirectAttributes ra){
        System.out.println("форма schedule получена");
        System.out.println("расписание " + schedule.getId());
        System.out.println("getDate() " + schedule.getDate());
        System.out.println("getStartTime() " + schedule.getStartTime());
        System.out.println("getEndTime() " + schedule.getEndTime());

        System.out.println("id  doctor" + schedule.getDoctor().getId());

        Schedule updSchedule = daoSchedule.update(schedule);
        ra.addFlashAttribute("goodMsg", "Данные о расписании " + updSchedule + " обновлены");
        return "redirect:/schedule/";
    }
//    @GetMapping("/detail/{id}")
//    public String getDetail(@PathVariable ("id") Integer scheduleId, @RequestParam String back, Model model){
//        Optional<Schedule> schedule = daoSchedule.getById(scheduleId);
//        if (schedule.isPresent()) {
//            model.addAttribute("schedule", schedule.get());
//            model.addAttribute("back", back);
//        }
//        return "schedule/schedule-detail";
//    }
    @GetMapping("/delete/{id}")
    public String deleteSchedule(@PathVariable ("id") Integer scheduleId){
        daoSchedule.delete(scheduleId);
        return "redirect:/schedule/";
    }


}
