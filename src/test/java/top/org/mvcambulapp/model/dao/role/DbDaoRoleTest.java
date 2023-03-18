package top.org.mvcambulapp.model.dao.role;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.org.mvcambulapp.model.entity.Role;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class DbDaoRoleTest {
@Autowired
private RoleRepository roleRepository;

    @Test
    void save() {

        roleRepository.save(new Role("ROLE_PATIENT"));
        roleRepository.save(new Role("ROLE_ADMIN"));
        roleRepository.save(new Role("ROLE_DOCTOR"));


    }
}