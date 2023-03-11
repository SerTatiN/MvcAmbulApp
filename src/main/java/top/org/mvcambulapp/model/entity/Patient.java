package top.org.mvcambulapp.model.entity;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name="patient_t")
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
    private Set<RecordToDoctor> recordToDoctors; //записи к врачу (хранятся на будущее время, после принятия врача д.б. удалены

    //Мед.карта пациента, где хранятся результаты приема врачей записанные в ListPatientCard.
    // Доступна к просмотру пациенту и всем врачам (админ?)
    @OneToMany(mappedBy = "patient", cascade = CascadeType.PERSIST)
    private Set<ListPatientCard> patientCard;


    public Patient() {
    }

    public Patient(Integer id, Person person, Set<RecordToDoctor> recordToDoctors, Set<ListPatientCard> patientCard) {
        this.id = id;
        this.person = person;
        this.recordToDoctors = recordToDoctors;
        this.patientCard = patientCard;
    }

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
                ", person=" + person.toString() +
                ", recordToDoctors=" + recordToDoctors +
               // ", patientChart=" + patientChart +
                '}';
    }
}
