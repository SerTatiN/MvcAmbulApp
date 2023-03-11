package top.org.mvcambulapp.model.dao.recordtodoctor;

import org.springframework.data.repository.CrudRepository;
import top.org.mvcambulapp.model.entity.RecordToDoctor;

import java.util.Optional;

public interface RecordRepository extends CrudRepository<RecordToDoctor,Integer> {
    @Override
    Optional<RecordToDoctor> findById(Integer integer);
}
