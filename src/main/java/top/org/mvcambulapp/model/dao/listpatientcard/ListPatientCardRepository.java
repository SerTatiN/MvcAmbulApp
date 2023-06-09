package top.org.mvcambulapp.model.dao.listpatientcard;

import org.springframework.data.repository.CrudRepository;
import top.org.mvcambulapp.model.entity.ListPatientCard;

import java.util.List;
import java.util.Optional;

public interface ListPatientCardRepository extends CrudRepository<ListPatientCard,Integer> {
    @Override
    Optional<ListPatientCard> findById(Integer integer);

    List<ListPatientCard> findByRecord_Patient_Id(Integer id);

}
