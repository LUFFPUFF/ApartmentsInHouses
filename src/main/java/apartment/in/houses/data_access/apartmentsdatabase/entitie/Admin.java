package apartment.in.houses.data_access.apartmentsdatabase.entitie;

import apartment.in.houses.util.DI.annotation.Component;
import apartment.in.houses.util.orm.annotation.*;

import java.io.Serializable;


@Entity
@Table(name = "Admins")
@Component
public class Admin implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GeneratedValue.GenerationType.IDENTITY)
    private int id;

    @ManyToOne(targetEntity = Role.class, optional = false)
    @Column(name = "role_id")
    private Role role;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
