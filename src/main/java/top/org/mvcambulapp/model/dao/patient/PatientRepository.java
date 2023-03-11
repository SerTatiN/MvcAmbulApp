package top.org.mvcambulapp.model.dao.patient;

import org.springframework.data.repository.CrudRepository;
import top.org.mvcambulapp.model.entity.Patient;

import java.util.Optional;

public interface PatientRepository extends CrudRepository<Patient,Integer> {
    @Override
    Optional<Patient> findById(Integer integer);
}
