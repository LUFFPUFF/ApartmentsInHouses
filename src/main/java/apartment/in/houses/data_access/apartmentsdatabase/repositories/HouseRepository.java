package apartment.in.houses.data_access.apartmentsdatabase.repositories;

import apartment.in.houses.data_access.apartmentsdatabase.dao.HouseDAO;
import apartment.in.houses.data_access.apartmentsdatabase.entities.House;
import apartment.in.houses.data_access.util.exucutor.ExecutorImpl;
import apartment.in.houses.util.DI.annotation.Autowired;
import apartment.in.houses.util.DI.annotation.Component;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@Component
public class HouseRepository implements HouseDAO {

    @Autowired
    private ExecutorImpl<House, Integer> executor;

    @Override
    public List<House> getAllHouses() {
        try {
            return executor.executeGetAll(House.class);
        } catch (SQLException | InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public House getHouse(int id) {
        try {
            return executor.executeGetId(House.class, id);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchFieldException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean insert(House house) {
        try {
            return executor.executeInsert(house);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(House house) {
        try {
            return executor.executeUpdate(house);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        return executor.executeDelete(House.class, id);
    }
}
