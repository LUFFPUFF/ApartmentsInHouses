package apartment.in.houses.service.service.apartmentservice;

import apartment.in.houses.data_access.apartmentsdatabase.dao.PermissionDAO;
import apartment.in.houses.data_access.apartmentsdatabase.entitie.Apartment;
import apartment.in.houses.data_access.apartmentsdatabase.entitie.Permission;
import apartment.in.houses.data_access.apartmentsdatabase.repositories.PermissionRepository;
import apartment.in.houses.framework.spring.DI.annotation.Autowired;
import apartment.in.houses.framework.spring.DI.annotation.Component;
import apartment.in.houses.service.exception.InvalidException;
import apartment.in.houses.service.exception.NotFoundException;

import java.util.List;

@Component
public class PermissionService implements Service<Permission, Integer> {


    @Autowired
    private PermissionRepository repository;


    @Override
    public List<Permission> getAll() {
        return repository.getPermissions();
    }

    @Override
    public Permission getId(Integer key) throws NotFoundException {
        Permission permission = repository.getPermission(key);
        if (permission == null || key < 0) {
            throw new NotFoundException("Apartment with id " + key + " not found");
        }
        return permission;
    }

    @Override
    public void add(Permission entity) throws InvalidException {
        if (entity.getId() < 0) {
            throw new InvalidException("Id house must be positive");
        }
        repository.insert(entity);
    }

    @Override
    public void update(Permission entity) throws InvalidException {

    }

    @Override
    public void delete(Permission entity) throws InvalidException {

    }
}
