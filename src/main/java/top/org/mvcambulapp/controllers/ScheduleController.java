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
import top.org.mvcambulapp.model.entity.Doctor;
import top.org.mvcambulapp.model.entity.Person;
import top.org.mvcambulapp.model.entity.RecordToDoctor;
import top.org.mvcambulapp.model.entity.Schedule;

import javax.print.Doc;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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

    @GetMapping("/")
    public String listAll(Model model, Authentication auth){
        System.out.println("schedule/listAll " + auth.getAuthorities().toString());
        List<Schedule> list = daoSchedule.listAll();
        System.out.println("list " + list.size());
        model.addAttribute("schedules", list);
        model.addAttribute("isAdmin", auth);
        model.addAttribute("isPatient", auth); //
        return "schedule/schedule-list";
    }

    @GetMapping("/add/")
    public String getFormAddSchedule(Model model, Authentication auth) {
        System.out.println("/ addSchedule");
        if (auth != null) {
            System.out.println("getFormAddDoctor " + auth.getAuthorities());
            if (auth.getAuthorities().toString().contains("ROLE_ADMIN")) {
                Schedule schedule = new Schedule();
                List<Doctor> doctors = daoDoctor.listAll();

                model.addAttribute("schedule", schedule);
                model.addAttribute("doctors", doctors); // для вставки в <optional>

                System.out.println("form  заполнена");
                return "schedule/schedule-form";
            }
        }
        return "index";
    }
    @PostMapping("/add/")
    public String addSchedule(Schedule schedule, RedirectAttributes ra){
        System.out.println("form  получена " + schedule);

       // Map<Date, Boolean> mapTimeFoRecord = createSchedule(schedule.getStartTime(),schedule.getEndTime(),15);
       // List<Date> keyList = new ArrayList<>(mapTimeFoRecord.keySet());

       //List<RecordToDoctor> listTime= createScheduleToRecord1(schedule.getStartTime(),schedule.getEndTime(),15, schedule);

      //  schedule.setTimeRpiemaForRecord(mapTimeFoRecord);

        Schedule scheduleAdd = daoSchedule.save(schedule);
        System.out.println("scheduleAdd: " + scheduleAdd);

        createScheduleToRecord(scheduleAdd.getStartTime(),scheduleAdd.getEndTime(),15, scheduleAdd);

        ra.addFlashAttribute("goodMsg", "Расписание специалисту " +
                scheduleAdd.getDoctor().getPerson().getFullName() + " added");

    //    ra.addFlashAttribute("times", listTime);
        return "redirect:/schedule/";
    }
//    private Map<Date, Boolean> createSchedule(Date start, Date end, Integer timePriema){
//        Map<Date, Boolean> map = new HashMap<>();
//        Date time = start;
//        SimpleDateFormat simple = new SimpleDateFormat("HH:mm");
//        while (time.before(end)) {
//            map.put(time, false);
//            time.setTime(time.getTime()+timePriema*60000);
//            System.out.println("map.. "+ simple.format(time));
//        }
//        System.out.println("map"+ map);
////        SimpleDateFormat simple = new SimpleDateFormat("HH:mm");
////        System.out.println("map"+ simple.format(map.));
//        return map;
//    }
    private List<RecordToDoctor> createScheduleToRecord1(Date start, Date end, Integer timeAccept, Schedule schedule){

        RecordToDoctor recordToDoctor = null;
        Date time = start;
        Date timeH = new Date();
        SimpleDateFormat simple = new SimpleDateFormat("HH:mm");
        while (time.before(end)) {

            recordToDoctor = new RecordToDoctor(schedule,time);
            daoRecord.save(recordToDoctor);

            timeH.setTime(time.getTime()+timeAccept*60000);
            System.out.println("*///map.. "+ simple.format(timeH));
            time.setTime(timeH.getTime());
        }
        List<RecordToDoctor> list= daoRecord.getRecordsToSchedule(schedule);
       // System.out.println("map/**/"+ list);
//        SimpleDateFormat simple = new SimpleDateFormat("HH:mm");
//        System.out.println("map"+ simple.format(map.));
        return list;
    }
    private void createScheduleToRecord(Date start, Date end, Integer timeAccept, Schedule schedule){


        RecordToDoctor recordToDoctor1 = new RecordToDoctor(schedule, new Date());
        RecordToDoctor recordToDoctorAdd1 = daoRecord.save(recordToDoctor1);
        System.out.println("recordToDoctorAdd1 = " + recordToDoctorAdd1);

        schedule.getRecordToAccept().add(recordToDoctorAdd1);

        RecordToDoctor recordToDoctor2 = new RecordToDoctor(schedule,start);
        System.out.println("recordToDoctorAdd2 = " + recordToDoctor2.getTimeAccept());
//        Date date = recordToDoctor2.getTimeAccept();
//        date.setMinutes(date.getMinutes()+15);
//        System.out.println(date);
        recordToDoctor2.getTimeAccept().setTime(recordToDoctor2.getTimeAccept().getTime()+timeAccept*60000);
        RecordToDoctor recordToDoctorAdd2 = daoRecord.save(recordToDoctor2);
        System.out.println("recordToDoctorAdd2 = " + recordToDoctorAdd2);
        schedule.getRecordToAccept().add(recordToDoctorAdd2);
    }



    @GetMapping("/update/{id}")
    public String getFormUpdateSchedule(@PathVariable("id") Integer scheduleId, Model model){
        Optional<Schedule> schedule = daoSchedule.getById(scheduleId);
        List<Doctor> listDoc = daoDoctor.listAll();
       // List<Doctor> doctors = daoDoctor.ge();
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
    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable ("id") Integer scheduleId, @RequestParam String back, Model model){
        List<RecordToDoctor> listTime = daoRecord.getRecordsToSchedule(daoSchedule.getById(scheduleId).get());
        if (listTime != null) {
            model.addAttribute("times", listTime);
            model.addAttribute("back", back);
        }
        return "schedule/schedule-detail";
    }
    @GetMapping("/delete/{id}")
    public String deleteSchedule(@PathVariable ("id") Integer scheduleId){
        daoSchedule.delete(scheduleId);
        return "redirect:/schedule/";
    }


}
