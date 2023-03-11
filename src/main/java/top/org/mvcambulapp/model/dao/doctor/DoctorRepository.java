package top.org.mvcambulapp.model.dao.doctor;

import org.springframework.data.repository.CrudRepository;
import top.org.mvcambulapp.model.entity.Doctor;

import java.util.Optional;

public interface DoctorRepository extends CrudRepository<Doctor,Integer> {

    Optional<Doctor> findById(Integer integer);
}
