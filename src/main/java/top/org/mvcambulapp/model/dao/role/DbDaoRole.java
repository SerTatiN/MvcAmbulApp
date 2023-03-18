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
        System.out.println("getRoleByAuthority "+roles.size()+" " +roles.toString());
        for (Role role : roles){
           // System.out.println("{1} "+role.getAuthority()+ " ? " +authority);
            if (role.getAuthority().contains(authority))
               // System.out.println("{1} "+role.getAuthority()+ " ? " +authority);
                return role;
        }

        return null;
    }


//    @Override
//    public User getUserByRole(String role) {
//        return daoRole.getUserByRole(role);
//    }
}
