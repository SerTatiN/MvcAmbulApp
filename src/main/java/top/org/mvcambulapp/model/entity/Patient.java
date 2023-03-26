package top.org.mvcambulapp.model.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="t_patient")
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


//    @Column(nullable = false, length = 11)
//    private String phone;

    @OneToOne
    @JoinColumn(name="person_id",nullable = false)
    private Person person;

    @OneToMany(mappedBy = "patient",cascade = CascadeType.PERSIST)
    private Set<RecordToDoctor> recordToDoctors = new HashSet<>(); //записи к врачу (хранятся на будущее время, после принятия врача д.б. удалены

    //Мед.карта пациента, где хранятся результаты приема врачей записанные в ListPatientCard.
    // Доступна к просмотру пациенту и всем врачам (админ?)
//    @OneToMany(mappedBy = "patient", cascade = CascadeType.PERSIST)
//    private Set<ListPatientCard> patientCard = new HashSet<>();

//    @OneToOne
//    @JoinColumn(name="user_id", nullable = false)
//    private User user;


    public Patient() {
    }

    public Patient(Person person) {
        this.person = person;
    }

    public Patient(Person person, Set<RecordToDoctor> recordToDoctors) {
        this.person = person;
        this.recordToDoctors = recordToDoctors;
    }
    //    public Patient(Person person, Set<RecordToDoctor> recordToDoctors, Set<ListPatientCard> patientCard) {
//        this.person = person;
//        this.recordToDoctors = recordToDoctors;
//        this.patientCard = patientCard;
//    }
    //    public Patient(Person person, User user) {
//        this.person = person;
//        this.user = user;
//    }
//
//    public Patient(Person person, Set<RecordToDoctor> recordToDoctors, Set<ListPatientCard> patientCard, User user) {
//        this.person = person;
//        this.recordToDoctors = recordToDoctors;
//        this.patientCard = patientCard;
//        this.user = user;
//    }

//    public Set<ListPatientCard> getPatientCard() {
//        return patientCard;
//    }
//
//    public void setPatientCard(Set<ListPatientCard> patientCard) {
//        this.patientCard = patientCard;
//    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Set<RecordToDoctor> getRecordToDoctors() {
        return recordToDoctors;
    }

    public void setRecordToDoctors(Set<RecordToDoctor> recordToDoctors) {
        this.recordToDoctors = recordToDoctors;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", person=" + person +
                ", recordToDoctors=" + recordToDoctors +
 //               ", patientCard=" + patientCard +
//                ", user=" + user +
                '}';
    }
}
