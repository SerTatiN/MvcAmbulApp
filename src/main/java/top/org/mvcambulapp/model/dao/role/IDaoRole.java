package top.org.mvcambulapp.model.dao.role;

import top.org.mvcambulapp.model.entity.Role;
import top.org.mvcambulapp.model.entity.User;

import java.util.List;

public interface IDaoRole {
   // User getUserByRole(String role);

//    Role add(String role);
//
    Role save(Role role);
   // Role getRoleByAuthority(String authority);

    List<Role> listAll();
}
