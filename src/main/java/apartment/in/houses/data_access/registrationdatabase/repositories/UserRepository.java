package apartment.in.houses.data_access.registrationdatabase.repositories;

import apartment.in.houses.data_access.registrationdatabase.dao.UserDAO;
import apartment.in.houses.data_access.registrationdatabase.entities.Administrator;
import apartment.in.houses.data_access.registrationdatabase.entities.User;
import apartment.in.houses.data_access.util.exucutor.interf.Executor;
import apartment.in.houses.util.DI.annotation.Autowired;
import apartment.in.houses.util.DI.annotation.Component;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.List;

@Component
public class UserRepository implements UserDAO<User, Integer> {
    @Autowired
    private Executor<User, Integer> executor;

    @Override
    public List<User> getAllUsers() {
        try {
            return executor.executeGetAll(User.class);
        } catch (SQLException | InstantiationException | IllegalAccessException | NoSuchMethodException |
                 InvocationTargetException | NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User getUser(int id) {
        try {
            return executor.executeGetId(User.class, id);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException |
                 NoSuchFieldException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean insert(User entity) {
        try {
            return executor.executeInsert(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(User entity) {
        try {
            return executor.executeUpdate(entity);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean delete(Integer key) {
        return executor.executeDelete(Administrator.class, key);
    }
}
