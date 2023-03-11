package top.org.mvcambulapp.model.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import top.org.mvcambulapp.model.dao.user.IDaoUser;
import top.org.mvcambulapp.model.entity.User;

// Сервис данных о пользователей
@Service
public class DbUserDetailsService implements UserDetailsService {
    @Autowired
    private IDaoUser daoUser;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = daoUser.getUserByLogin(username);
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new DbUserDetails(user);
    }
//    //Authentication-РёРЅС„РѕСЂРјР°С†РёСЏ Рѕ С‚РµРєСѓС‰РµРј Usere
//    public boolean hasUserId(Authentication authentication, int userId) {
//        User user = repository.findByEmail(authentication.getName()).orElseThrow();
//        return user.getId() == userId;
//    }

}
