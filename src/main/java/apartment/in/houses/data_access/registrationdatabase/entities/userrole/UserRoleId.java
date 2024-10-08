package apartment.in.houses.data_access.registrationdatabase.entities.userrole;

import apartment.in.houses.data_access.util.exucutor.annotation.Column;
import apartment.in.houses.util.DI.annotation.Component;

@Component
public class UserRoleId {

    @Column(name = "user_id")
    private int userId;

    @Column(name = "role_id")
    private int roleId;

    public UserRoleId(int userId, int roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }
}
