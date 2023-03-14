package top.org.mvcambulapp.model.dao.role;

import org.springframework.data.repository.CrudRepository;
import top.org.mvcambulapp.model.entity.Role;
import top.org.mvcambulapp.model.entity.User;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role,Integer> {
  //  List<Role> listAll();
//    Role findRoleByAuthority(String authority);

//    Optional<Role> findById(Integer id);
//    User findByName(String name);

//    Role findRoleByAuthority(String authority) throws NoSuchElementException;

}
