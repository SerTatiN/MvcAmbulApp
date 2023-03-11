package top.org.mvcambulapp.model.dao.role;

import org.springframework.data.repository.CrudRepository;
import top.org.mvcambulapp.model.entity.Role;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role,Integer> {
//    Optional<Role> findById(Integer id);

}
