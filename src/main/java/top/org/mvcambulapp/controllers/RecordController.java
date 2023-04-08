package top.org.mvcambulapp.controllers;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import top.org.mvcambulapp.model.dao.doctor.IDaoDoctor;
import top.org.mvcambulapp.model.dao.patient.IDaoPatient;
import top.org.mvcambulapp.model.dao.recordtodoctor.IDaoRecord;
import top.org.mvcambulapp.model.dao.recordtodoctor.RecordRepository;
import top.org.mvcambulapp.model.dao.schedule.IDaoSchedule;
import top.org.mvcambulapp.model.dao.user.DbDaoUser;
import top.org.mvcambulapp.model.entity.*;

import javax.xml.crypto.Data;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

@Controller
@RequestMapping("/record")
public class RecordController {
    @Autowired
    private IDaoRecord daoRecord;
    @Autowired
    private IDaoSchedule daoSchedule;
    @Autowired
    private IDaoDoctor daoDoctor;

    @Autowired
    private IDaoPatient daoPatient;
    @Autowired
    private DbDaoUser daoUser;



    @GetMapping("/")
    public String listAll(Model model,  Authentication auth){
        List<RecordToDoctor> records = daoRecord.listAll();

        System.out.println("list " + records.size());
        model.addAttribute("records", records);
        model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));
       // model.addAttribute("back", back);
        return "record/record-l";
    }
    //Показать записанных пациентов к доктору @RequestParam String back,  model.addAttribute("back", back);
//    @GetMapping("/list")//исключить
//    public String listRecordToDoctor(Model model, Authentication auth){
//        System.out.println("listRecordToDoctor(): user= " + daoUser.currentUser().getPerson().getFullName());
//
//        if (auth.getAuthorities().toString().contains("ROLE_DOCTOR")) {
//          //  List<RecordToDoctor> records = daoRecord.getRecordsByDoctorId(daoUser.currentUser().getPerson().getDoctor().getId());
//            List<RecordToDoctor> records = daoRecord.getRecordsByDoctorIdSortedByTime(daoUser.currentUser().getPerson().getDoctor().getId());
//
//            System.out.println("list " + records.size());
//
////            for (RecordToDoctor rd: records) {
////                System.out.println(rd);
////            }
//            model.addAttribute("doctor", daoUser.currentUser().getPerson().getDoctor());
//            model.addAttribute("records", records);
////            model.addAttribute("isDoctor", auth.getAuthorities().toString().contains("ROLE_DOCTOR"));
//
//            return "record/record-doctor";
//        }
//        return "record/record-l";
//    }

    //Показать записанных пациентов к доктору на дату из Schedule;
    @GetMapping("/doctor-list-date/{id}")
    public String listRecordAtDate(@PathVariable("id") Integer scheduleId, Model model, Authentication auth,
                                   @RequestParam String back){
        System.out.println("listRecordToDoctorAtDate(): user= " + daoUser.currentUser().getPerson().getFullName());
        System.out.println("doctor-"+ daoSchedule.getById(scheduleId).get().getDoctor());
        System.out.println("date-"+ daoSchedule.getById(scheduleId).get().getDate());

        if (auth.getAuthorities().toString().contains("ROLE_DOCTOR")) {
            List<RecordToDoctor> records = daoRecord.getRecordsByScheduleIdSortedByTime(scheduleId);
            System.out.println("list " + records.size());
                //daoRecord.getRecordsByDoctorIdSortedByTime(daoUser.currentUser().getPerson().getDoctor().getId());
//            if (!records.isEmpty()) {
                model.addAttribute("schedule", daoSchedule.getById(scheduleId).get());
                //        model.addAttribute("doctor", daoUser.currentUser().getPerson().getDoctor());//?
                model.addAttribute("records", records);
                model.addAttribute("back", back);
                model.addAttribute("isDoctor", auth.getAuthorities().toString().contains("ROLE_DOCTOR"));
            return "record/doctor-list-date";
        }
        return "record/record-l";
    }

