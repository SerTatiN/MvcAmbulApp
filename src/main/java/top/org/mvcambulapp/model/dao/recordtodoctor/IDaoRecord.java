package top.org.mvcambulapp.model.dao.recordtodoctor;

import top.org.mvcambulapp.model.dao.IDaoBase;

import top.org.mvcambulapp.model.entity.Doctor;
import top.org.mvcambulapp.model.entity.RecordToDoctor;
import top.org.mvcambulapp.model.entity.Schedule;

import java.util.Date;
import java.util.List;

public interface IDaoRecord extends IDaoBase<RecordToDoctor> {
//    List<RecordToDoctor> getRecordsToDoctor(Doctor doctor);
    //List<RecordToDoctor> getRecordsToSchedule(Schedule schedule);
    List<RecordToDoctor> getRecordsByDoctorId(Integer id);
    List<RecordToDoctor> getRecordsByDoctorIdSortedByTime(Integer id);
    List<RecordToDoctor> getRecordsByDoctorIdAndDate(Integer id,Date date);
    List<RecordToDoctor> getRecordsByScheduleIdSortedByTime(Integer id);
    List<RecordToDoctor> getRecordsByPatientId(Integer id);

}
