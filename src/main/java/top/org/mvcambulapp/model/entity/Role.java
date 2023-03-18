package top.org.mvcambulapp.model.entity;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="t_role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

//    @ManyToMany
//    @JoinTable(
//            name="users_roles",
//            joinColumns = @JoinColumn(name="role_id"),
//            inverseJoinColumns = @JoinColumn(name="user_id"))
//    private Set<User> users = new HashSet<>();


    @ManyToMany(mappedBy = "roles", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    private Set<User> users = new HashSet<>();
    public Role() {
    }

    public Role(Integer id, String name, Set<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }

    public Role(String name) {
        this.name = name;
    }

    public Role(String name, Set<User> users) {
        this.name = name;
        this.users = users;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
//                ", users=" + users +
                '}';
    }

    @Override
    public String getAuthority() {
        return getName();
    }

}
