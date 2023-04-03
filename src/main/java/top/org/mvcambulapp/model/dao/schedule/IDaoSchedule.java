package top.org.mvcambulapp.model.dao.schedule;

import top.org.mvcambulapp.model.dao.IDaoBase;
import top.org.mvcambulapp.model.entity.Doctor;
import top.org.mvcambulapp.model.entity.Schedule;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IDaoSchedule extends IDaoBase<Schedule> {
    Optional<Schedule> getScheduleByDoctorAndDate(Doctor doctor, Date date);
    Optional<Schedule> getScheduleByDoctorIdAndDate(Integer id, Date date);
    List<Schedule> getScheduleByDoctorId(Integer id);
    List<Schedule> getScheduleByDoctorIdSortByDate(Integer id);
}
