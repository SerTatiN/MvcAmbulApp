package top.org.mvcambulapp.model.dao.recordtodoctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.org.mvcambulapp.model.entity.Doctor;
import top.org.mvcambulapp.model.entity.RecordToDoctor;
import top.org.mvcambulapp.model.entity.Schedule;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class DbDaoRecord implements IDaoRecord{
    @Autowired
    private RecordRepository recordRepository;

    @Override
    public List<RecordToDoctor> listAll() {
        return (List<RecordToDoctor>) recordRepository.findAll();
    }

    @Override
    public Optional<RecordToDoctor> getById(Integer id) {
        return recordRepository.findById(id);
    }

    @Override
    public RecordToDoctor save(RecordToDoctor recordToDoctor) {
        return recordRepository.save(recordToDoctor);
    }

//    public List<RecordToDoctor> getRecordsToDoctor(Doctor doctor){
//        return recordRepository.findByDoctor(doctor);
//    }
//    public List<RecordToDoctor> getRecordsToDateAccept(Date dateAccept){
//        return recordRepository.findByDateAccept(dateAccept);
//    }

    @Override
    public RecordToDoctor update(RecordToDoctor recordToDoctor) {
        if (recordRepository.findById(recordToDoctor.getId()).isPresent()) {
            return recordRepository.save(recordToDoctor);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        if (recordRepository.findById(id).isPresent()) {
            recordRepository.deleteById(id);
        }
    }

//    @Override
//    public List<RecordToDoctor> getRecordsToSchedule(Schedule schedule) {
//        return recordRepository.findRecordToDoctorsBySchedule(schedule);
//    }

    @Override
    public List<RecordToDoctor> getRecordsByDoctorId(Integer id) {
        return recordRepository.findBySchedule_Doctor_Id(id);
    }

    @Override
    public List<RecordToDoctor> getRecordsByDoctorIdSortedByDate(Integer id) {
        return recordRepository.findBySchedule_Doctor_IdOrderByTimeAccept(id);
    }

    @Override
    public List<RecordToDoctor> getRecordsByDoctorIdAndDate(Integer id, Date date) {
        return recordRepository.findBySchedule_Doctor_IdAndSchedule_DateOrderByTimeAccept(id,date);
    }

    @Override
    public List<RecordToDoctor> getRecordsByPatientId(Integer id) {
        return recordRepository.findByPatientId(id);
    }
}
