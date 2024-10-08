package apartment.in.houses.data_access.registrationdatabase.repositories;

import apartment.in.houses.data_access.registrationdatabase.dao.RegistrationDAO;
import apartment.in.houses.data_access.registrationdatabase.entities.Administrator;
import apartment.in.houses.data_access.registrationdatabase.entities.Registration;
import apartment.in.houses.data_access.registrationdatabase.entities.User;
import apartment.in.houses.data_access.util.exucutor.interf.Executor;
import apartment.in.houses.util.DI.annotation.Autowired;
import apartment.in.houses.util.DI.annotation.Component;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@Component
public class RegistrationRepository implements RegistrationDAO {
    @Autowired
    private Executor<Registration, Integer> executor;

    @Override
    public List<Registration> getAllRegistration() {
        try {
            return executor.executeGetAll(Registration.class);
        } catch (SQLException | InstantiationException | IllegalAccessException | NoSuchMethodException |
                InvocationTargetException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Registration getRegistration(int id) {
        try {
            return executor.executeGetId(Registration.class, id);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchFieldException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean insert(Registration registration) {
        try {
            return executor.executeInsert(registration);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Registration registration) {
        try {
            return executor.executeUpdate(registration);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(int id) {
        return executor.executeDelete(Registration.class, id);
    }
}
