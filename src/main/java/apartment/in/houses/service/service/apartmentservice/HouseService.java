package apartment.in.houses.service.service.apartmentservice;

import apartment.in.houses.data_access.apartmentsdatabase.repositories.HouseRepository;
import apartment.in.houses.service.exceprion.NotFoundException;
import apartment.in.houses.service.exceprion.InvalidException;
import apartment.in.houses.data_access.apartmentsdatabase.entities.House;
import apartment.in.houses.util.DI.annotation.Autowired;
import apartment.in.houses.util.DI.annotation.Component;

import java.util.List;

@Component
public class HouseService implements Service<House, Integer> {

    @Autowired
    private HouseRepository repository;

    @Override
    public List<House> getAll() {
        return repository.getAllHouses();
    }

    @Override
    public House getId(Integer id) throws NotFoundException {
        House house = repository.getHouse(id);
        if (house == null || id < 0) {
            throw new NotFoundException("House with id " + id + " not found");
        }
        return house;
    }

    @Override
    public void add(House house) throws InvalidException {
        if (house.getId() < 0) {
            throw new InvalidException("Id house must be positive");
        }
        repository.insert(house);
    }

    @Override
    public void update(House house) throws InvalidException {
        if (house.getId() < 0) {
            throw new InvalidException("Id house must be positive");
        }
        repository.update(house);
    }

    @Override
    public void delete(House house) throws InvalidException {
        if (house.getId() < 0) {
            throw new InvalidException("Id house must be positive");
        }
        repository.delete(house);
    }
}
