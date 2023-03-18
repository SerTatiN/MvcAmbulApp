package top.org.mvcambulapp.model.dao.recordtodoctor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.org.mvcambulapp.model.dao.schedule.DbDaoSchedule;
import top.org.mvcambulapp.model.entity.RecordToDoctor;
import top.org.mvcambulapp.model.entity.Schedule;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DbDaoRecordTest {
    @Autowired
    private DbDaoRecord dbDaoRecord;
    @Autowired
    private DbDaoSchedule dbDaoSchedule;

    @Test
    void listAll() {
        List<RecordToDoctor> list = dbDaoRecord.listAll();
        for (RecordToDoctor rd: list) {
            System.out.println(rd);
        }
    }

    @Test
    void getById() {
        Integer id;
        id = 3;
        RecordToDoctor rd = dbDaoRecord.getById(id).get();
        assertNotNull(rd);
        assertEquals(rd.getId(),id);
    }

    @Test
    void save() {
        Schedule sch1 = dbDaoSchedule.getById(1).get();
        List <Date> dateList = new ArrayList<>();
        Date time = sch1.getDate();
        for(int i = 1; i<=4; i++){
            time.setTime(time.getTime()+15*i*60000);
            System.out.println(i + " "+time);
            dateList.add(time);
        }

        RecordToDoctor rd1 = new RecordToDoctor(sch1,dateList.get(0));
        RecordToDoctor rd1a = dbDaoRecord.save(rd1);
        RecordToDoctor rd2 = new RecordToDoctor(sch1,dateList.get(1));
        RecordToDoctor rd2a = dbDaoRecord.save(rd2);
        RecordToDoctor rd3 = new RecordToDoctor(sch1,dateList.get(2));
        RecordToDoctor rd3a = dbDaoRecord.save(rd3);
        RecordToDoctor rd4 = new RecordToDoctor(sch1,dateList.get(3));
        RecordToDoctor rd4a = dbDaoRecord.save(rd4);
        listAll();
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }

    @Test
    void getRecordsToSchedule() {
    }
}