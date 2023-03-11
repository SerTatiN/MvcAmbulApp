package top.org.mvcambulapp.model.dao.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.org.mvcambulapp.model.entity.Person;

import java.util.List;
import java.util.Optional;

@Service
public class DbDaoPerson implements IDaoPerson{

    @Autowired
    private PersonRepository personRepository;

    @Override
    public List<Person> listAll() {
        List<Person> personList = (List<Person>) personRepository.findAll();
        return personList;
    }

    @Override
    public Optional<Person> getById(Integer id) {
        return personRepository.findById(id);
    }

    @Override
    public Person save(Person person) {
        return personRepository.save(person);
    }

    @Override
    public Person update(Person person) {
        if (personRepository.findById(person.getId()).isPresent()) {
            System.out.println("person.getId()=" + person.getId());
            return personRepository.save(person);
        }
        return null;
    }

    @Override
    public void delete(Integer id) {
        if (personRepository.findById(id).isPresent() && personRepository.findById(id).get().getDoctor()== null) {
            personRepository.deleteById(id);
        } else {
          if (personRepository.findById(id).get().getDoctor()!= null) {
              System.out.println("Сначала надо удалить Doctor");
          }
        }
    }
}
