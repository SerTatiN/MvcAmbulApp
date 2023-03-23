package top.org.mvcambulapp.model.dao.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.org.mvcambulapp.model.dao.doctor.DbDaoDoctor;
import top.org.mvcambulapp.model.dao.doctor.DoctorRepository;
import top.org.mvcambulapp.model.entity.Doctor;
import top.org.mvcambulapp.model.entity.Schedule;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
public class DbDaoSchedule implements IDaoSchedule{
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private DoctorRepository doctorRepository;

    @Override
    public List<Schedule> listAll() {
        List<Schedule> list = (List<Schedule>) scheduleRepository.findAll();
        return list;
    }

    @Override
    public Optional<Schedule> getById(Integer id) {
        return scheduleRepository.findById(id);
    }

    @Override
    public Schedule save(Schedule schedule) {
       // System.out.println("save sch " + schedule.getDate());
        return scheduleRepository.save(schedule);
    }

    @Override
    public Schedule update(Schedule schedule) {
        System.out.println("upd " + schedule.getId());
        if (scheduleRepository.findById(schedule.getId()).isPresent()) {
            return scheduleRepository.save(schedule);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        if (scheduleRepository.findById(id).isPresent()) {
            scheduleRepository.deleteById(id);
        }
    }

    @Override
    public Optional<Schedule> getScheduleByDoctorAndDate(Doctor doctor, Date date) {
        return scheduleRepository.findByDoctorAndDate(doctor,date);
    }
}
