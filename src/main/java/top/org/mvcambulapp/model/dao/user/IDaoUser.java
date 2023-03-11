package top.org.mvcambulapp.model.dao.user;

import top.org.mvcambulapp.model.entity.User;

public interface IDaoUser {
    User getUserByLogin(String login);
    User addUser(User user);
//    User update(User user);
//    void delete(Integer id);

}
