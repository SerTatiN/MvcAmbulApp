package top.org.mvcambulapp.model.entity;

import jakarta.persistence.*;
import org.apache.tomcat.util.modeler.ParameterInfo;
import org.springframework.cglib.core.Local;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
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
    @DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
    private LocalTime timeAccept;
    // private Date dateAccept;
    private boolean isRecord;
    private boolean isAccept;

    //новое
    @OneToOne(mappedBy = "record")
    private ListPatientCard list;

    public RecordToDoctor() {
        this.patient = null;
        this.isRecord = false;
        this.isAccept = false;
    }
    public RecordToDoctor(Schedule schedule, LocalTime timeAccept) {
        this.id = id;
        this.patient = null;
        this.schedule = schedule;
        this.timeAccept = timeAccept;
        this.isRecord = false;
        this.isAccept = false;
    }

    public RecordToDoctor(Integer id, Patient patient, Schedule schedule, LocalTime timeAccept, boolean isRecord, boolean isAccept) {//Date dateAccept,
        this.id = id;
        this.patient = patient;
        this.schedule = schedule;
        this.timeAccept = timeAccept;
        this.isRecord = isRecord;
        this.isAccept = isAccept;
    }

    public RecordToDoctor(Patient patient, Schedule schedule,  LocalTime timeAccept) { //Date dateAccept,
        this.patient = patient;
        this.schedule = schedule;
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



    public LocalTime getTimeAccept() {
        return timeAccept;
    }
    public String getTimeAcceptPrint() {
        SimpleDateFormat simple = new SimpleDateFormat("hh:mm");
        return simple.format(timeAccept);
    }

    public void setTimeAccept(LocalTime timeAccept) {
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
                ", timeAccept=" + timeAccept +
                ", isRecord=" + isRecord +
                ", isAccept=" + isAccept +
                '}';
    }
}
