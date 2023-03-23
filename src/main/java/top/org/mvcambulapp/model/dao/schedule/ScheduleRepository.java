package top.org.mvcambulapp.model.dao.schedule;


import org.springframework.data.repository.CrudRepository;
import top.org.mvcambulapp.model.entity.Doctor;
import top.org.mvcambulapp.model.entity.Schedule;

import java.util.Date;
import java.util.Optional;

public interface ScheduleRepository extends CrudRepository<Schedule, Integer> {

    Optional<Schedule> findById(Integer integer);
    Optional<Schedule> findByDoctorAndDate(Doctor doctor,Date date);
}
