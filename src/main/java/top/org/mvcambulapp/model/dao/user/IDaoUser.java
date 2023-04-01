package top.org.mvcambulapp.model.dao.user;

import top.org.mvcambulapp.model.entity.User;

import java.util.List;
import java.util.Optional;

public interface IDaoUser {
   User getUserByLogin(String login);
    User save(User user);
    Optional <User> getUserById(Integer userId);
    List<User> listAll();

//    User update(User user);
    void delete(Integer userId);

}
