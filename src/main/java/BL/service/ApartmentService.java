package BL.service;

import BL.exceprion.InvalidException;
import BL.exceprion.NotFoundException;
import data_access.entity.Apartment;
import data_access.util.DAOUtil.ApartmentController;
import util.DI.annotation.Autowired;
import util.DI.annotation.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ApartmentService implements Service<Apartment, Integer> {

    @Autowired
    private ApartmentController controller;

    public ApartmentService() {

    }

    @Autowired
    public ApartmentService(ApartmentController controller) {
        this.controller = controller;
    }

    @Override
    public List<Apartment> getAll() {
        return controller.getAll();
    }

    @Override
    public Apartment getId(Integer key) throws NotFoundException {
        Apartment apartment = controller.getEntityById(key);
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
        controller.insert(entity);
    }

    @Override
    public void update(Apartment entity) throws InvalidException {
        if (entity.getId() < 0) {
            throw new InvalidException("Id house must be positive");
        }
        controller.update(entity);
    }

    @Override
    public void delete(Integer key) throws InvalidException {
        if (key < 0) {
            throw new InvalidException("Id house must be positive");
        }
        controller.delete(key);
    }
}
