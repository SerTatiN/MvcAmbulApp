package top.org.mvcambulapp.model.dao.schedule;

import top.org.mvcambulapp.model.dao.IDaoBase;
import top.org.mvcambulapp.model.entity.Doctor;
import top.org.mvcambulapp.model.entity.Schedule;

import java.util.Date;
import java.util.Optional;

public interface IDaoSchedule extends IDaoBase<Schedule> {
    Optional<Schedule> getScheduleByDoctorAndDate(Doctor doctor, Date date);
}
