package top.org.mvcambulapp.model.dao.person;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.org.mvcambulapp.model.entity.Person;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DbDaoPersonTest {
    
    @Autowired
    private DbDaoPerson dbDaoPerson;

    @Test
    void listAll() {
        List<Person> list = dbDaoPerson.listAll();
        for (Person person : list) {
            System.out.println(person);
        }
    }

    @Test
    void getById() {
        Integer id = 1;
        Optional<Person> person = dbDaoPerson.getById(id);
        assertEquals(person.get().getId(),id);
    }

    @Test
    void save() {
        Calendar calendar = Calendar.getInstance();
        Date birthDate;
        Person person;
        Person personAdd;

        //calendar.set(1999, Calendar.JANUARY, 05);
        //birthDate = calendar.getTime();
      //  System.out.println(birthDate);

//        person = new Person("Петр","Петрович","Петров","2q@m.ru",birthDate);
//        personAdd = dbDaoPerson.save(person);
//        System.out.println(personAdd);
//        System.out.println(person);
//        assertEquals(person,personAdd);


        calendar.set(1989, Calendar.FEBRUARY, 25);
        birthDate = calendar.getTime();
        person = new Person("Иван","Петрович","Петров","3q@m.ru",birthDate);
        personAdd = dbDaoPerson.save(person);
        System.out.println(person);
        System.out.println(personAdd);
        assertNotNull(personAdd);
        assertEquals(person.getSurname(),personAdd.getSurname());

    }

    @Test
    void update() {
        Calendar calendar = Calendar.getInstance();
        Date birthDate;
        Person person, personUpd;
        Integer id;

        calendar.set(1999, 6, 25,0,0,0);
        birthDate = calendar.getTime();
        System.out.println(birthDate);
        id = 8;

        person = dbDaoPerson.getById(id).get();
        System.out.println("Было: " + person.getBirthdate());

        assertNotNull(person);

        person.setBirthdate(birthDate);
        System.out.println("поменяли в объекте " + person.getBirthdate());
        System.out.println("в БД до update(): " + dbDaoPerson.getById(id).get().getBirthdate());

        personUpd = dbDaoPerson.update(person);
        System.out.println("в БД после update(): " + personUpd.getBirthdate());

        assertEquals(personUpd.getBirthdate(),person.getBirthdate());

    }

    @Test
    void delete() {
//        Calendar calendar = Calendar.getInstance();
//        Date birthDate;
//        Person person, personAdd;
//        calendar.set(1946, Calendar.FEBRUARY, 25);
//        birthDate = calendar.getTime();
//        person = new Person("Иван","Петрович","Петров","4q@m.ru",birthDate);
//        personAdd = dbDaoPerson.save(person);
//        System.out.println(personAdd);

        Integer id;
//        id = person.getId();
//        dbDaoPerson.delete(id);
//        assertEquals(Optional.empty(),dbDaoPerson.getById(id));

        id=10;
        dbDaoPerson.delete(id);
       // assertEquals(Optional.empty(),dbDaoPerson.getById(id));

    }
}