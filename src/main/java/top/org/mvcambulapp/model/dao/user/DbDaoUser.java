package top.org.mvcambulapp.model.dao.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import top.org.mvcambulapp.model.entity.Role;
import top.org.mvcambulapp.model.entity.User;

import java.security.SecurityPermission;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class DbDaoUser implements IDaoUser{
    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Override
    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User save(User user) {
        User userExist = userRepository.findByLogin(user.getLogin());
        if (userExist == null) {

            System.out.println("addUser " + user.getRoles());
           // user.setRoles(user.getRoles());
            // перед добавлением пользователя захешируем его пароль
            String encodedPassword = encoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
            return userRepository.save(user);
        }

        return null;
    }
    public User currentUser(){
        String login = SecurityContextHolder.getContext().getAuthentication().getName();
      // List <GrantedAuthority> roles = SecurityContextHolder.getContext().getAuthentication().getCredentials();
        return userRepository.findByLogin(login);
    }

    @Override
    public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<User> listAll() {
        return (List<User>) userRepository.findAll();
    }

    @Override
    public void delete(Integer userId) {
        if (userRepository.findById(userId).isPresent()){
            userRepository.deleteById(userId);
        }
    }



}