//Записанные пациенты на дату? выбрать дату
    @GetMapping("/list-form-date")
    public String listRecordFormDate(Model model, Authentication auth){
        System.out.println("listRecordFormDate(): user= " + daoUser.currentUser().getPerson().getFullName());

        if (auth.getAuthorities().toString().contains("ROLE_DOCTOR")) {
            List<Schedule> schedules = daoSchedule.getScheduleByDoctorId(daoUser.currentUser().getPerson().getDoctor().getId());
//            if (!schedules.isEmpty()) {
            //  List<RecordToDoctor> records = daoRecord.getRecordsByDoctorId(daoUser.currentUser().getPerson().getDoctor().getId());
          //  List<RecordToDoctor> records = daoRecord.getRecordsByDoctorIdAndDate(daoUser.currentUser().getPerson().getDoctor().getId(),date);

            System.out.println("list " + schedules.size());

            model.addAttribute("schedules", schedules);
         // model.addAttribute("isDoctor", daoUser.currentUser().getPerson().getDoctor());
            model.addAttribute("schedule", new Schedule());
            model.addAttribute("isDoctor", auth.getAuthorities().toString().contains("ROLE_DOCTOR"));

            return "record/form-case-date";
        }
        return "record/record-l";
    }

    @PostMapping("/list-form-date")
    public String recordsAtDate(Schedule schedule, Model model, Authentication auth){
        System.out.println("recordsAtDate");
        System.out.println("recordsAtDate: user= " + daoUser.currentUser().getPerson().getFullName());

         System.out.println("schedule " + schedule.getId());
        if (auth.getAuthorities().toString().contains("ROLE_DOCTOR")) {
           List<RecordToDoctor> records = daoRecord.getRecordsByScheduleIdSortedByTime(schedule.getId());
           if (!records.isEmpty()) {
               model.addAttribute("schedule", daoSchedule.getById(schedule.getId()).get());
               model.addAttribute("records", records);
               model.addAttribute("doctor", daoUser.currentUser().getPerson().getDoctor());
               model.addAttribute("isDoctor", auth.getAuthorities().toString().contains("ROLE_DOCTOR"));
               model.addAttribute("back", "list-form");
           } else {
               model.addAttribute("goodMsg", "У Вас нет записанных пациентов");
           }
           return "record/doctor-list-date";
        }
        return "record/record-l";
    }
