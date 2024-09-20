package BL.service;

import BL.exceprion.NotFoundException;
import BL.exceprion.InvalidException;
import data_access.entity.House;
import data_access.util.DAOUtil.HouseController;
import util.DI.annotation.Autowired;
import util.DI.annotation.Component;

import java.util.List;

@Component
public class HouseService implements Service<House, Integer> {

    @Autowired
    private HouseController controller;

    @Autowired
    public HouseService(HouseController controller) {
        this.controller = controller;
    }

    public HouseService() {

    }

    @Override
    public List<House> getAll() {
        return controller.getAll();
    }

    @Override
    public House getId(Integer id) throws NotFoundException {
        House house = controller.getEntityById(id);
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
        controller.insert(house);
    }

    @Override
    public void update(House house) throws InvalidException {
        if (house.getId() < 0) {
            throw new InvalidException("Id house must be positive");
        }
        controller.update(house);
    }

    @Override
    public void delete(Integer id) throws InvalidException {
        if (id < 0) {
            throw new InvalidException("Id house must be positive");
        }
        controller.delete(id);
    }
}
