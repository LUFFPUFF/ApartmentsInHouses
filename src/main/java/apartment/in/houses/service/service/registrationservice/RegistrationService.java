package apartment.in.houses.service.service.registrationservice;

import apartment.in.houses.data_access.registrationdatabase.entities.Administrator;
import apartment.in.houses.data_access.registrationdatabase.entities.Registration;
import apartment.in.houses.data_access.registrationdatabase.entities.User;
import apartment.in.houses.data_access.registrationdatabase.repositories.RegistrationRepository;
import apartment.in.houses.service.exceprion.InvalidException;
import apartment.in.houses.service.exceprion.NotFoundException;
import apartment.in.houses.service.service.apartmentservice.Service;
import apartment.in.houses.util.DI.annotation.Autowired;
import apartment.in.houses.util.DI.annotation.Component;

import java.util.List;

@Component
public class RegistrationService implements Service<Registration, Integer> {

    @Autowired
    private RegistrationRepository repository;
    @Override
    public List<Registration> getAll() {
        return repository.getAllRegistration();
    }

    @Override
    public Registration getId(Integer key) throws NotFoundException {
        return repository.getRegistration(key);
    }

    @Override
    public void add(Registration entity) throws InvalidException {
        repository.insert(entity);
    }

    @Override
    public void update(Registration entity) throws InvalidException {
        repository.update(entity);
    }

    @Override
    public void delete(Integer key) throws InvalidException {
        repository.delete(key);
    }
}
