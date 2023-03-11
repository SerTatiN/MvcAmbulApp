package top.org.mvcambulapp.model.dao.recordtodoctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.org.mvcambulapp.model.entity.RecordToDoctor;

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
}
