package top.org.mvcambulapp.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "t_doctor")
public class Doctor{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    @JoinColumn(name="person_id",nullable = false)
    private Person person;

    @Column(nullable = false, length = 25)
    private String speciality;

    @Column(nullable = false,length = 250)
    private String info;

    @Column(length = 250)
    private String photo;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private Set<Schedule> scheduleSet;

    public Doctor() {
    }

    public Doctor(String speciality, String info) {
        this.speciality = speciality;
        this.info = info;
    }

    public Doctor(Integer id, Person person, String speciality, String info) {
        this.id = id;
        this.person = person;
        this.speciality = speciality;
        this.info = info;
    }

    public Doctor(Person person, String speciality, String info, Set<Schedule> scheduleSet) {
        this.person = person;
        this.speciality = speciality;
        this.info = info;
        this.scheduleSet = scheduleSet;
    }

    public Doctor(Integer id, Person person, String speciality, String info, String photo) {
        this.id = id;
        this.person = person;
        this.speciality = speciality;
        this.info = info;
        this.photo = photo;
    }

    public Doctor(Person person, String speciality, String info) {
        this.person = person;
        this.speciality = speciality;
        this.info = info;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<Schedule> getScheduleSet() {
        return scheduleSet;
    }

    public void setScheduleSet(Set<Schedule> scheduleSet) {
        this.scheduleSet = scheduleSet;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", person=" + person.toString() +
                ", speciality='" + speciality + '\'' +
                ", info='" + info + '\'' +
                ", scheduleSet=" + scheduleSet +
              //  ", recordToAccept=" + recordToAccept +
                '}';
    }
}
