package top.org.mvcambulapp.controllers;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import top.org.mvcambulapp.model.dao.recordtodoctor.IDaoRecord;
import top.org.mvcambulapp.model.dao.schedule.IDaoSchedule;
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
    public String listAll(Model model){
        List<RecordToDoctor> listRecords = daoRecord.listAll();
        model.addAttribute("records", listRecords);
        return "record/record-list";
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
