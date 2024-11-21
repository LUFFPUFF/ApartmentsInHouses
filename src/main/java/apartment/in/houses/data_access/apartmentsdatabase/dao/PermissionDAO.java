package apartment.in.houses.data_access.apartmentsdatabase.dao;

import apartment.in.houses.data_access.apartmentsdatabase.entitie.Apartment;
import apartment.in.houses.data_access.apartmentsdatabase.entitie.Permission;

import java.util.List;

public interface PermissionDAO {

    List<Permission> getPermissions();
    Permission getPermission(int id);
    boolean insert(Permission permission);
    boolean update(Permission permission);
    boolean delete(Permission permission);
}
