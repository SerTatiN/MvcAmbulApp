package top.org.mvcambulapp.model.dao.user;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.org.mvcambulapp.model.dao.doctor.DbDaoDoctor;
import top.org.mvcambulapp.model.dao.doctor.DoctorRepository;
import top.org.mvcambulapp.model.dao.doctor.IDaoDoctor;
import top.org.mvcambulapp.model.dao.patient.IDaoPatient;
import top.org.mvcambulapp.model.dao.person.DbDaoPerson;
import top.org.mvcambulapp.model.dao.person.IDaoPerson;
import top.org.mvcambulapp.model.dao.person.PersonRepository;
import top.org.mvcambulapp.model.dao.role.DbDaoRole;
import top.org.mvcambulapp.model.dao.role.IDaoRole;
import top.org.mvcambulapp.model.entity.Doctor;
import top.org.mvcambulapp.model.entity.Person;
import top.org.mvcambulapp.model.entity.Role;
import top.org.mvcambulapp.model.entity.User;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DbDaoUserTest {
//    @Autowired
//    private DbDaoUser daoUser;
//    @Autowired
//    private DbDaoRole daoRole;
//    @Autowired
//    private DbDaoPerson daoPerson;
//    @Autowired
//    private DbDaoDoctor daoDoctor;

    @Autowired
    private UserRepository daoUser;
    @Autowired
    private DbDaoRole daoRole;
    @Autowired
    private PersonRepository daoPerson;
    @Autowired
    private DoctorRepository daoDoctor;

    @Test
    @Transactional
    void addUser() {
//        User user = new User("user1", "qwerty");
//        daoUser.addUser(user);
//        Role role = new Role("ROLE_ADMIN");
//        System.out.println("Role " + role.toString());
//        Role roleAdd =daoRole.save(role);
//        // System.out.println("Role " + role.toString());
//        Set<Role> roles= new HashSet<>();
//        roles.add(roleAdd);

        User user = daoUser.save(new User("doc","qwerty"));
        user.getRoles().add(daoRole.getRoleByAuthority("ROLE_ADMIN"));

        System.out.println("user " + user.getRoles());
        System.out.println("user " + user.toString());

        Calendar calendar = Calendar.getInstance();
        Date birthDate;
        Person person, personAdd;

        calendar.set(1999, 6, 25,0,0,0);
        birthDate = calendar.getTime();

        person = new Person("Иван","Петрович","Петров",birthDate);
        person.setUser(user);
        personAdd = daoPerson.save(person);

        String spec = "Терапевт";
        String info = "Окончил мед.факультет МГУ по специальности \"Лечебное дело\"";


        Doctor doctor = new Doctor(personAdd, spec, info);
        System.out.println("перед добавлением в БД: " + doctor);
        Doctor doctorAdd = daoDoctor.save(doctor);
        System.out.println("после добавления в БД: " + doctorAdd);

// добавление пациента
       /* role = new Role("ROLE_PATIENT");
        System.out.println("Role " + role.toString());
        Role roleAdd =daoRole.save(role);
        // System.out.println("Role " + role.toString());
        Set<Role> roles= new HashSet<>();
        roles.add(roleAdd);
        User user = new User("pat","qwerty");
        user.setRoles(roles);
        System.out.println("user " + user.toString());
        User userAdd = daoUser.addUser(user);

        Calendar calendar = Calendar.getInstance();
        Date birthDate;
        Person person, personAdd;

        calendar.set(1990,06,02,0,0,0);
        birthDate = calendar.getTime();
        System.out.println(birthDate);
        person = new Person("Семен", Петрович","Семенов",birthDate,userAdd);
        personAdd = daoPerson.save(person);

        Patient patient = new Patient(personAdd);
        System.out.println("перед добавлением в БД: " + patient);
        Patient patientAdd = daoPatient.save(patient);
        System.out.println("после добавления в БД: " + patientAdd);
*/


    }

}