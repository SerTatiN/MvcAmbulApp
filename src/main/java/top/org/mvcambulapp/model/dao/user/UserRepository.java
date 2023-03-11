package top.org.mvcambulapp.model.dao.user;

import org.springframework.data.repository.CrudRepository;
import top.org.mvcambulapp.model.entity.User;


public interface UserRepository extends CrudRepository<User,Integer> {
    User findByLogin(String login);



}
