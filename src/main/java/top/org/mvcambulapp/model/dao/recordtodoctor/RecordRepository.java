package top.org.mvcambulapp.model.dao.recordtodoctor;

import org.springframework.data.repository.CrudRepository;
import top.org.mvcambulapp.model.entity.Doctor;
import top.org.mvcambulapp.model.entity.RecordToDoctor;
import top.org.mvcambulapp.model.entity.Schedule;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RecordRepository extends CrudRepository<RecordToDoctor,Integer> {
    @Override
    Optional<RecordToDoctor> findById(Integer integer);

//    List<RecordToDoctor> findByDoctor(Doctor doctor);
//    List<RecordToDoctor> findByDateAccept(Date dateAccept);
    List<RecordToDoctor> findRecordToDoctorsBySchedule(Schedule schedule);

}
