package top.org.mvcambulapp.controllers;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import top.org.mvcambulapp.model.dao.recordtodoctor.IDaoRecord;
import top.org.mvcambulapp.model.dao.schedule.IDaoSchedule;
import top.org.mvcambulapp.model.entity.Doctor;
import top.org.mvcambulapp.model.entity.Person;
import top.org.mvcambulapp.model.entity.RecordToDoctor;
import top.org.mvcambulapp.model.entity.Schedule;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private IDaoRecord daoRecord;
    @Autowired
    private IDaoSchedule daoSchedule;



    @GetMapping("/")
    public String listAll(Model model,  Authentication auth){
        List<RecordToDoctor> listRecords = daoRecord.listAll();
        System.out.println("schedule/listAll " + auth.getAuthorities().toString());
        System.out.println("list " + listRecords.size());
        model.addAttribute("records", listRecords);
        model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));
       // model.addAttribute("back", back);
        return "record/record-l";
    }
    @GetMapping("/list/{id}")
    public String listRecordToDoctor(Model model, @PathVariable("id") Integer scheduleId, @RequestParam String back, Authentication auth){
        List<RecordToDoctor> listRecords = daoRecord.getRecordsToSchedule(daoSchedule.getById(scheduleId).get());
        model.addAttribute("records", listRecords);
        model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));
        model.addAttribute("back", back);
        return "record/record-doctor";
    }
    @GetMapping("/add/")
    public String getFormAddRecord(Model model, Authentication auth){
        System.out.println("getFormAddRecord");
        if (auth.getAuthorities().toString().contains("ROLE_ADMIN")) {
            RecordToDoctor record = new RecordToDoctor();

            List<Schedule> schedules = daoSchedule.listAll();

            System.out.println("форма record отправлена");
            model.addAttribute("record", record);
            model.addAttribute("schedules", schedules);

            return "record/record-form";
        }


        return null;
    }

//    @PostMapping("/add/{id}")
//    public String addRecord(@PathVariable("id") Integer scheduleId, Model model){
//        Optional<Schedule> schedule = daoSchedule.getById(scheduleId);
//        RecordToDoctor record = new RecordToDoctor();
//        Doctor doctor =  schedule.get().getDoctor();
//        Date date = schedule.get().getDate();
//
//
//        model.addAttribute("records", listRecords);
//        return "record/record-list";
//    }



}
