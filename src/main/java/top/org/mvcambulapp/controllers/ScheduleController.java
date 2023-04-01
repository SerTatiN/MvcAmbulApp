package top.org.mvcambulapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.org.mvcambulapp.model.dao.doctor.IDaoDoctor;
import top.org.mvcambulapp.model.dao.recordtodoctor.IDaoRecord;
import top.org.mvcambulapp.model.dao.schedule.IDaoSchedule;
import top.org.mvcambulapp.model.dao.user.DbDaoUser;
import top.org.mvcambulapp.model.entity.Doctor;
import top.org.mvcambulapp.model.entity.Person;
import top.org.mvcambulapp.model.entity.RecordToDoctor;
import top.org.mvcambulapp.model.entity.Schedule;

import javax.print.Doc;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@Controller
@RequestMapping("/schedule")
public class ScheduleController {
    @Autowired
    private IDaoSchedule daoSchedule;
    @Autowired
    private IDaoDoctor daoDoctor;

    @Autowired
    private IDaoRecord daoRecord;
    @Autowired
    private DbDaoUser daoUser;

    @GetMapping("/list")
    public String listAll(Model model, Authentication auth){
        System.out.println("schedule/listAll " + auth.getAuthorities().toString());
        List<Schedule> list = daoSchedule.listAll();
        System.out.println("list " + list.size());
        model.addAttribute("schedules", list);
        model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));
        model.addAttribute("isPatient", auth.getAuthorities().toString().contains("ROLE_PATIENT")); //
        return "schedule/schedule-list";
    }

    @GetMapping("/list2")
    public String listAllToRec(Model model, Authentication auth){
        System.out.println("schedule/listAllToRec " + auth.getAuthorities().toString());
        List<Doctor> doctors = daoDoctor.listAll();
//        List<Schedule> schedules = daoSchedule.listAll();

        System.out.println("doctors " + doctors.size());
//        System.out.println("schedules " + schedules.size());

//        model.addAttribute("schedules", schedules);
        model.addAttribute("doctors", doctors);
        model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));
        model.addAttribute("isPatient", auth.getAuthorities().toString().contains("ROLE_PATIENT")); //
        return "schedule/schedule-list-by-doctors";
    }

    @GetMapping("/listDoctor/{id}")
    public String listSchedulesDoctor(@PathVariable("id") Integer doctorId, Model model,@RequestParam String back, Authentication auth){
        System.out.println("listSchedulesDoctor " + auth.getAuthorities().toString());
        List<Schedule> schedules = daoSchedule.getScheduleByDoctorId(doctorId);

        System.out.println("schedules " + schedules.size());
//        System.out.println("schedules " + schedules.size());

//        model.addAttribute("schedules", schedules);
        model.addAttribute("doctor", daoDoctor.getById(doctorId).get());
        model.addAttribute("schedules", schedules);
        model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));
        model.addAttribute("isPatient", auth.getAuthorities().toString().contains("ROLE_PATIENT")); //
        model.addAttribute("back", back);
        return "schedule/schedule-list-by-doctor";
    }

    // расписание (дни, часы приема) авторизованного доктора
    @GetMapping("/doctor")
    public String listScheduleDoctor(Model model, Authentication auth){
        System.out.println("listScheduleDoctor() " + auth.getAuthorities().toString());
        if (auth.getAuthorities().toString().contains("ROLE_DOCTOR")) {
            List<Schedule> list = daoSchedule.getScheduleByDoctorId(daoUser.currentUser().getPerson().getDoctor().getId());
            System.out.println("list " + list.size());

            model.addAttribute("doctor", daoUser.currentUser().getPerson().getDoctor());
            model.addAttribute("schedules", list);
            //  model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));
            model.addAttribute("isDoctor", auth.getAuthorities().toString().contains("ROLE_DOCTOR")); //
            return "schedule/scedule-doctor-list";
        }
        return "schedule/schedule-list";
    }

    @GetMapping("/add/")
    public String getFormAddSchedule(Model model, Authentication auth ) {
        System.out.println("/ addSchedule");
        if (auth != null) {
            System.out.println("getFormAddDoctor " + auth.getAuthorities());
            if (auth.getAuthorities().toString().contains("ROLE_ADMIN")) {
                Schedule schedule = new Schedule();
                List<Doctor> doctors = daoDoctor.listAll();

                model.addAttribute("schedule", schedule);
                model.addAttribute("doctors", doctors); // для вставки в <optional>
                model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));

//                model.addAttribute("standardDate", new Date());  //<p th:text="${#dates.formatISO(standardDate)}"></p> 2023-03-19T09:32:25.682+04:00
//                model.addAttribute("localDateTime", LocalDateTime.now());//  <p th:text="${#temporals.formatISO(localDateTime)}"></p> 2023-03-19T09:32:25.682+0400
//                model.addAttribute("localDate", LocalDate.now());//<p th:text="${#temporals.formatISO(localDate)}"></p> 2023-03-19T00:00:00.000+0400
//                model.addAttribute("timestamp", Instant.now());//    <p th:text="${#temporals.formatISO(timestamp)}"></p> 2023-03-19T09:32:25.683+0400

                System.out.println("form  заполнена");
                return "schedule/schedule-form";
            }
        }
        return "index";
    }
    @PostMapping("/add/")
    public String addSchedule(Schedule schedule, RedirectAttributes ra){
        System.out.println("form  получена " + schedule);
        Schedule scheduleAdd = daoSchedule.save(schedule);
        System.out.println("scheduleAdd: " + scheduleAdd);

        createScheduleToRecord(scheduleAdd.getStartTime(),scheduleAdd.getEndTime(),15, scheduleAdd);

        ra.addFlashAttribute("goodMsg", "Расписание специалисту " +
                scheduleAdd.getDoctor().getPerson().getFullName() + " added");


        return "redirect:/schedule/list";
    }

    private void createScheduleToRecord(LocalTime start, LocalTime end, Integer spanAccept, Schedule schedule){
        LocalTime time = start;
        RecordToDoctor record = null;

        while (time.isBefore(end) || !time.equals(end)) {
            record = daoRecord.save(new RecordToDoctor(schedule,time));
            schedule.getRecordToAccept().add(record);
            time = time.plusMinutes(spanAccept);
        }
        System.out.println("проверка добавления в Set " + schedule.getRecordToAccept());
    }

    @GetMapping("/update/{id}")
    public String getFormUpdateSchedule(@PathVariable("id") Integer scheduleId, Model model,Authentication auth ){
        Optional<Schedule> schedule = daoSchedule.getById(scheduleId);
     //Doctor doctor = daoDoctor.getById();
       // List<Doctor> doctors = daoDoctor.ge();
        if (schedule.isPresent()) {
            System.out.println("форма schedule отправлена");
            model.addAttribute("schedule", schedule.get());
          //  model.addAttribute("doctor", doctor);
            model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));

        }
        return "schedule/schedule-update";
    }

    @PostMapping("/update/")
    public String updateSchedule(Schedule schedule, RedirectAttributes ra){
        System.out.println("форма schedule получена" +  schedule);

//        Doctor doctor = daoDoctor.getById(schedule.getDoctor().getId()).get();
//        // Проверяем изменение часов приема и создаем новые records для записи
//        Schedule scheduleDB = daoSchedule.getById(schedule.getId()).get();
//        if (schedule.getStartTime().isBefore(scheduleDB.getStartTime()) )
//
//
//        Schedule scheduleUpd = daoSchedule.update(schedule);
//        doctor.getScheduleSet().add(scheduleUpd,);


        // сформировать новый список времен. Что делать со старым?
        // Если не было записанных пациентов, то можно удалить старые записи и создать новые.
        // Если были записаны пациенты, то обзвонить и перезаписать вручную
        //createScheduleToRecord();scheduleUpd

        ra.addFlashAttribute("goodMsg", "Данные о расписании " + " обновлены");
        return "redirect:/schedule/list";
    }

    //Инфо по дате приема
    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable ("id") Integer scheduleId, @RequestParam String back, Model model){
        Schedule schedule = daoSchedule.getById(scheduleId).get();
       // List<RecordToDoctor> records = daoRecord.getRecordsToSchedule(daoSchedule.getById(scheduleId).get());
        System.out.println("getDetailschedule " + schedule );

            model.addAttribute("schedule", schedule);
            model.addAttribute("back", back);

        return "schedule/schedule-detail";
    }
    @GetMapping("/delete/{id}")
    public String deleteSchedule(@PathVariable ("id") Integer scheduleId){

        daoSchedule.delete(scheduleId);
        return "redirect:/schedule/list";
    }


}
