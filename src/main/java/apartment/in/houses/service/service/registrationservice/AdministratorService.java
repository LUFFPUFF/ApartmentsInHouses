package apartment.in.houses.service.service.registrationservice;

import apartment.in.houses.data_access.registrationdatabase.entities.Administrator;
import apartment.in.houses.data_access.registrationdatabase.repositories.AdministratorRepository;
import apartment.in.houses.service.exceprion.InvalidException;
import apartment.in.houses.service.exceprion.NotFoundException;
import apartment.in.houses.service.service.apartmentservice.Service;
import apartment.in.houses.util.DI.annotation.Autowired;
import apartment.in.houses.util.DI.annotation.Component;

import java.util.List;

@Component
public class AdministratorService implements Service<Administrator, Integer> {
    @Autowired
    private AdministratorRepository repository;

    @Override
    public List<Administrator> getAll() {
        return repository.getAllUsers();
    }

    @Override
    public Administrator getId(Integer key) throws NotFoundException {
        return repository.getUser(key);
    }

    @Override
    public void add(Administrator entity) {
        repository.insert(entity);
    }

    @Override
    public void update(Administrator entity) throws InvalidException {
        repository.update(entity);
    }

    @Override
    public void delete(Integer key) throws InvalidException {
        repository.delete(key);
    }
}
