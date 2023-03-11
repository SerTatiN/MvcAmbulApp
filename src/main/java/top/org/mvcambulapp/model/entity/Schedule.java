package top.org.mvcambulapp.model.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Date;


@Entity
@Table(name="t_schedule")
public class Schedule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private Date date;
   @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
   @Column(nullable = false)
    private Date startTime;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    //@DateTimeFormat(pattern = "hh:mm")
    @Column(nullable = false)
    private Date endTime;

    @ManyToOne()
    @JoinColumn(name="doctor_id",nullable = false)
    private Doctor doctor;

    public Schedule() {
    }

    public Schedule(Integer id, Date date, Date startTime, Date endTime, Doctor doctor) {
        this.id = id;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor = doctor;
    }

    public Schedule(Date date, Date startTime, Date endTime, Doctor doctor) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.doctor = doctor;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public String getDataPrint() {
        SimpleDateFormat simple = new SimpleDateFormat("dd.MM.Y");
        return simple.format(date);
    }

    public void setData(Date date) {
        this.date = date;
    }

    public Date getStartTime() {
        return startTime;
    }

    public String getStartTimePrint() {
        SimpleDateFormat simple = new SimpleDateFormat("HH:mm");
        return simple.format(startTime);
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public String getEndTimePrint() {
        SimpleDateFormat simple = new SimpleDateFormat("HH:mm");
        return simple.format(endTime);
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "id=" + id +
                ", date=" + getDataPrint() +
                ", startTime=" + getStartTimePrint() +
                ", endTime='" + getEndTimePrint() + '\'' +
                ", doctor=" + doctor +
                '}';
    }
}
