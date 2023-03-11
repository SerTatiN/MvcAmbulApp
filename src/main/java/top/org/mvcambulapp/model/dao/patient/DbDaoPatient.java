package top.org.mvcambulapp.model.dao.patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.org.mvcambulapp.model.entity.Patient;

import java.util.List;
import java.util.Optional;

@Service
public class DbDaoPatient implements IDaoPatient{
    @Autowired
    private PatientRepository patientRepository;

    @Override
    public List<Patient> listAll() {
        return (List<Patient>) patientRepository.findAll();
    }

    @Override
    public Optional<Patient> getById(Integer id) {
        return patientRepository.findById(id);
    }

    @Override
    public Patient save(Patient patient) {
        return patientRepository.save(patient);
    }

    @Override
    public Patient update(Patient patient) {
        if (patientRepository.findById(patient.getId()).isPresent()) {
            return patientRepository.save(patient);
        }
        return null;
    }
//добавить проверку: при наличии записи к врачу не удалять, а также при наличии карты
    @Override
    public void delete(Integer id) {
        if (patientRepository.findById(id).isPresent()) {
            patientRepository.deleteById(id);
        }
    }
}
