package apartment.in.houses.service.service.apartmentservice;

import apartment.in.houses.service.exceprion.InvalidException;
import apartment.in.houses.service.exceprion.NotFoundException;
import apartment.in.houses.data_access.apartmentsdatabase.repositories.ApartmentRepository;
import apartment.in.houses.data_access.apartmentsdatabase.entitie.Apartment;
import apartment.in.houses.util.DI.annotation.Autowired;
import apartment.in.houses.util.DI.annotation.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApartmentService implements Service<Apartment, Integer> {
    @Autowired
    private ApartmentRepository repository;

    public ApartmentService() {
    }

    public ApartmentService(ApartmentRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Apartment> getAll() {
        return repository.getAllApartments();
    }

    @Override
    public Apartment getId(Integer key) throws NotFoundException {
        Apartment apartment = repository.getApartment(key);
        if (apartment == null || key < 0) {
            throw new NotFoundException("Apartment with id " + key + " not found");
        }
        return apartment;
    }

    public List<Apartment> getBetweenPrice(double minPrice, double maxPrice) {
        return getAll().stream()
                .filter(min -> min.getPrice() >= minPrice)
                .filter(max -> max.getPrice() <= maxPrice)
                .collect(Collectors.toList());
    }

    public List<Apartment> getCountRooms(int rooms) {
        return getAll().stream()
                .filter(floors -> floors.getRooms() == rooms)
                .collect(Collectors.toList());
    }

    @Override
    public void add(Apartment entity) throws InvalidException {
        if (entity.getId() < 0) {
            throw new InvalidException("Id house must be positive");
        }
        repository.insert(entity);
    }

    @Override
    public void update(Apartment entity) throws InvalidException {
        if (entity.getId() < 0) {
            throw new InvalidException("Id house must be positive");
        }
        repository.update(entity);
    }

    @Override
    public void delete(Apartment apartment) throws InvalidException {
        repository.delete(apartment);
    }
}
