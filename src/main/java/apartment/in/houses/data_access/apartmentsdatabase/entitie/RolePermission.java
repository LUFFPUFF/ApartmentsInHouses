package apartment.in.houses.data_access.apartmentsdatabase.entitie;

import apartment.in.houses.framework.spring.DI.annotation.Component;
import apartment.in.houses.framework.spring.orm.annotation.*;

import java.io.Serializable;

@Entity
@Component
@Table(name = "RolesPermissions")
public class RolePermission implements Serializable {
    @EmbeddedId
    private RolePermissionId id;

    @ManyToOne(targetEntity = Role.class, optional = false)
    @Column(name = "role_id")
    private Role role;

    @ManyToOne(targetEntity = Permission.class, optional = false)
    @Column(name = "permission_id")
    private Permission permission;

    public RolePermissionId getId() {
        return id;
    }

    public void setId(RolePermissionId id) {
        this.id = id;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Permission getPermission() {
        return permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission;
    }
}
