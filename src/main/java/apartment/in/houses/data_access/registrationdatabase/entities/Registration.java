package apartment.in.houses.data_access.registrationdatabase.entities;

import apartment.in.houses.data_access.util.exucutor.annotation.Column;
import apartment.in.houses.data_access.util.exucutor.annotation.Id;
import apartment.in.houses.data_access.util.exucutor.annotation.Table;
import apartment.in.houses.util.DI.annotation.Component;

import java.sql.Date;

@Component
@Table(name = "Registrations")
public class Registration {

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "user_id")
    private int userId;

    @Column(name = "admin_id")
    private int adminId;

    @Column(name = "registration_date")
    private Date registrationDate;

    @Column(name = "confirmed")
    private boolean confirmed;

    public Registration(int id, int userId, int adminId, Date registrationDate, boolean confirmed) {
        this.id = id;
        this.userId = userId;
        this.adminId = adminId;
        this.registrationDate = registrationDate;
        this.confirmed = confirmed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAdminId() {
        return adminId;
    }

    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public Date getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }
}
