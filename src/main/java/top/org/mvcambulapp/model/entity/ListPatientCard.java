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

    @Column(nullable = false, length = 255)
    private String result;


    @OneToOne
    @JoinColumn(name="record_id")//, nullable = false если приема нет, нет листа
    private RecordToDoctor record;

    public ListPatientCard() {
    }

    //Создание в момент приема врача
    public ListPatientCard(RecordToDoctor record) {
        this.record = record;
    }


    public RecordToDoctor getRecord() {
        return record;
    }

    public void setRecord(RecordToDoctor record) {
        this.record = record;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
                ", patient=" + record.getPatient().getPerson().getSurname() +
                ", dataAccept=" + record.getSchedule().getDataPrint()+
                ", specialityDoctor='" + record.getSchedule().getDoctor().getSpeciality() + '\'' +
                ", doctor=" + record.getSchedule().getDoctor().getPerson().getSurname() +
                ", result='" + result + '\'' +
                '}';
    }

}
