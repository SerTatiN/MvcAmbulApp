package top.org.mvcambulapp.model.entity;

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

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private Set<Schedule> scheduleSet;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.PERSIST)
   // @JoinColumn(name="doctor_id", nullable = false)
   // private Doctor doctor;
    private Set<RecordToDoctor> recordToAccept;

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


    public Doctor(Person person, String speciality, String info) {
        this.person = person;
        this.speciality = speciality;
        this.info = info;
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

    public Set<RecordToDoctor> getRecordToAccept() {
        return recordToAccept;
    }

    public void setRecordToAccept(Set<RecordToDoctor> recordToAccept) {
        this.recordToAccept = recordToAccept;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", person=" + person.toString() +
                ", speciality='" + speciality + '\'' +
                ", info='" + info + '\'' +
              //  ", scheduleSet=" + scheduleSet +
              //  ", recordToAccept=" + recordToAccept +
                '}';
    }
}
