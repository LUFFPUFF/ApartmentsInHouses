package apartment.in.houses.data_access.apartmentsdatabase.entitie;

import apartment.in.houses.framework.spring.orm.annotation.Column;
import apartment.in.houses.framework.spring.orm.annotation.Embeddable;

import java.io.Serializable;


@Embeddable
public class RolePermissionId implements Serializable {

    @Column(name = "role_id")
    private int role_id;

    @Column(name = "permission_id")
    private int permission_id;

    public RolePermissionId() {
    }

    public RolePermissionId(int role_id, int permission_id) {
        this.role_id = role_id;
        this.permission_id = permission_id;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public int getPermission_id() {
        return permission_id;
    }

    public void setPermission_id(int permission_id) {
        this.permission_id = permission_id;
    }

    @Override
    public String toString() {
        return "RolePermissionId{" +
                "role_id=" + role_id +
                ", permission_id=" + permission_id +
                '}';
    }
}
