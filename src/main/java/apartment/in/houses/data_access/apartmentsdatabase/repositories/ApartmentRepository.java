package apartment.in.houses.data_access.apartmentsdatabase.repositories;

import apartment.in.houses.data_access.apartmentsdatabase.dao.ApartmentDAO;
import apartment.in.houses.data_access.apartmentsdatabase.entities.Apartment;
import apartment.in.houses.data_access.util.exucutor.ExecutorImpl;
import apartment.in.houses.util.DI.annotation.Autowired;
import apartment.in.houses.util.DI.annotation.Component;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@Component
public class ApartmentRepository implements ApartmentDAO {
    @Autowired
    private ExecutorImpl<Apartment, Integer> executor;

    @Override
    public List<Apartment> getAllApartments() {
        try {
            return executor.executeGetAll(Apartment.class);
        } catch (SQLException | InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Apartment getApartment(int id) {
        try {
            return executor.executeGetId(Apartment.class, id);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchFieldException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean insert(Apartment apartment) {
        try {
            return executor.executeInsert(apartment);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Apartment apartment) {
        try {
            return executor.executeUpdate(apartment);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Integer id) {
        return executor.executeDelete(Apartment.class, id);
    }
}
