package top.org.mvcambulapp.model.dao.user;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.org.mvcambulapp.model.dao.doctor.DbDaoDoctor;
import top.org.mvcambulapp.model.dao.doctor.DoctorRepository;
import top.org.mvcambulapp.model.dao.doctor.IDaoDoctor;
import top.org.mvcambulapp.model.dao.patient.DbDaoPatient;
import top.org.mvcambulapp.model.dao.patient.IDaoPatient;
import top.org.mvcambulapp.model.dao.person.DbDaoPerson;
import top.org.mvcambulapp.model.dao.person.IDaoPerson;
import top.org.mvcambulapp.model.dao.person.PersonRepository;
import top.org.mvcambulapp.model.dao.role.DbDaoRole;
import top.org.mvcambulapp.model.dao.role.IDaoRole;
import top.org.mvcambulapp.model.entity.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DbDaoUserTest {
    @Autowired
    private DbDaoUser daoUser;
    @Autowired
    private DbDaoRole daoRole;
    @Autowired
    private DbDaoPerson daoPerson;
    @Autowired
    private DbDaoPatient daoPatient;

    @Autowired
    private DbDaoDoctor daoDoctor;

//    @Autowired
//    private UserRepository daoUser;
//    @Autowired
//    private DbDaoRole daoRole;
//    @Autowired
//    private PersonRepository daoPerson;
//    @Autowired
//    private DoctorRepository daoDoctor;

    @Test
   // @Transactional
    void addUser() {
        addDoctor();
        addAdmin();
        addPatient();

// Добавление админа
   /*     User user = new User("admin4","qwerty");
        user.setRoles(Collections.singleton((daoRole.getRoleByAuthority("ROLE_ADMIN"))));
        User userAdd = daoUser.save(user);

        System.out.println("роль выбрана 1" + userAdd.getRoles());
        System.out.println("user 2 " + userAdd.toString());

        Calendar calendar = Calendar.getInstance();
        Date birthDate;
        Person person, personAdd;

        calendar.set(1999, 6, 25,0,0,0);
        birthDate = calendar.getTime();

        person = new Person("Иван","Петрович","Admin4",birthDate);
        person.setUser(user);
        personAdd = daoPerson.save(person);
        System.out.println("user " + personAdd.toString());
        System.out.println("после добавлением в БД: " + personAdd);
*/
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
    private  void addDoctor(){
  // Добавление доктора
        User user = new User("doc","qwerty");
        user.setRoles(Collections.singleton((daoRole.getRoleByAuthority("ROLE_DOCTOR"))));
        User userAdd = daoUser.save(user);
   //        Person person, personAdd;
        Calendar calendar = Calendar.getInstance();
        calendar.set(1999, 6, 25,0,0,0);
        Date birthDate = calendar.getTime();

//        person = new Person("Кирилл","Петрович","Грач",birthDate);
//        person.setUser(user);
        Person personAdd = daoPerson.save(new Person("Кирилл","Петрович","Грач",birthDate,user));
        System.out.println("user " + personAdd.toString());
        System.out.println("после добавлением в БД: " + personAdd);

        String spec = "Терапевт";
        String info = "Окончил мед.факультет МГУ по специальности \"Лечебное дело\"";

        Doctor doctor = new Doctor(spec, info);
        doctor.setPerson(personAdd);

        Doctor doctorAdd = daoDoctor.save(doctor);
        System.out.println("после добавления в БД: " + doctorAdd);

    }

    // Добавление админа
    private void addAdmin(){

        User user = new User("admin","qwerty");
        user.setRoles(Collections.singleton((daoRole.getRoleByAuthority("ROLE_ADMIN"))));
        User userAdd = daoUser.save(user);

        System.out.println("роль выбрана 1" + userAdd.getRoles());
        System.out.println("user 2 " + userAdd.toString());

        Calendar calendar = Calendar.getInstance();
        Date birthDate;
        Person person, personAdd;

        calendar.set(1999, 6, 25,0,0,0);
        birthDate = calendar.getTime();

        person = new Person("Иван","Петрович","Петров",birthDate);
        person.setUser(user);
        personAdd = daoPerson.save(person);
        System.out.println("user " + personAdd.toString());
        System.out.println("после добавлением в БД: " + personAdd);

    }
    // добавление пациента
    private void addPatient(){
        User user = new User("pat","qwerty");
        user.setRoles(Collections.singleton((daoRole.getRoleByAuthority("ROLE_PATIENT"))));
        User userAdd = daoUser.save(user);



        Calendar calendar = Calendar.getInstance();
        calendar.set(1990,06,02,0,0,0);
        Date birthDate = calendar.getTime();
        System.out.println(birthDate);

        Person person = new Person("Семен", "Петрович","Семенов", birthDate,userAdd);
        Person personAdd = daoPerson.save(person);

        Patient patient = new Patient(personAdd);
        System.out.println("перед добавлением в БД: " + patient);
        Patient patientAdd = daoPatient.save(patient);
        System.out.println("после добавления в БД: " + patientAdd);

    }


}