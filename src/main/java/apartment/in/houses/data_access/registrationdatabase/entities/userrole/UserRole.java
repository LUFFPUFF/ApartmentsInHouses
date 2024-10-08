package apartment.in.houses.data_access.registrationdatabase.entities.userrole;

import apartment.in.houses.data_access.util.exucutor.annotation.Column;
import apartment.in.houses.data_access.util.exucutor.annotation.Table;
import apartment.in.houses.util.DI.annotation.Component;

@Component
@Table(name = "UserRole")
public class UserRole {

    @Column(name = "id")
    private UserRoleId id;

    public UserRole(UserRoleId id) {
        this.id = id;
    }

    public UserRoleId getId() {
        return id;
    }

    public void setId(UserRoleId id) {
        this.id = id;
    }
}
