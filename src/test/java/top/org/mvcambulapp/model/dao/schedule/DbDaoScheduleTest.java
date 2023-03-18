package top.org.mvcambulapp.model.dao.schedule;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.org.mvcambulapp.model.dao.doctor.DbDaoDoctor;
import top.org.mvcambulapp.model.entity.Doctor;
import top.org.mvcambulapp.model.entity.Schedule;

import javax.print.Doc;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DbDaoScheduleTest {
    @Autowired
    private DbDaoSchedule dbDaoSchedule;
    @Autowired
    private DbDaoDoctor dbDaoDoctor;

    @Test
    void listAll() {
        List<Schedule> list = dbDaoSchedule.listAll();
        for (Schedule schedule: list) {
            System.out.println(schedule);
        }
    }

    @Test
    void getById() {
        Integer id;
        id = 3;
        Schedule schedule = dbDaoSchedule.getById(id).get();
        assertNotNull(schedule);
        assertEquals(schedule.getId(),id);

    }

    @Test
    void save() {
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2023, 2, 1, 0,0,0);
//        Date datePr = calendar.getTime();
//        calendar.set(2023, 2, 1, 8,0,0);
//        Date timeStart = calendar.getTime();
//        calendar.set(2023, 2, 1, 14,0,0);
//        Date timeEnd = calendar.getTime();
//
//        Doctor doctor = dbDaoDoctor.getById(2).get();
//
//        Schedule schedule = new Schedule(datePr,timeStart,timeEnd,doctor);
//        System.out.println("до ввода в БД: "+ schedule);
//        Schedule scheduleAdd = dbDaoSchedule.save(schedule);
//        System.out.println("после ввода в БД: "+ scheduleAdd);
//        assertNotNull(scheduleAdd);
//        assertEquals(scheduleAdd,schedule);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, 2, 1, 0,0,0);
        Date datePr = calendar.getTime();
        calendar.set(2023, 2, 1, 9,0,0);
        Date timeStart = calendar.getTime();
        Date timeEnd = calendar.getTime();

        Doctor doctor = dbDaoDoctor.getById(1).get();

        Schedule schedule = new Schedule(datePr,timeStart,timeEnd,doctor);
        System.out.println("до ввода в БД: "+ schedule);
        Schedule scheduleAdd = dbDaoSchedule.save(schedule);
        System.out.println("после ввода в БД: "+ scheduleAdd);
        assertNotNull(scheduleAdd);
        assertEquals(scheduleAdd,schedule);

    }

    @Test
    void update() {
        Integer id;

        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, 1, 28, 18,0,0);
        Date endTime = calendar.getTime();
        System.out.println("уст: " + endTime);
        id = 3;
        Schedule schedule = dbDaoSchedule.getById(id).get();
        System.out.println("из БД "+schedule.getEndTime());
        System.out.println(schedule.getId());
        schedule.setEndTime(endTime);
        System.out.println("после установки"+ schedule.getEndTime());
        Schedule scheduleUpd = dbDaoSchedule.update(schedule);
        System.out.println("из БД " + scheduleUpd.getEndTime());
        assertNotNull(scheduleUpd);

        assertEquals(scheduleUpd.getEndTime(), new java.sql.Date(endTime.getTime()));

    }

    @Test
    void delete() {

    }
}