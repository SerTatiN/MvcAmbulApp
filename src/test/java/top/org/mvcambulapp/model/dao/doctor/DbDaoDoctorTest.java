package top.org.mvcambulapp.model.dao.doctor;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.org.mvcambulapp.model.dao.person.DbDaoPerson;
import top.org.mvcambulapp.model.entity.Doctor;
import top.org.mvcambulapp.model.entity.Person;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DbDaoDoctorTest {

    @Autowired
    private DbDaoDoctor dbDaoDoctor;

    @Autowired
    private DbDaoPerson dbDaoPerson;

    @Test
    void listAll() {
        List<Doctor> list = dbDaoDoctor.listAll();
        for (Doctor doctor: list ) {
            System.out.println(doctor);
        }
    }

    @Test
    void getById() {
        Integer id;
        id = 1;
        Doctor doctor = dbDaoDoctor.getById(id).get();
        System.out.println(doctor);
        assertEquals(doctor.getId(),id);
    }

    @Test
    void save() {
        Calendar calendar = Calendar.getInstance();
        Date birthDate;
        Person person, personAdd;
        Doctor doctor, doctorAdd;

        calendar.set(1999, 6, 05);
        birthDate = calendar.getTime();
        System.out.println(birthDate);

        person = new Person("Лев","Петрович","Каменев","5q@m.ru",birthDate);
        personAdd = dbDaoPerson.save(person);

        String spec = "Терапевт";
        String info = "Окончил мед.факультет МГУ по специальности \"Лечебное дело\"";

        doctor = new Doctor(personAdd, spec, info);
        System.out.println("перед добавлением в БД: " + doctor);
        doctorAdd = dbDaoDoctor.save(doctor);
        System.out.println("после добавления в БД: " + doctorAdd);

        assertEquals(doctorAdd,doctor);

        assertNotNull(doctorAdd);
        assertEquals(doctor.getPerson().getSurname(),personAdd.getSurname());
    }

    @Test
    void update() {
//        Calendar calendar = Calendar.getInstance();
//        Date birthDate;
        Person person, personAdd;
        Doctor doctor, doctorUpd;
        Integer id;


        id = 3;
        doctor = dbDaoDoctor.getById(id).get();
        System.out.println("до обновления БД " + doctor);
       // person = dbDaoPerson.getById(4).get();
        String spec = "Хирург";
        String info = "Окончил мед.факультет МГУ по специальности \"Хирургия\"";
        doctor.setSpeciality(spec);
        doctor.setInfo(info);
        doctorUpd = dbDaoDoctor.update(doctor);
        assertNotNull(doctorUpd);
        System.out.println("после обновления БД"+ doctorUpd);
        assertEquals(doctor.getInfo(),info);
        assertEquals(doctor.getSpeciality(), spec);
    }

    @Test
    void delete() {
        Calendar calendar = Calendar.getInstance();
        Date birthDate;
        Person person, personAdd;
        calendar.set(1956, 3, 25);
        birthDate = calendar.getTime();
        person = new Person("Илья","Петрович","Земсков","10q@m.ru",birthDate);
        personAdd = dbDaoPerson.save(person);
        System.out.println(personAdd);

        Doctor doctor = new Doctor(person,"iuiw","wewe");
        Doctor doctorDel = dbDaoDoctor.save(doctor);
//        Integer id;
//        id = doctorDel.getId();
//        System.out.println(doctorDel);
//        dbDaoDoctor.delete(id);
//        assertEquals(Optional.empty(),dbDaoDoctor.getById(id));
    }
}