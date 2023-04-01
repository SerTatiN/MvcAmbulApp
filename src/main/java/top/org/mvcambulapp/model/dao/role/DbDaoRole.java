package top.org.mvcambulapp.model.dao.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.org.mvcambulapp.model.entity.Role;

import java.util.List;

@Service
public class DbDaoRole implements IDaoRole{
    @Autowired
    private RoleRepository roleRepository;

    @Override
    public List<Role> listAll() {
        List<Role> roles = (List<Role>) roleRepository.findAll();
        return roles;
    }

    @Override
    public Role save(Role role) {
        return roleRepository.save(role);
    }

//    @Override // нельзя так
//    public Role getByAuthority(String authority) {
//        return roleRepository.findByAuthority(authority);
//    }

    @Override
    public Role getRoleByAuthority(String authority) {
        List<Role> roles = (List<Role>) roleRepository.findAll();
        for (Role role : roles){
            if (role.getAuthority().contains(authority))
                return role;
        }
        return null;
    }

}
