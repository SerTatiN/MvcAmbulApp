package top.org.mvcambulapp.model.dao.user;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.org.mvcambulapp.model.entity.User;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DbDaoUserTest {
    @Autowired
    private IDaoUser daoUser;

    @Test
    void addUser() {
        User user = new User("user1", "qwerty");
        daoUser.addUser(user);
    }
}