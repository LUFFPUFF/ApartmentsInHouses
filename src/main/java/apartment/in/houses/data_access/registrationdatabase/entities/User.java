package apartment.in.houses.data_access.registrationdatabase.entities;

import apartment.in.houses.util.DI.annotation.Component;
import apartment.in.houses.util.orm.annotation.Column;
import apartment.in.houses.util.orm.annotation.Id;
import apartment.in.houses.util.orm.annotation.Table;

import java.sql.Date;

@Component
@Table(name = "Users")
public class User {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private String status;
    @Column(name = "create_at")
    private Date createAt;

    public User(int id, String userName, String email, String password, String status, Date createAt) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.status = status;
        this.createAt = createAt;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }
}