//записанные пациенты на текущий день для приема
    @GetMapping("/list-today")
    public String listRecordToDoctorToday(Model model, Authentication auth){
        System.out.println("listRecordToDoctorToday(): user= " + daoUser.currentUser().getPerson().getFullName());
      //  Date date = new Date(2023-1900,02,27);
        LocalDate ldate = LocalDate.now();
        Date date = Date.from(ldate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        System.out.println("date " + date);
        if (auth.getAuthorities().toString().contains("ROLE_DOCTOR")) {
            Optional<Schedule> schedule = daoSchedule.getScheduleByDoctorIdAndDate(daoUser.currentUser().getPerson().getDoctor().getId(), date);
            //  List<RecordToDoctor> records = daoRecord.getRecordsByDoctorId(daoUser.currentUser().getPerson().getDoctor().getId());
            //List<RecordToDoctor> records = daoRecord.getRecordsByDoctorIdAndDate(daoUser.currentUser().getPerson().getDoctor().getId(),date);

            if (schedule.isPresent()) {
                System.out.println("schedule--" + schedule.get());
                model.addAttribute("schedule", schedule.get());
                model.addAttribute("doctor", daoUser.currentUser().getPerson().getDoctor());
                //   model.addAttribute("records", records);
             //   model.addAttribute("date", date);
                model.addAttribute("isDoctor", auth.getAuthorities().toString().contains("ROLE_DOCTOR"));
               //
               // return "record/doctor-list-date";
            } else {
                model.addAttribute("goodMsg", "У Вас сегодня нет приема");
                System.out.println("Сегодня у вас нет приема " );
            }
            model.addAttribute("back", "today");
            return "record/doctor-list-date";
            }
        return "index";
    }


// Записи пациента
    @GetMapping("/patient-records")
    public String listRecordsForPatient(Model model, Authentication auth){
        System.out.println("listRecordsForPatient: user= " + daoUser.currentUser().getPerson().getFullName());

        if (auth.getAuthorities().toString().contains("ROLE_PATIENT")) {
            List<RecordToDoctor> records = daoRecord.getRecordsByPatientId (daoUser.currentUser().getPerson().getPatient().getId());

            model.addAttribute("patient", daoUser.currentUser().getPerson().getPatient());
            model.addAttribute("records", records);
            return "record/record-patient";
        }
        return "index";
    }


    @GetMapping("/add/")
    public String getFormAddRecord(Model model, Authentication auth){
        System.out.println("getFormAddRecord");
        if (auth.getAuthorities().toString().contains("ROLE_ADMIN")) {
            RecordToDoctor record = new RecordToDoctor();

            List<Doctor> doctors = daoDoctor.listAll();

            model.addAttribute("record", record);
            model.addAttribute("doctors", doctors);
            model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));
            System.out.println("record: "+ record);
            return "record/record-form";
        }
        System.out.println("add record fail");
        return "record/record-form";
    }

    @PostMapping("/add/")
    public String addRecord(RecordToDoctor record, RedirectAttributes ra) {
        System.out.println("addRecord ");
        System.out.println("addRecord " + record);
        try {
            Optional<Schedule> schedule = daoSchedule.getScheduleByDoctorAndDate(record.getSchedule().getDoctor(),
                    record.getSchedule().getDate());
            if (schedule != null) {
                System.out.println("addRecord/schedule (1) " + schedule.get());
                if (record.getTimeAccept().isBefore(schedule.get().getEndTime()) &&
                        record.getTimeAccept().isAfter(schedule.get().getStartTime())) {
                    record.setSchedule(schedule.get());
                    RecordToDoctor recordAdd = daoRecord.save(record);
                    schedule.get().getRecordToAccept().add(recordAdd);
                    System.out.println("addRecord/schedule (2)" + schedule);
                    ra.addFlashAttribute("goodMsg", "Время записи к врачу " +
                            schedule.get().getDoctor().getPerson().getFullName() + " добавлено!");
                    return "redirect:/record/";
                } else {
                    ra.addFlashAttribute("goodMsg", "Время записи к врачу " +
                            schedule.get().getDoctor().getPerson().getFullName() + " не может быть добавлено, так " +
                            "как время приема выходит за рамки часов приема!");
                    return "redirect:/record/";
                }
            }
        } catch (NoSuchElementException e) {
            System.out.println("Необходимо создать расписание на этот день " + e);
        }
        ra.addFlashAttribute("goodMsg", "Необходимо добавить расписание для врача на этот день!");
        return "redirect:/schedule/list";
    }

    @GetMapping("/update/{id}")
    public String getFormUpdateRecord(@PathVariable("id") Integer recordId, Model model, Authentication auth){
        System.out.println("getFormUpdateRecord");
        if (auth.getAuthorities().toString().contains("ROLE_ADMIN")) {

            RecordToDoctor record = daoRecord.getById(recordId).get();

            System.out.println("форма record отправлена " + record);
            model.addAttribute("record", record);

            model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));
            System.out.println("форма record отправлена (2)" + record);
            return "record/record-update";
        }
        System.out.println("upd record fail");
        return "record/record-l";
    }

    // Изменяем время приема
    @PostMapping("/update/")
    public String updateRecord(RecordToDoctor record, RedirectAttributes ra) {
        System.out.println("updateRecord " + record);
        Schedule schedule = daoSchedule.getById(record.getSchedule().getId()).get();

        System.out.println("updRecord/schedule (1) " + schedule);
        if (!record.isRecord()) {
            if (record.getTimeAccept().isBefore(schedule.getEndTime()) &&
                    record.getTimeAccept().isAfter(schedule.getStartTime())) {
                //record.setSchedule(schedule);
                RecordToDoctor recordUpd = daoRecord.update(record);
                //???? schedule.getRecordToAccept().add(recordUpd);
                System.out.println("updRecord/schedule (2)" + schedule);
                ra.addFlashAttribute("goodMsg", "Время записи врачу " +
                        schedule.getDoctor().getPerson().getFullName() + " обновлено!");
                return "redirect:/record/";
            } else {
                ra.addFlashAttribute("goodMsg", "Время для записи к врачу " +
                        schedule.getDoctor().getPerson().getFullName() + " не может быть обновлено, так " +
                        "как время приема выходит за рамки часов приема!");
                return "redirect:/record/";
            }
        } else {
            System.out.println("Необходимо предупредить записанного пациента !");
            ra.addFlashAttribute("goodMsg", "Перед изменением времени приема к врачу " +
                    schedule.getDoctor().getPerson().getFullName() + " необходимо предупредить записанного пациента!");

            return "redirect:/record/";
        }
    }

    //Запись пациента Админом
    @GetMapping("/patient/{id}")
    public String getFormRecordPatient(@PathVariable ("id") Integer recordId, Model model, Authentication auth){
        System.out.println("recordPatient " );
        if (auth.getAuthorities().toString().contains("ROLE_ADMIN")){
            RecordToDoctor record = daoRecord.getById(recordId).get();
            System.out.println("форма recordPatient отправлена");
            List <Patient> patients = daoPatient.listAll();
            model.addAttribute("record", record);
            model.addAttribute("patients", patients);
            model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));
            return "record/record-patient-form";
        }
        System.out.println("patient record fail");
        return "record/record-l";
        }
    @PostMapping("/patient/")
    public String recordPatient(RecordToDoctor record, RedirectAttributes ra) {
        System.out.println(" recordPatient() " + record);
        System.out.println(" record.getPatient() " + record.getPatient());
//        Schedule schedule = daoSchedule.getById(record.getSchedule().getId()).get();

//        System.out.println("updRecord/schedule (1) " + schedule);
        if (record.getPatient() != null) {
           // record.setPatient(record.getPatient());
            record.setRecord(true);
            RecordToDoctor recordUpd = daoRecord.update(record);

            System.out.println("updRecord/recordPatient/" + recordUpd);
            ra.addFlashAttribute("goodMsg", "Пациент " +
                        record.getPatient().getPerson().getFullName() + " записан!");
            return "redirect:/record/";
        } else {
            System.out.println("Необходимо выбрать пациента!");
            ra.addFlashAttribute("goodMsg", "Пациент не выбран!");

            return "redirect:/record/";
        }
    }

    @GetMapping("/delete/{id}")
    public String deleteRecordPatient(@PathVariable ("id") Integer recordId, RedirectAttributes ra, Authentication auth){
        System.out.println("deleteRecordPatient " +  recordId);
        if (auth.getAuthorities().toString().contains("ROLE_ADMIN")){
            RecordToDoctor record = daoRecord.getById(recordId).get();
            System.out.println("record delete " + record);
            //List <Patient> patients = daoPatient.listAll();
            record.setPatient(null);
            record.setRecord(false);
            RecordToDoctor recordUpd = daoRecord.update(record);
            System.out.println("record deleted " + recordUpd);
//            model.addAttribute("record", record);
//            model.addAttribute("patients", patients);
//            model.addAttribute("isAdmin", auth.getAuthorities().toString().contains("ROLE_ADMIN"));
 //           return "record/record-patient-form";
            ra.addFlashAttribute("goodMsg", "Запись пациента удалена!");
            return "redirect:/record/";

        }
        System.out.println("patient record fail");
        return "record/record-l";
    }


    //    Подтверждение запись пациента самому
    @GetMapping("/record-to-doctor/{id}")
    public String getFormRecordPatientSelf(@PathVariable ("id") Integer recordId, Model model, @RequestParam String back,Authentication auth){
        System.out.println("getFormRecordPatientSelf " + daoUser.currentUser().getPerson().getFullName());
        if (auth.getAuthorities().toString().contains("ROLE_PATIENT")) {
            RecordToDoctor record = daoRecord.getById(recordId).get();

            System.out.println("форма подтверждения recordPatient отправлена " + record);
            System.out.println("1 " + record.getPatient());
            System.out.println("2 " + record.getSchedule());
            System.out.println("3 " + daoUser.currentUser().getPerson().getPatient());


//            List <Patient> patients = daoPatient.listAll();
          //  model.addAttribute("recordPat", new RecordToDoctor());
            model.addAttribute("record", record);
            model.addAttribute("patient", daoUser.currentUser().getPerson().getPatient());
            model.addAttribute("isPatient", auth.getAuthorities().toString().contains("ROLE_PATIENT"));
            model.addAttribute("back", back);
            return "record/confirm-patient";
        }
        System.out.println("patient record fail");
        return "record/record-l";
    }

    @PostMapping("/record-to-doctor/")
    public String recordPatientSelf(RecordToDoctor record, RedirectAttributes ra) {

        record.setPatient(daoUser.currentUser().getPerson().getPatient());

        record.setRecord(true);

        RecordToDoctor recordUpd = daoRecord.update(record);
        System.out.println(" recordUpd " + recordUpd);

        ra.addFlashAttribute("goodMsg", "Пациент " +
                record.getPatient().getPerson().getFullName() + " записан!");
        return "redirect:/doctor/list";

    }
    @GetMapping("/patient-delete/{id}")
    public String deleteRecordPatientSelf(@PathVariable ("id") Integer recordId, RedirectAttributes ra, Authentication auth){
        System.out.println("deleteRecordPatientSelf " +  recordId);
        if (auth.getAuthorities().toString().contains("ROLE_PATIENT")){
            RecordToDoctor record = daoRecord.getById(recordId).get();
            System.out.println("record delete " + record);
            LocalDate ldate = LocalDate.now();
            Date date = Date.from(ldate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (record.getSchedule().getDate().after(date)) {
                record.setPatient(null);
                record.setRecord(false);
                RecordToDoctor recordUpd = daoRecord.update(record);
                System.out.println("record deleted " + recordUpd);

                ra.addFlashAttribute("goodMsg", "Запись пациента удалена!");
            } else {
                ra.addFlashAttribute("goodMsg", "Запись пациента не удалена!");
            }

            return "redirect:/record/patient-records";
        }
        System.out.println("patient-delete fail");
        return "index";
    }

    @GetMapping("/detail/{id}")
    public String getDetail(@PathVariable ("id") Integer recordId,  Model model,Authentication auth){
        Optional<RecordToDoctor>  record = daoRecord.getById(recordId);
        System.out.println("getDetail" + record.get());
        if (record.isPresent()) {
            model.addAttribute("record", record.get());
            model.addAttribute("isAdmin",auth.getAuthorities().toString().contains("ROLE_ADMIN"));
        }
//        model.addAttribute("back", back);@RequestParam String back,
        return "record/record-detail";
    }


}
