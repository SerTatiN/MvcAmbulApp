package top.org.mvcambulapp.model.dao.listpatientcard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.org.mvcambulapp.model.entity.ListPatientCard;

import java.util.List;
import java.util.Optional;

@Service
public class DbDaoListPatientCard implements IDaoListPatientCard{
    @Autowired
    private ListPatientCardRepository listPatientCardRepository;

    @Override
    public List<ListPatientCard> listAll() {
        return (List<ListPatientCard>) listPatientCardRepository.findAll();
    }

    @Override
    public Optional<ListPatientCard> getById(Integer id) {
        return listPatientCardRepository.findById(id);
    }

    @Override
    public ListPatientCard save(ListPatientCard listPatientCard) {
        return listPatientCardRepository.save(listPatientCard);
    }

    @Override
    public ListPatientCard update(ListPatientCard listPatientCard) {
        if (listPatientCardRepository.findById(listPatientCard.getId()).isPresent()){
            return listPatientCardRepository.save(listPatientCard);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        if (listPatientCardRepository.findById(id).isPresent()) {
            listPatientCardRepository.deleteById(id);
        }
    }
}
