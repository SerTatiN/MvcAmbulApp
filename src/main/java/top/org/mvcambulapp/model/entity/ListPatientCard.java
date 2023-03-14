package top.org.mvcambulapp.model.entity;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name="t_patient_card")
public class ListPatientCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "patient_id",nullable = false)
    private Patient patient;

    @Column(nullable = false)
    private Date dataAccept;

    @Column(nullable = false, length = 155)
    private String specialityDoctor;  //врач(Person) может поменяться, а специальность врача важнее для карты

    @ManyToOne
    @JoinColumn(name = "doctor_id",nullable = false)
    private Doctor doctor;

    @Column(nullable = false, length = 255)
    private String result;

    public ListPatientCard() {
    }

    public ListPatientCard(Integer id, Patient patient, Date dataAccept, String specialityDoctor, Doctor doctor, String result) {
        this.id = id;
        this.patient = patient;
        this.dataAccept = dataAccept;
        this.specialityDoctor = specialityDoctor;
        this.doctor = doctor;
        this.result = result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public Date getDataAccept() {
        return dataAccept;
    }
    public String getDataAcceptPrint() {
        SimpleDateFormat simple = new SimpleDateFormat("dd.MM.Y");
        return simple.format(dataAccept);
    }

    public void setDataAccept(Date dataAccept) {
        this.dataAccept = dataAccept;
    }

    public String getSpecialityDoctor() {
        return specialityDoctor;
    }

    public void setSpecialityDoctor(String specialityDoctor) {
        this.specialityDoctor = specialityDoctor;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ListPatientCard{" +
                "id=" + id +
                ", patient=" + patient.getPerson().getSurname() +
                ", dataAccept=" + getDataAcceptPrint() +
                ", specialityDoctor='" + specialityDoctor + '\'' +
                ", doctor=" + doctor.getPerson().getSurname() +
                ", result='" + result + '\'' +
                '}';
    }

}
