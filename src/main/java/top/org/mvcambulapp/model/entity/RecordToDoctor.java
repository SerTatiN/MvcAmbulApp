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
    @JoinColumn(name="patient_id")//nullable = false, null если не записан
    private Patient patient;

    @ManyToOne
    @JoinColumn(name="schedule_id", nullable = false)
    private Schedule schedule;

   // private Date dateAccept;
    private Date timeAccept;

    private boolean isRecord;
    private boolean isAccept;

    public RecordToDoctor() {

    }
    public RecordToDoctor(Schedule schedule, Date timeAccept) {//Date dateAccept,
        this.id = id;
        this.patient = null;
        this.schedule = schedule;
       // this.dateAccept = dateAccept;
        this.timeAccept = timeAccept;
        this.isRecord = false;
        this.isAccept = false;
    }

    public RecordToDoctor(Integer id, Patient patient, Schedule schedule, Date timeAccept, boolean isRecord, boolean isAccept) {//Date dateAccept,
        this.id = id;
        this.patient = patient;
        this.schedule = schedule;
     //   this.dateAccept = dateAccept;
        this.timeAccept = timeAccept;
        this.isRecord = isRecord;
        this.isAccept = isAccept;
    }

    public RecordToDoctor(Patient patient, Schedule schedule,  Date timeAccept) { //Date dateAccept,
        this.patient = patient;
        this.schedule = schedule;
       // this.dateAccept = dateAccept;
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

//    public Doctor getDoctor() {
//        return doctor;
//    }
//
//    public void setDoctor(Doctor doctor) {
//        this.doctor = doctor;
//    }

//    public Date getDateAccept() {
//        return dateAccept;
//    }
//    public String getDateAcceptPrint() {
//        SimpleDateFormat simple = new SimpleDateFormat("dd.MM.Y");
//        return simple.format(dateAccept);
//    }
//
//    public void setDateAccept(Date dateAccept) {
//        this.dateAccept = dateAccept;
//    }

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

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    @Override
    public String toString() {
        return "RecordToDoctor{" +
                "id=" + id +
                ", patient=" + patient+
                ", doctor=" + schedule.getDoctor().getPerson().getSurname() +
//                ", dateAccept=" + getDateAcceptPrint() +
                ", timeAccept=" + getTimeAcceptPrint() +
                ", isRecord=" + isRecord +
                ", isAccept=" + isAccept +
                '}';
    }
}
