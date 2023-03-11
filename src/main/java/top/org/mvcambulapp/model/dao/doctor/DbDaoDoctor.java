package top.org.mvcambulapp.model.dao.doctor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.org.mvcambulapp.model.entity.Doctor;

import java.util.List;
import java.util.Optional;
@Service
public class DbDaoDoctor implements IDaoDoctor{

    @Autowired
    DoctorRepository doctorRepository;
    @Override
    public List<Doctor> listAll() {
        return (List<Doctor>) doctorRepository.findAll();
    }

    @Override
    public Optional<Doctor> getById(Integer id) {
        return doctorRepository.findById(id);
    }

    @Override
    public Doctor save(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    @Override
    public Doctor update(Doctor doctor) {
        if (doctorRepository.findById(doctor.getId()).isPresent()){
            return doctorRepository.save(doctor);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        if (doctorRepository.findById(id).isPresent()){
           doctorRepository.deleteById(id);
        }
    }
}
