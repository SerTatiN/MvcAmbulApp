package top.org.mvcambulapp.model.dao.recordtodoctor;

import top.org.mvcambulapp.model.dao.IDaoBase;

import top.org.mvcambulapp.model.entity.Doctor;
import top.org.mvcambulapp.model.entity.RecordToDoctor;
import top.org.mvcambulapp.model.entity.Schedule;

import java.util.Date;
import java.util.List;

public interface IDaoRecord extends IDaoBase<RecordToDoctor> {
//    List<RecordToDoctor> getRecordsToDoctor(Doctor doctor);
    List<RecordToDoctor> getRecordsToSchedule(Schedule schedule);
}
