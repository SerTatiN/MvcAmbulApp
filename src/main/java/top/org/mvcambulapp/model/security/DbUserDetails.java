package top.org.mvcambulapp.model.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import top.org.mvcambulapp.model.entity.Role;
import top.org.mvcambulapp.model.entity.User;

import java.util.*;

// класс-сервис для работы с суностью пользователя
public class DbUserDetails implements UserDetails {

    private User dbUser;  // сущность пользователя из БД

    public DbUserDetails(User dbUser) {
        this.dbUser = dbUser;
    }

    public User getDbUser() {
        return dbUser;
    }

    public void setDbUser(User dbUser) {
        this.dbUser = dbUser;
    }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.<GrantedAuthority>singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
//    }
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Role> roles = (Set<Role>) dbUser.getRoles();
//
        System.out.println("getAuthorities() "+ roles.size() +" " + roles.toString());
////        for (Role text : roles)
////        {
////            System.out.println(text.getAuthority()+ " " + text.getName() );
////        }
       List<SimpleGrantedAuthority> authorities = new ArrayList<>();
//
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
            System.out.println(role.getName());
        }
       // System.out.println(" authorities "+ roles.size()+ " "+ authorities.toString());
       return authorities;
      // return dbUser.getRoles();
    }

    @Override
    public String getPassword() {
        return dbUser.getPassword();
    }

    @Override
    public String getUsername() {
        return dbUser.getLogin();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
