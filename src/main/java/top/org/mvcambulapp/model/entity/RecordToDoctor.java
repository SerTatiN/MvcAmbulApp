package top.org.mvcambulapp.model.entity;

import jakarta.persistence.*;
import org.apache.tomcat.util.modeler.ParameterInfo;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "t_record_to_doctor")
public class RecordToDoctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name="patient_id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name="doctor_id", nullable = false)
    private Doctor doctor;

    private Date dateAccept;
    private Date timeAccept;

    private boolean isRecord;
    private boolean isAccept;

    public RecordToDoctor() {

    }

    public RecordToDoctor(Integer id, Patient patient, Doctor doctor, Date dateAccept, Date timeAccept, boolean isRecord, boolean isAccept) {
        this.id = id;
        this.patient = patient;
        this.doctor = doctor;
        this.dateAccept = dateAccept;
        this.timeAccept = timeAccept;
        this.isRecord = isRecord;
        this.isAccept = isAccept;
    }

    public RecordToDoctor(Patient patient, Doctor doctor, Date dateAccept, Date timeAccept) {
        this.patient = patient;
        this.doctor = doctor;
        this.dateAccept = dateAccept;
        this.timeAccept = timeAccept;
        this.isRecord = false;
        this.isAccept = false;
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

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public Date getDateAccept() {
        return dateAccept;
    }
    public String getDateAcceptPrint() {
        SimpleDateFormat simple = new SimpleDateFormat("dd.MM.Y");
        return simple.format(dateAccept);
    }

    public void setDateAccept(Date dateAccept) {
        this.dateAccept = dateAccept;
    }

    public Date getTimeAccept() {
        return timeAccept;
    }
    public String getTimeAcceptPrint() {
        SimpleDateFormat simple = new SimpleDateFormat("hh:mm");
        return simple.format(timeAccept);
    }

    public void setTimeAccept(Date timeAccept) {
        this.timeAccept = timeAccept;
    }

    public boolean isRecord() {
        return isRecord;
    }

    public void setRecord(boolean record) {
        isRecord = record;
    }

    public boolean isAccept() {
        return isAccept;
    }

    public void setAccept(boolean accept) {
        isAccept = accept;
    }

    @Override
    public String toString() {
        return "RecordToDoctor{" +
                "id=" + id +
                ", patient=" + patient+
                ", doctor=" + doctor.getPerson().getSurname() +
                ", dateAccept=" + getDateAcceptPrint() +
                ", timeAccept=" + getTimeAcceptPrint() +
                ", isRecord=" + isRecord +
                ", isAccept=" + isAccept +
                '}';
    }
}
