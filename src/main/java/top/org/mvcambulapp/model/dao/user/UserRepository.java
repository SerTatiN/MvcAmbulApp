package top.org.mvcambulapp.model.dao.user;

import org.springframework.data.repository.CrudRepository;
import top.org.mvcambulapp.model.entity.Person;
import top.org.mvcambulapp.model.entity.RecordToDoctor;
import top.org.mvcambulapp.model.entity.User;

import java.util.Optional;


public interface UserRepository extends CrudRepository<User,Integer> {
    User findByLogin(String login);
    Optional<User> findById(Integer userId);

//    User update (User user);




}
