package top.org.mvcambulapp.model.dao.listpatientcard;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.org.mvcambulapp.model.dao.doctor.DbDaoDoctor;
import top.org.mvcambulapp.model.dao.patient.DbDaoPatient;
import top.org.mvcambulapp.model.dao.recordtodoctor.DbDaoRecord;
import top.org.mvcambulapp.model.dao.schedule.DbDaoSchedule;
import top.org.mvcambulapp.model.entity.Doctor;
import top.org.mvcambulapp.model.entity.ListPatientCard;
import top.org.mvcambulapp.model.entity.Patient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DbDaoListPatientCardTest {
    @Autowired
    private DbDaoListPatientCard daoListPatientCard;

//    @Autowired
//    private DbDaoPatient daoPatient;
//
//    @Autowired
//    private DbDaoDoctor daoDoctor;

//    @Autowired // попробовать заменить
//    private DbDaoSchedule daoSchedule;
    @Autowired
    private DbDaoRecord daoRecord;




    @Test
    void listAll() {
        List<ListPatientCard> list = daoListPatientCard.listAll();
        for (ListPatientCard card: list ) {
            System.out.println(card);
        }
    }

    @Test
    void getById() {
        ListPatientCard card = daoListPatientCard.getById(1).get();
        System.out.println(card);
        assertEquals(card.getId(),1);
    }

    @Test
    void save() {

        ListPatientCard card = new ListPatientCard(daoRecord.getById(1).get());



    }
}