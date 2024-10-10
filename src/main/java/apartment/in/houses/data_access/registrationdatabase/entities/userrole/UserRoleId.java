package apartment.in.houses.data_access.registrationdatabase.entities.userrole;

import apartment.in.houses.util.DI.annotation.Component;
import apartment.in.houses.util.orm.annotation.Column;

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
