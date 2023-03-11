package top.org.mvcambulapp.model.dao.person;

import org.springframework.data.repository.CrudRepository;
import top.org.mvcambulapp.model.entity.Person;

import java.util.Optional;

public interface PersonRepository extends CrudRepository<Person,Integer> {

    Optional<Person> findById(Integer integer);
}
