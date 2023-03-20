package top.org.mvcambulapp.model.entity;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="t_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String login;

    @Column(nullable = false)
    private String password;

//    @ManyToMany(mappedBy = "users", cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
//    private Set<Role> roles = new HashSet<>();

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(
            name="users_roles",
            joinColumns = @JoinColumn(name="user_id"),
            inverseJoinColumns = @JoinColumn(name="role_id"))
    private Set<Role> roles = new HashSet<>();


    @OneToOne(mappedBy = "user")
    private Person person;

  //  @OneToOne(mappedBy = "user")
//    private Doctor doctor;
//
//    @OneToOne(mappedBy = "user")
//    private Patient patient;


    public User() {}

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + roles +
                '}';
    }
}
