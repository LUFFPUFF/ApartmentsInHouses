package apartment.in.houses.data_access.registrationdatabase.entities;

import apartment.in.houses.data_access.util.exucutor.annotation.Column;
import apartment.in.houses.data_access.util.exucutor.annotation.Id;
import apartment.in.houses.data_access.util.exucutor.annotation.Table;
import apartment.in.houses.util.DI.annotation.Component;

import java.sql.Date;

@Component
@Table(name = "Administrations")
public class Administrator {
    @Id
    @Column(name = "id")
    private int id;
    @Column(name = "user_name")
    private String userName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "role")
    private String role;
    @Column(name = "create_at")
    private Date createAt;
    @Column(name = "last_login")
    private Date lastLogin;
    @Column(name = "status")
    private String status;

    public Administrator(int id, String userName, String email, String password, String role, Date createAt, Date lastLogin, String status) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
        this.createAt = createAt;
        this.lastLogin = lastLogin;
        this.status = status;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
